package io.kryptoblocks.msa.network.stream.service.business.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataLandingSenderImpl implements NetworkDataLandingSender{

	/** The network data stream. */
	@Autowired 
	NetworkDatraStream networkDatraStream;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	/** The Constant log. */
  	private static final Logger log = LoggerFactory.getLogger(NetworkDataLandingSenderImpl.class);
  	

	
	/**
	 * Send to network data landing stream topic.
	 *
	 * @param object the object
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToNetworkDataLandingStreamTopic(Object object){
		
		try {
			
			MessageChannel landingMessageChannel = networkDatraStream.networkLandingMessageChannel();
			
			String jsonObject = objectMapper.writeValueAsString(object);
					    
			landingMessageChannel.send(MessageBuilder
	                .withPayload(jsonObject)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				log.info("In sendToNetworkDataLandingStreamTopic exception" + ee);
				ee.printStackTrace();
	        }
		
	}
}
