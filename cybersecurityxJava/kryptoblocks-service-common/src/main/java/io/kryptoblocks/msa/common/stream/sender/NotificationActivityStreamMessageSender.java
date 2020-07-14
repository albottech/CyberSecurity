package io.kryptoblocks.msa.common.stream.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.stream.source.NotificationActivityStreamSource;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationActivityStreamMessageSender.
 */
public class NotificationActivityStreamMessageSender {	
	
	/** The notification activity source. */
	@Autowired
	private NotificationActivityStreamSource notificationActivitySource;	
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(NotificationActivityStreamMessageSender.class);
	
	
    /**
     * Process message.
     *
     * @param notificationMsg the notification msg
     */
    public void processMessage(String notificationMsg) {
    	LOGGER.debug("notification activity source input message:  {}", notificationMsg);
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(notificationMsg, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = notificationActivitySource.notificationeActivityOutput().send(message);
		LOGGER.debug("notification activity source message delivery stattus: {}", status);
    }

}
