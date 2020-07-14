package io.kryptoblocks.msa.data.nfr.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.security.model.SecurityActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;


// TODO: Auto-generated Javadoc
/**
 * The Class SecurityActivityEventSender.
 */
public class SecurityActivityEventSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityActivityEventSender.class);
	

	/** The service security activity stream. */
	@Value("${service.security.activity.stream}")
	String serviceSecurityActivityStream;
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
	
	
	
	/**
	 * Send service trace.
	 *
	 * @param securityActivity the security activity
	 */
	public void sendServiceTrace(SecurityActivity securityActivity) {
		try {
			securityActivity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			securityActivity.setStreamType(serviceSecurityActivityStream);
		eventBus.send(securityActivity, Event.wrap(securityActivity, securityActivity));
		}catch(Exception e) {
			LOGGER.error("exception in security activity send method: {}", ExceptionUtils.getFullStackTrace(e));
			
		}
	}
}
