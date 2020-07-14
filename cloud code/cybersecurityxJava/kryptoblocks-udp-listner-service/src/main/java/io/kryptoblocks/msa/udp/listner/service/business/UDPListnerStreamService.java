package io.kryptoblocks.msa.udp.listner.service.business;

import org.springframework.messaging.handler.annotation.Payload;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;

public interface UDPListnerStreamService {
	
	public void listenToNetworkDataLandingStreamTopic(@Payload Object input) throws BusinessException;
	public void sendToNetworkDataLandingStreamTopic(Object object)throws BusinessException;
	
	public void listenToNetworkDataSourcingStreamTopic(@Payload NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException;
	public void sendToNetworkDataSourcingStreamTopic(NetworkDataSourcingModel networkDataSourcingModel)throws BusinessException;
	
	public void listenToNetworkDataEnrichmentStreamTopic(@Payload NetworkDataEnrichmentModel input);
	public void sendToNetworkDataEnrichmentStreamTopic(NetworkDataEnrichmentModel networkDataEnrichmentModel)throws BusinessException;
	
	public void listenToNetworkDataAiStreamTopic(@Payload Object input)throws BusinessException;
	public void sendToNetworkDataAiStreamTopic(NetworkDataAIModel networkDataAIModel)throws BusinessException;
	
	public void listenToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel);
	public void sendToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel)throws BusinessException;
	
	public void listenToNetworkDataIndexingStreamTopic(@Payload Object input);
	public void sendToNetworkDataIndexingStreamTopic(NetworkDataIndexModel networkDataIndexModel)throws BusinessException;
	
	

}
