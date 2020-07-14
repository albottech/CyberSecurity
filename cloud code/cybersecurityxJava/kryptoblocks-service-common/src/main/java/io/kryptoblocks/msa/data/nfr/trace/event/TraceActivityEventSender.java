package io.kryptoblocks.msa.data.nfr.trace.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;


// TODO: Auto-generated Javadoc
/**
 * The Class TraceActivityEventSender.
 */
public class TraceActivityEventSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TraceActivityEventSender.class);
	

	/** The service trace activity stream. */
	@Value("${service.trace.activity.stream}")
	String serviceTraceActivityStream;
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
	
	
	
	/**
	 * Send service trace.
	 *
	 * @param traceActivity the trace activity
	 */
	public void sendServiceTrace(TraceActivity traceActivity) {
		try {
		traceActivity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		traceActivity.setStreamType(serviceTraceActivityStream);
		eventBus.send(traceActivity, Event.wrap(traceActivity, traceActivity));
		}catch(Exception e) {
			LOGGER.error("exception in trace activity send method: {}", ExceptionUtils.getFullStackTrace(e));
			
		}
	}
}
