package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface DirectActivityStreamSource.
 */
public interface DirectActivityStreamSource {
	
	/** The direct activity output. */
	String DIRECT_ACTIVITY_OUTPUT = "directActivityOutput";
	
	/**
	 * Direct activity output.
	 *
	 * @return the message channel
	 */
	@Output(DIRECT_ACTIVITY_OUTPUT)
	MessageChannel directActivityOutput();

	 

}
