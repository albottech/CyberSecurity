package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface DistributedNotificationStreamMessageSink.
 */
public interface DistributedNotificationStreamMessageSink {
	
	
	/** The distributed notification stream message input. */
	String DISTRIBUTED_NOTIFICATION_STREAM_MESSAGE_INPUT = "distributedNotificationStreamMessageInput";

	/**
	 * Distributed notification stream message input.
	 *
	 * @return the subscribable channel
	 */
	@Input(DistributedNotificationStreamMessageSink.DISTRIBUTED_NOTIFICATION_STREAM_MESSAGE_INPUT)
	SubscribableChannel distributedNotificationStreamMessageInput();

}
