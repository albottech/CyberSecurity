package io.kryptoblocks.msa.data.nfr.notification.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.notification.model.NotificationActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;


// TODO: Auto-generated Javadoc
/**
 * The Class NotificationActivityEventSender.
 */
public class NotificationActivityEventSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationActivityEventSender.class);
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
	
	/** The service notification activity stream. */
	@Value("${service.ride.bid.mgmt.activity.stream}")
	String serviceNotificationActivityStream;
	
	/**
	 * Send service notification.
	 *
	 * @param notificationActivity the notification activity
	 */
	public void sendServiceNotification(NotificationActivity notificationActivity) {
		
		try {
			notificationActivity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			notificationActivity.setStreamType(serviceNotificationActivityStream);
		eventBus.send(notificationActivity, Event.wrap(notificationActivity, notificationActivity));
		}catch(Exception e) {
			LOGGER.debug("exception in send service notification method: {}", ExceptionUtils.getFullStackTrace(e));			
		}
	}
}
