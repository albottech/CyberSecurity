package io.kryptoblocks.msa.common.stream.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import io.kryptoblocks.msa.common.stream.sink.DirectActivitityStreamSink;
 


// TODO: Auto-generated Javadoc
/**
 * The Class DirectActivityStreamMessageReceiver.
 */
public class DirectActivityStreamMessageReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DirectActivityStreamMessageReceiver.class);
	
	/**
	 * Process message.
	 *
	 * @param message the message
	 */
	@ServiceActivator(inputChannel = DirectActivitityStreamSink.DIRECT_ACTIVITY_INPUT)	
    public void processMessage(Message<?> message) {
		String payloadAsString = (String)message.getPayload();	
		System.out.println("direct activity sink message receiver msg: " + payloadAsString);
    }

}
