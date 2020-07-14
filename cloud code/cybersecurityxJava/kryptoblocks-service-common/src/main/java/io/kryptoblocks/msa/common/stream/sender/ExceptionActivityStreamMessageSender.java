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
import io.kryptoblocks.msa.common.stream.source.ExceptionActivityStreamSource;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivityStreamMessageSender.
 */
public class ExceptionActivityStreamMessageSender {	
	
	/** The exception source. */
	@Autowired
	private ExceptionActivityStreamSource exceptionSource;	
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionActivityStreamMessageSender.class);
	
	
    /**
     * Process message.
     *
     * @param exceptionMsg the exception msg
     */
    public void processMessage(String exceptionMsg) {
    	LOGGER.debug("Exception source input message:  {}", exceptionMsg);
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(exceptionMsg, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = exceptionSource.exceptionActivityOutput().send(message);
		LOGGER.debug("Exception source message delivery stattus: {}", status);
    }

}
