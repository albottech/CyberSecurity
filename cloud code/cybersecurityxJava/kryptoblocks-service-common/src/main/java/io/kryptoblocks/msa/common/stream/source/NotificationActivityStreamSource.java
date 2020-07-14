package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface NotificationActivityStreamSource.
 */
public interface NotificationActivityStreamSource {
	
	/** The notification activity output. */
	String NOTIFICATION_ACTIVITY_OUTPUT = "notificationActivityOutput";
	
	/**
	 * Notificatione activity output.
	 *
	 * @return the message channel
	 */
	@Output(NOTIFICATION_ACTIVITY_OUTPUT)
	MessageChannel notificationeActivityOutput();
	
}
