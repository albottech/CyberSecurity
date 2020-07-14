package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataIndexingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataIndexingSenderImpl implements NetworkDataIndexingSender{
	
	/** The Constant log. */
  	private static final Logger log = LoggerFactory.getLogger(NetworkDataIndexingSenderImpl.class);
  	
	/** The network data stream. */
	@Autowired 
	NetworkDatraStream networkDatraStream;
	
	/**
	 * Send to network data indexing stream topic.
	 *
	 * @param networkDataIndexModel the network data index model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToNetworkDataIndexingStreamTopic(NetworkDataIndexModel networkDataIndexModel)
			throws BusinessException {
		try {
			MessageChannel networkIndexingMessageChannel = networkDatraStream.networkIndexingMessageChannel();
			
			//TODO
			networkIndexingMessageChannel.send(MessageBuilder
	                .withPayload(networkDataIndexModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
		
	}
}
