package io.kryptoblocks.msa.common.stream.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.stream.source.AppContainerPerformanceStreamSource;
import io.kryptoblocks.msa.common.stream.source.BusinessActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.DirectActivityStreamSource;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectActivityStreamMessageSender.
 */
public class DirectActivityStreamMessageSender {	
	
		
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DirectActivityStreamMessageSender.class);
	
	/** The direct activity stream source. */
	@Autowired
	private DirectActivityStreamSource directActivityStreamSource;
	
	
    /**
     * Process message.
     *
     * @param businessActivityMsg the business activity msg
     */
    public void processMessage(String businessActivityMsg) {
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(businessActivityMsg, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = directActivityStreamSource.directActivityOutput().send(message);
		LOGGER.debug("direct activity stream source message delivery status: {}", status);
    }

}
