package io.kryptoblocks.msa.common.stream.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.stream.source.AppContainerPerformanceStreamSource;
import io.kryptoblocks.msa.common.stream.source.BusinessActivityStreamSource;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityStreamMessageSender.
 */
public class BusinessActivityStreamMessageSender {	
	
	/** The business activity source. */
	@Autowired
	private BusinessActivityStreamSource businessActivitySource;	
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivityStreamMessageSender.class);
	
	
    /**
     * Process message.
     *
     * @param businessActivityMsg the business activity msg
     */
    public void processMessage(String businessActivityMsg) {
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(businessActivityMsg, messageHeaderAccessor.getMessageHeaders());		 
		//SimpMessageHeaderAccessor.DESTINATION_HEADER
		boolean status = businessActivitySource.businessActivityOutput().send(message);
		LOGGER.debug("Business activity source message delivery stattus: {}", status);
    }

}
