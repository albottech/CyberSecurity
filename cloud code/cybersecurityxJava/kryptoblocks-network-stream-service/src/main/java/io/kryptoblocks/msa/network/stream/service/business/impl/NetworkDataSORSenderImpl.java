package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataSORSenderImpl implements NetworkDataSORSender{
	
	@Autowired
	NetworkDatraStream networkDatraStream;
	/**
	 * Send to network data sor stream topic.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel) throws BusinessException {
		try {
			MessageChannel networkSorMessageChannel = networkDatraStream.networkSorMessageChannel();
			
			//TODO
			networkSorMessageChannel.send(MessageBuilder
	                .withPayload(networkDataSORModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
	}
}
