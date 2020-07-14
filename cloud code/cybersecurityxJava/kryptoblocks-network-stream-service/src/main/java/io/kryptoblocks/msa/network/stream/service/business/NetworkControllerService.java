package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.stream.service.model.NetworkDataProcessStatus;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessInput;

public interface NetworkControllerService {
	/**
	 * Gets the network data process status.
	 *
	 * @param networkProcessInput the network process input
	 * @return the network data process status
	 * @throws BusinessException the business exception
	 */
	public NetworkDataProcessStatus getNetworkDataProcessStatus(NetworkProcessInput networkProcessInput) throws BusinessException;
}
