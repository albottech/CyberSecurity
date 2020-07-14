package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface TraceActivityStreamSource.
 */
public interface TraceActivityStreamSource {
	
	/** The trace activity output. */
	String TRACE_ACTIVITY_OUTPUT = "traceActivityOutput";
	
	/**
	 * Trace activity output.
	 *
	 * @return the message channel
	 */
	@Output(TRACE_ACTIVITY_OUTPUT)
	MessageChannel traceActivityOutput();
	
}
