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
import io.kryptoblocks.msa.common.stream.source.DistributedNotificationStreamMessageSource;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributedNotificationStreamMessageSender.
 */
public class DistributedNotificationStreamMessageSender {	
	
		
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DistributedNotificationStreamMessageSender.class);
	
	/** The distributed notification stream message source. */
	@Autowired
	private DistributedNotificationStreamMessageSource distributedNotificationStreamMessageSource;
	
	
    /**
     * Process message.
     *
     * @param notificationMsg the notification msg
     */
    public void processMessage(String notificationMsg) {
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(notificationMsg, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = distributedNotificationStreamMessageSource.distributedNotificationStreamMessageOutput().send(message);
		LOGGER.debug("distributed notification stream message source delivery status: {}", status);
    }

}
