package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
 
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface TraceActivityStreamSink.
 */
public interface TraceActivityStreamSink {
		
	/** The trace activity input. */
	String TRACE_ACTIVITY_INPUT = "traceActivityInput";

	/**
	 * Trace activity input.
	 *
	 * @return the subscribable channel
	 */
	@Input(TraceActivityStreamSink.TRACE_ACTIVITY_INPUT)
	SubscribableChannel traceActivityInput();

}
