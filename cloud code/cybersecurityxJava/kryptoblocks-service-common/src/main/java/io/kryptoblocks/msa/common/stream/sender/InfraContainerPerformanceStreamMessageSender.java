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
import io.kryptoblocks.msa.common.stream.source.InfraContainerPerformanceStreamSource;



// TODO: Auto-generated Javadoc
/**
 * The Class InfraContainerPerformanceStreamMessageSender.
 */
public class InfraContainerPerformanceStreamMessageSender {	
	
	/** The infra container performance stream source. */
	@Autowired
	private InfraContainerPerformanceStreamSource infraContainerPerformanceStreamSource;	
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InfraContainerPerformanceStreamMessageSender.class);
	
	
    /**
     * Process message.
     *
     * @param infraContainerPerformanceMsgAsJson the infra container performance msg as json
     */
    public void processMessage(String infraContainerPerformanceMsgAsJson) {
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(infraContainerPerformanceMsgAsJson, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = infraContainerPerformanceStreamSource.infraContainerPerformanceOutput().send(message);
		LOGGER.debug("app container performance source message delivery stattus: {}", status);
    }

}
