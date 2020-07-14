package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExceptionActivityStreamSink.
 */
public interface ExceptionActivityStreamSink {
	
	
	/** The exception activity input. */
	String EXCEPTION_ACTIVITY_INPUT = "exceptionActivityInput";

	/**
	 * Exception activity input.
	 *
	 * @return the subscribable channel
	 */
	@Input(ExceptionActivityStreamSink.EXCEPTION_ACTIVITY_INPUT)
	SubscribableChannel exceptionActivityInput();

}
