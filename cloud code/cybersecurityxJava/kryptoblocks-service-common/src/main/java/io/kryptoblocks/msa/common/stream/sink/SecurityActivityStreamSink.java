package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
 
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface SecurityActivityStreamSink.
 */
public interface SecurityActivityStreamSink {
		
	/** The security activity input. */
	String SECURITY_ACTIVITY_INPUT = "securityActivityInput";

	/**
	 * Security activity input.
	 *
	 * @return the subscribable channel
	 */
	@Input(SecurityActivityStreamSink.SECURITY_ACTIVITY_INPUT)
	SubscribableChannel securityActivityInput();

}
