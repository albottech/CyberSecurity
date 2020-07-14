package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;

public interface NetworkDataIndexingSender {
	
	/**
	 * Send to network data indexing stream topic.
	 *
	 * @param networkDataIndexModel the network data index model
	 * @throws BusinessException the business exception
	 */
	public void sendToNetworkDataIndexingStreamTopic(NetworkDataIndexModel networkDataIndexModel)throws BusinessException;
}
