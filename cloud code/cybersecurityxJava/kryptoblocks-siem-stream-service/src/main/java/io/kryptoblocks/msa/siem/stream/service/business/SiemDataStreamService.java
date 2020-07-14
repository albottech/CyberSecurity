package io.kryptoblocks.msa.siem.stream.service.business;

import org.springframework.messaging.handler.annotation.Payload;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.siem.repository.model.SiemDataAIModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataEnrichmentModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataIndexModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSORModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSourcingModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface SiemDataStreamService.
 */
public interface SiemDataStreamService {
	
	/**
	 * Listen to siem data landing stream topic.
	 *
	 * @param input the input
	 * @throws BusinessException the business exception
	 */
	public void listenToSiemDataLandingStreamTopic(@Payload Object input) throws BusinessException;
	
	/**
	 * Send to siem data landing stream topic.
	 *
	 * @param object the object
	 * @throws BusinessException the business exception
	 */
	public void sendToSiemDataLandingStreamTopic(Object object)throws BusinessException;
	
	/**
	 * Listen to siem data sourcing stream topic.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 * @throws BusinessException the business exception
	 */
	public void listenToSiemDataSourcingStreamTopic(@Payload SiemDataSourcingModel siemDataSourcingModel) throws BusinessException;
	
	/**
	 * Send to siem data sourcing stream topic.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 * @throws BusinessException the business exception
	 */
	public void sendToSiemDataSourcingStreamTopic(SiemDataSourcingModel siemDataSourcingModel)throws BusinessException;
	
	/**
	 * Listen to siem data enrichment stream topic.
	 *
	 * @param input the input
	 */
	public void listenToSiemDataEnrichmentStreamTopic(@Payload SiemDataEnrichmentModel input);
	
	/**
	 * Send to siem data enrichment stream topic.
	 *
	 * @param siemDataEnrichmentModel the siem data enrichment model
	 * @throws BusinessException the business exception
	 */
	public void sendToSiemDataEnrichmentStreamTopic(SiemDataEnrichmentModel siemDataEnrichmentModel)throws BusinessException;
	
	/**
	 * Listen to siem data ai stream topic.
	 *
	 * @param input the input
	 * @throws BusinessException the business exception
	 */
	public void listenToSiemDataAiStreamTopic(@Payload Object input)throws BusinessException;
	
	/**
	 * Send to siem data ai stream topic.
	 *
	 * @param siemDataAIModel the siem data AI model
	 * @throws BusinessException the business exception
	 */
	public void sendToSiemDataAiStreamTopic(SiemDataAIModel siemDataAIModel)throws BusinessException;
	
	/**
	 * Listen to siem data sor stream topic.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 */
	public void listenToSiemDataSorStreamTopic(SiemDataSORModel siemDataSORModel);
	
	/**
	 * Send to siem data sor stream topic.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 * @throws BusinessException the business exception
	 */
	public void sendToSiemDataSorStreamTopic(SiemDataSORModel siemDataSORModel)throws BusinessException;
	
	/**
	 * Listen to siem data indexing stream topic.
	 *
	 * @param input the input
	 */
	public void listenToSiemDataIndexingStreamTopic(@Payload Object input);
	
	/**
	 * Send to siem data indexing stream topic.
	 *
	 * @param siemDataIndexModel the siem data index model
	 * @throws BusinessException the business exception
	 */
	public void sendToSiemDataIndexingStreamTopic(SiemDataIndexModel siemDataIndexModel)throws BusinessException;
	
	

}
