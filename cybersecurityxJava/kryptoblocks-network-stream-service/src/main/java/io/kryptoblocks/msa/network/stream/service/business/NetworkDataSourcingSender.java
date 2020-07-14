package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;

public interface NetworkDataSourcingSender {
	/**
	 * Send to network data sourcing stream topic.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @throws BusinessException the business exception
	 */
	public void sendToNetworkDataSourcingStreamTopic(NetworkDataSourcingModel networkDataSourcingModel)throws BusinessException;
}
