package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;

public interface NetworkDataAISender {
	
	/**
	 * Send to network data ai stream topic.
	 *
	 * @param networkDataAIModel the network data AI model
	 * @throws BusinessException the business exception
	 */
	public void sendToNetworkDataAiStreamTopic(NetworkDataAIModel networkDataAIModel)throws BusinessException;
}
