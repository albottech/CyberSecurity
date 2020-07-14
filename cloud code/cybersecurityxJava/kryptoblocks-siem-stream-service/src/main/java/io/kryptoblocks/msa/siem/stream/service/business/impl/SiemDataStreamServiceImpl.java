package io.kryptoblocks.msa.siem.stream.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.siem.repository.model.SiemDataAIModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataEnrichmentModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataIndexModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSORModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSourcingModel;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDataProcessService;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDataStreamService;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDatraStream;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataStreamServiceImpl.
 */
public class SiemDataStreamServiceImpl implements SiemDataStreamService {
	
	
	/** The siem datra stream. */
	@Autowired 
	SiemDatraStream siemDatraStream;
	
	/** The siem data process service. */
	@Autowired
	SiemDataProcessService siemDataProcessService;
	
	
	/**
	 * Send to siem data landing stream topic.
	 *
	 * @param object the object
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToSiemDataLandingStreamTopic(Object object) throws BusinessException {
		try {
			MessageChannel landingMessageChannel = siemDatraStream.siemLandingMessageChannel();
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
	
	/**
	 * Listen to siem data landing stream topic.
	 *
	 * @param input the input
	 * @throws BusinessException the business exception
	 */
	@Override
	@StreamListener(SiemDatraStream.SIEM_DATA_LANDING_STREAM_INPUT)
	public void listenToSiemDataLandingStreamTopic(Object input) throws BusinessException {
		//siem landing topic can be any data, so the input is generic object
		//we need to transform this to SOURCING format, so that we can source it for our processing.
		
		try {
			
			if(input != null) {
				//Derive the SOURCING model
				SiemDataSourcingModel siemDataSourcingModel = deriveSiemDataSourcingModel(input);
				//From LANDING TO SOURCING 
				siemDataProcessService.sourceSiemData(siemDataSourcingModel);
			}
			
		}catch(Exception e) {
			//TODO
		} 
	}

	/**
	 * Send to siem data sourcing stream topic.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToSiemDataSourcingStreamTopic(SiemDataSourcingModel siemDataSourcingModel)
			throws BusinessException {
		try {
		MessageChannel messageChannel = siemDatraStream.siemSourcingMessageChannel();
		//TODO
		//convert the input to JSON or AVRO - in future
        messageChannel.send(MessageBuilder
                .withPayload(siemDataSourcingModel)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
		
		}catch(Exception ee) {
			//TODO
        }
	}
	

	/**
	 * Listen to siem data sourcing stream topic.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 * @throws BusinessException the business exception
	 */
	@Override
	@StreamListener(SiemDatraStream.SIEM_DATA_SOURCING_STREAM_INPUT)
	public void listenToSiemDataSourcingStreamTopic(SiemDataSourcingModel siemDataSourcingModel) throws BusinessException{
	
		
		try {
			//SiemDataSourcingModel model = deriveSiemDataSourcingModel(input);
			if(siemDataSourcingModel != null) {
				//From SOURCE TO ENRICHMENT
				siemDataProcessService.enrichSiemData(siemDataSourcingModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
		
	}

	
		
	
	/**
	 * Send to siem data enrichment stream topic.
	 *
	 * @param siemDataEnrichmentModel the siem data enrichment model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToSiemDataEnrichmentStreamTopic(SiemDataEnrichmentModel siemDataEnrichmentModel)
			throws BusinessException {
		try {
			MessageChannel messageChannel = siemDatraStream.siemEnrichmentMessageChannel();
			//TODO
			//convert the input to JSON or AVRO - in future
	        messageChannel.send(MessageBuilder
	                .withPayload(siemDataEnrichmentModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
	}
	
	/**
	 * Listen to siem data enrichment stream topic.
	 *
	 * @param siemDataEnrichmentModel the siem data enrichment model
	 */
	@Override
	@StreamListener(SiemDatraStream.SIEM_DATA_ENRICHMENT_STREAM_INPUT)
	public void listenToSiemDataEnrichmentStreamTopic(SiemDataEnrichmentModel siemDataEnrichmentModel) {
		try {
			//SiemDataSourcingModel model = deriveSiemDataSourcingModel(input);
			if(siemDataEnrichmentModel != null) {
				//From ENRICHMENT to SOR
				siemDataProcessService.sorSiemData(siemDataEnrichmentModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
	}
	
	
	/**
	 * Send to siem data sor stream topic.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToSiemDataSorStreamTopic(SiemDataSORModel siemDataSORModel) throws BusinessException {
		try {
			MessageChannel siemSorMessageChannel = siemDatraStream.siemSorMessageChannel();
			
			//TODO
			siemSorMessageChannel.send(MessageBuilder
	                .withPayload(siemDataSORModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
	}
	
	/**
	 * Listen to siem data sor stream topic.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 */
	@Override
	@StreamListener(SiemDatraStream.SIEM_DATA_SOR_STREAM_INPUT)
	public void listenToSiemDataSorStreamTopic(SiemDataSORModel siemDataSORModel) {
		
		try {
			 
			if(siemDataSORModel != null) {
				//From SOR to AI
				siemDataProcessService.aiSiemData(siemDataSORModel);
				siemDataProcessService.indexSiemData(siemDataSORModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
	}

	/**
	 * Send to siem data ai stream topic.
	 *
	 * @param siemDataAIModel the siem data AI model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToSiemDataAiStreamTopic(SiemDataAIModel siemDataAIModel) throws BusinessException {
		try {
			MessageChannel siemAiMessageChannel = siemDatraStream.siemAiMessageChannel();
			
			//TODO
			siemAiMessageChannel.send(MessageBuilder
	                .withPayload(siemDataAIModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
	}
	
	/**
	 * Listen to siem data ai stream topic.
	 *
	 * @param input the input
	 * @throws BusinessException the business exception
	 */
	@Override
	public void listenToSiemDataAiStreamTopic(Object input) throws BusinessException {
		//No need to read from here any more, end of the flow
	}

	

	/**
	 * Listen to siem data indexing stream topic.
	 *
	 * @param input the input
	 */
	@Override
	public void listenToSiemDataIndexingStreamTopic(Object input) {
		//No need to read from here any more, end of the flow
		
	}

	/**
	 * Send to siem data indexing stream topic.
	 *
	 * @param siemDataIndexModel the siem data index model
	 * @throws BusinessException the business exception
	 */
	@Override
	public void sendToSiemDataIndexingStreamTopic(SiemDataIndexModel siemDataIndexModel)
			throws BusinessException {
		try {
			MessageChannel siemIndexingMessageChannel = siemDatraStream.siemIndexingMessageChannel();
			
			//TODO
			siemIndexingMessageChannel.send(MessageBuilder
	                .withPayload(siemDataIndexModel)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
			
			}catch(Exception ee) {
				//TODO
	        }
		
	}
	
	/**
	 * Derive siem data sourcing model.
	 *
	 * @param input the input
	 * @return the siem data sourcing model
	 */
	private SiemDataSourcingModel deriveSiemDataSourcingModel(Object input){
		SiemDataSourcingModel returnValue = null;
		//TODO
		try {
			
		}catch(Exception e) {
			
		}
		
		return returnValue;
	}

	
	

}
