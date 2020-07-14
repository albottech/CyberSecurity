package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;

public interface NetworkDataSORSender {
	/**
	 * Send to network data sor stream topic.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @throws BusinessException the business exception
	 */
	public void sendToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel)throws BusinessException;
}
