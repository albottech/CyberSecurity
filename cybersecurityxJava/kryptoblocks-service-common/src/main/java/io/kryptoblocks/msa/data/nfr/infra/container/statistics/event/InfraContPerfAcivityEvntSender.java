package io.kryptoblocks.msa.data.nfr.infra.container.statistics.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.EventBus;
import reactor.bus.Event;


// TODO: Auto-generated Javadoc
/**
 * The Class InfraContPerfAcivityEvntSender.
 */
public class InfraContPerfAcivityEvntSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InfraContPerfAcivityEvntSender.class);
	
	/** The service infr cont perf activity stream. */
	@Value("${service.infra.container.performance.activity.stream}")
	String serviceInfrContPerfActivityStream;
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
		
	/**
	 * Send performance event.
	 *
	 * @param infraContPerfEntity the infra cont perf entity
	 */
	public void sendPerformanceEvent(InfraContPerfActivity infraContPerfEntity) {
		
		try {		
		LOGGER.debug("sending infrastructure container performance event message");
		infraContPerfEntity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		infraContPerfEntity.setStreamType(serviceInfrContPerfActivityStream);
		eventBus.send(infraContPerfEntity, Event.wrap(infraContPerfEntity, infraContPerfEntity));
		}catch(Exception e) {
			LOGGER.error("exception in send performance event method for infra container performance event: {}", ExceptionUtils.getFullStackTrace(e));
		}
	}
}
