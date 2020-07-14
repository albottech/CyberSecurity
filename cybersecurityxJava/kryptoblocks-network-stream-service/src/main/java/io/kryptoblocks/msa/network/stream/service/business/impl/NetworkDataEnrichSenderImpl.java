package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataEnrichSenderImpl implements NetworkDataEnrichSender{
	
	/** The Constant log. */
  	private static final Logger log = LoggerFactory.getLogger(NetworkDataEnrichSenderImpl.class);
  	
	/** The network data stream. */
	@Autowired 
	NetworkDatraStream networkDataStream;
	
	/**
	 * Send to network data enrichment stream topic.
	 *
	 * @param networkDataEnrichmentModel the network data enrichment model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToNetworkDataEnrichmentStreamTopic(NetworkDataEnrichmentModel networkDataEnrichmentModel)
			throws BusinessException {
		try {
			MessageChannel messageChannel = networkDataStream.networkEnrichmentMessageChannel();
			//TODO
			//convert the input to JSON or AVRO - in future
	        messageChannel.send(MessageBuilder
	                .withPayload(networkDataEnrichmentModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
	}
}
