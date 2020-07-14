package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface BusinessActivityStreamSource.
 */
public interface BusinessActivityStreamSource {
	
	/** The business activity output. */
	String BUSINESS_ACTIVITY_OUTPUT = "businessActivityOutput";
	
	/**
	 * Business activity output.
	 *
	 * @return the message channel
	 */
	@Output(BUSINESS_ACTIVITY_OUTPUT)
	MessageChannel businessActivityOutput();

	 

}
