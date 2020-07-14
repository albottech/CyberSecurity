package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface DistributedNotificationStreamMessageSource.
 */
public interface DistributedNotificationStreamMessageSource {
	
	/** The distributed notification stream message output. */
	String DISTRIBUTED_NOTIFICATION_STREAM_MESSAGE_OUTPUT = "distributedNotificationStreamMessageOutput";
	
	/**
	 * Distributed notification stream message output.
	 *
	 * @return the message channel
	 */
	@Output(DISTRIBUTED_NOTIFICATION_STREAM_MESSAGE_OUTPUT)
	MessageChannel distributedNotificationStreamMessageOutput();

	 

}
