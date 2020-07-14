package io.kryptoblocks.msa.udp.listner.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.udp.listner.service.business.UDPListnerService;
import io.kryptoblocks.msa.udp.listner.service.business.UDPListnerStreamService;
import io.kryptoblocks.msa.udp.listner.service.business.UDPListnerStream;

public class UDPListnerStreamServiceImpl implements UDPListnerStreamService {
	
	
	@Autowired 
	UDPListnerStream networkDatraStream;
	
	@Autowired
	UDPListnerService networkDataProcessService;
	
	
	@Override
	public void sendToNetworkDataLandingStreamTopic(Object object) throws BusinessException {
		try {
			MessageChannel landingMessageChannel = networkDatraStream.networkLandingMessageChannel();
			//TODO
			//convert the input to JSON or AVRO - in future
			landingMessageChannel.send(MessageBuilder
	                .withPayload(object)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				ee.printStackTrace();
	        }
		
	}
	
	@Override
	@StreamListener(UDPListnerStream.NETWORK_DATA_LANDING_STREAM_INPUT)
	public void listenToNetworkDataLandingStreamTopic(Object input) throws BusinessException {
		//network landing topic can be any data, so the input is generic object
		//we need to transform this to SOURCING format, so that we can source it for our processing.
		
		try {
			
			if(input != null) {
				//Derive the SOURCING model
				NetworkDataSourcingModel networkDataSourcingModel = deriveNetworkDataSourcingModel(input);
				//From LANDING TO SOURCING 
				networkDataProcessService.sourceNetworkData(networkDataSourcingModel);
			}
			
		}catch(Exception e) {
			//TODO
		} 
	}

	@Override
	public void sendToNetworkDataSourcingStreamTopic(NetworkDataSourcingModel networkDataSourcingModel)
			throws BusinessException {
		try {
		MessageChannel messageChannel = networkDatraStream.networkSourcingMessageChannel();
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
	

	@Override
	@StreamListener(UDPListnerStream.NETWORK_DATA_SOURCING_STREAM_INPUT)
	public void listenToNetworkDataSourcingStreamTopic(NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException{
	
		
		try {
			//NetworkDataSourcingModel model = deriveNetworkDataSourcingModel(input);
			if(networkDataSourcingModel != null) {
				//From SOURCE TO ENRICHMENT
				networkDataProcessService.enrichNetworkData(networkDataSourcingModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
		
	}

	
		
	
	@Override
	public void sendToNetworkDataEnrichmentStreamTopic(NetworkDataEnrichmentModel networkDataEnrichmentModel)
			throws BusinessException {
		try {
			MessageChannel messageChannel = networkDatraStream.networkEnrichmentMessageChannel();
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
	
	@Override
	@StreamListener(UDPListnerStream.NETWORK_DATA_ENRICHMENT_STREAM_INPUT)
	public void listenToNetworkDataEnrichmentStreamTopic(NetworkDataEnrichmentModel networkDataEnrichmentModel) {
		try {
			//NetworkDataSourcingModel model = deriveNetworkDataSourcingModel(input);
			if(networkDataEnrichmentModel != null) {
				//From ENRICHMENT to SOR
				networkDataProcessService.sorNetworkData(networkDataEnrichmentModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
	}
	
	
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
	
	@Override
	@StreamListener(UDPListnerStream.NETWORK_DATA_SOR_STREAM_INPUT)
	public void listenToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel) {
		
		try {
			 
			if(networkDataSORModel != null) {
				//From SOR to AI
				networkDataProcessService.aiNetworkData(networkDataSORModel);
				networkDataProcessService.indexNetworkData(networkDataSORModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
	}

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
	
	@Override
	public void listenToNetworkDataAiStreamTopic(Object input) throws BusinessException {
		//No need to read from here any more, end of the flow
	}

	

	@Override
	public void listenToNetworkDataIndexingStreamTopic(Object input) {
		//No need to read from here any more, end of the flow
		
	}

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
	
	private NetworkDataSourcingModel deriveNetworkDataSourcingModel(Object input){
		NetworkDataSourcingModel returnValue = null;
		//TODO
		try {
			
		}catch(Exception e) {
			
		}
		
		return returnValue;
	}

	
	

}
