package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface DirectActivitityStreamSink.
 */
public interface DirectActivitityStreamSink {
	
	
	/** The direct activity input. */
	String DIRECT_ACTIVITY_INPUT = "directActivityInput";

	/**
	 * Direct activity input.
	 *
	 * @return the subscribable channel
	 */
	@Input(DirectActivitityStreamSink.DIRECT_ACTIVITY_INPUT)
	SubscribableChannel directActivityInput();

}
