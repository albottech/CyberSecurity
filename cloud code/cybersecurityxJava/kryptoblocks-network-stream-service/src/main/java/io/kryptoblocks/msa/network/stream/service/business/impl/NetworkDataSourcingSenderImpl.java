package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSourcingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataSourcingSenderImpl implements NetworkDataSourcingSender{
	
	/** The network data stream. */
	@Autowired 
	NetworkDatraStream networkDataStream;
	
	/** The Constant log. */
  	private static final Logger log = LoggerFactory.getLogger(NetworkDataSourcingSenderImpl.class);
  	
	/**
	 * Send to network data sourcing stream topic.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToNetworkDataSourcingStreamTopic(NetworkDataSourcingModel networkDataSourcingModel)
			throws BusinessException {
		try {
		MessageChannel messageChannel = networkDataStream.networkSourcingMessageChannel();
		//TODO
		//convert the input to JSON or AVRO - in future
        messageChannel.send(MessageBuilder
                .withPayload(networkDataSourcingModel)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
		
		}catch(Exception ee) {
			//TODO
        }
	}

}
