package io.kryptoblocks.msa.data.nfr.app.statistics.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.EventBus;
import reactor.bus.Event;


// TODO: Auto-generated Javadoc
/**
 * The Class AppContPerfAcivityEvtSender.
 */
public class AppContPerfAcivityEvtSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContPerfAcivityEvtSender.class);
			
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
	
	/** The service container performance activity stream. */
	@Value("${service.app.container.performance.activity.stream}")
	String serviceContainerPerformanceActivityStream;
		
	/**
	 * Send performance event.
	 *
	 * @param appContPerfEntity the app cont perf entity
	 */
	public void sendPerformanceEvent(AppContPerfActivity appContPerfEntity) {
		
		try {
		LOGGER.debug("sending app container performance event");
		appContPerfEntity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		appContPerfEntity.setStreamType(serviceContainerPerformanceActivityStream);
		eventBus.send(appContPerfEntity, Event.wrap(appContPerfEntity, appContPerfEntity));	
		}catch(Exception e) {
			LOGGER.error("exception in sending applicaiton container performance event: {} ", ExceptionUtils.getFullStackTrace(e));
		}
	}
}
