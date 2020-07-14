package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface SecurityActivityStreamSource.
 */
public interface SecurityActivityStreamSource {
	
	/** The security activity output. */
	String SECURITY_ACTIVITY_OUTPUT = "securityActivityOutput";
	
	/**
	 * Security activity output.
	 *
	 * @return the message channel
	 */
	@Output(SECURITY_ACTIVITY_OUTPUT)
	MessageChannel securityActivityOutput();
	
}
