package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;

public interface NetworkDataLandingSender {
	/**
	 * Send to network data landing stream topic.
	 *
	 * @param object the object
	 * @throws BusinessException the business exception
	 */
	public void sendToNetworkDataLandingStreamTopic(Object object);
}
