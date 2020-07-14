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
import io.kryptoblocks.msa.common.stream.source.SecurityActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.TraceActivityStreamSource;

// TODO: Auto-generated Javadoc
/**
 * The Class TraceActivityStreamMessageSender.
 */
public class TraceActivityStreamMessageSender {	
	
	/** The trace activity source. */
	@Autowired
	private TraceActivityStreamSource traceActivitySource;	
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(TraceActivityStreamMessageSender.class);
	
	
    /**
     * Process message.
     *
     * @param exceptionMsg the exception msg
     */
    public void processMessage(String exceptionMsg) {
    	LOGGER.debug("trace activity source input message:  {}", exceptionMsg);
    	MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();		
		messageHeaderAccessor.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);
		Message<String> message = MessageBuilder.createMessage(exceptionMsg, messageHeaderAccessor.getMessageHeaders());		 
		boolean status = traceActivitySource.traceActivityOutput().send(message);
		LOGGER.debug("trace activity source message delivery stattus: {}", status);
    }

}
