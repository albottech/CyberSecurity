package io.kryptoblocks.msa.network.stream.service.business.impl;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.stream.service.business.NetworkControllerService;
import io.kryptoblocks.msa.network.stream.service.model.NetworkDataProcessStatus;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessInput;

public class NetworkControllerServiceImpl implements NetworkControllerService{
	/**
	 * Gets the network data process status.
	 *
	 * @param networkProcessInput the network process input
	 * @return the network data process status
	 * @throws BusinessException the business exception
	 */
	@Override
	public NetworkDataProcessStatus getNetworkDataProcessStatus(NetworkProcessInput networkProcessInput)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
