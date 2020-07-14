package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;

public interface NetworkDataSORListenerService {
	/**
	 * Ai network data.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the network data AI model
	 * @throws BusinessException the business exception
	 */
	public NetworkDataAIModel aiNetworkData(NetworkDataSORModel networkDataSORModel) throws BusinessException;
	
	/**
	 * Index network data.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the network data index model
	 * @throws BusinessException the business exception
	 */
	public NetworkDataIndexModel indexNetworkData(NetworkDataSORModel networkDataSORModel) throws BusinessException;
}
