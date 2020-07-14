package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface ExceptionActivityStreamSource.
 */
public interface ExceptionActivityStreamSource {
	
	/** The exception activity output. */
	String EXCEPTION_ACTIVITY_OUTPUT = "exceptionActivityOutput";
	
	/**
	 * Exception activity output.
	 *
	 * @return the message channel
	 */
	@Output(EXCEPTION_ACTIVITY_OUTPUT)
	MessageChannel exceptionActivityOutput();

}
