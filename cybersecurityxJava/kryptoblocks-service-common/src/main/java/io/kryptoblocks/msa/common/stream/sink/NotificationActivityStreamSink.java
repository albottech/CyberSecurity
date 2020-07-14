package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
 
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotificationActivityStreamSink.
 */
public interface NotificationActivityStreamSink {
		
	/** The notification activity input. */
	String NOTIFICATION_ACTIVITY_INPUT = "notificationActivityInput";

	/**
	 * Notification activity input.
	 *
	 * @return the subscribable channel
	 */
	@Input(NotificationActivityStreamSink.NOTIFICATION_ACTIVITY_INPUT)
	SubscribableChannel notificationActivityInput();

}
