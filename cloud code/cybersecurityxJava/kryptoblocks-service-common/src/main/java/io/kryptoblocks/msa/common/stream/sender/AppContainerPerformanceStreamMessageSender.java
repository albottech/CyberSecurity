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



// TODO: Auto-generated Javadoc
/**
 * The Class AppContainerPerformanceStreamMessageSender.
 */
public class AppContainerPerformanceStreamMessageSender {	
	
	/** The app container performance stream source. */
	@Autowired
	private AppContainerPerformanceStreamSource appContainerPerformanceStreamSource;	
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContainerPerformanceStreamMessageSender.class);
	
	
    /**
     * Process message.
     *
     * @param appContainerPerformanceMsgAsJson the app container performance msg as json
     */
    public void processMessage(String appContainerPerformanceMsgAsJson) {
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(appContainerPerformanceMsgAsJson, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = appContainerPerformanceStreamSource.appContainerPerformanceOutput().send(message);
		LOGGER.debug("app container performance source message delivery stattus: {}", status);
    }

}
