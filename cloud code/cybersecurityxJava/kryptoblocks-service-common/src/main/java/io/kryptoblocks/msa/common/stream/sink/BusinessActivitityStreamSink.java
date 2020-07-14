package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface BusinessActivitityStreamSink.
 */
public interface BusinessActivitityStreamSink {
	
	
	/** The business activity input. */
	String BUSINESS_ACTIVITY_INPUT = "businessActivityInput";

	/**
	 * Business activity input.
	 *
	 * @return the subscribable channel
	 */
	@Input(BusinessActivitityStreamSink.BUSINESS_ACTIVITY_INPUT)
	SubscribableChannel businessActivityInput();

}
