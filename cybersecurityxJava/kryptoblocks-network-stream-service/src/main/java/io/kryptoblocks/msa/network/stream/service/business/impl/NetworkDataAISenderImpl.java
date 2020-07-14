package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataAISender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataAISenderImpl implements NetworkDataAISender{
	
	/** The Constant log. */
  	private static final Logger log = LoggerFactory.getLogger(NetworkDataAISenderImpl.class);
  	
	/** The network data stream. */
	@Autowired 
	NetworkDatraStream networkDatraStream;
	
	/**
	 * Send to network data ai stream topic.
	 *
	 * @param networkDataAIModel the network data AI model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToNetworkDataAiStreamTopic(NetworkDataAIModel networkDataAIModel) throws BusinessException {
		try {
			MessageChannel networkAiMessageChannel = networkDatraStream.networkAiMessageChannel();
			
			//TODO
			networkAiMessageChannel.send(MessageBuilder
	                .withPayload(networkDataAIModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
	}
}
