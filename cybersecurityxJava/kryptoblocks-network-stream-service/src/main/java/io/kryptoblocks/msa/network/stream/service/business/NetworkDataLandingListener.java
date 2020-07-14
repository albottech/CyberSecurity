package io.kryptoblocks.msa.network.stream.service.business;

import org.springframework.messaging.handler.annotation.Payload;

import io.kryptoblocks.msa.common.exception.BusinessException;

public interface NetworkDataLandingListener{
	/**
	 * Listen to network data landing stream topic.
	 *
	 * @param input the input
	 * @throws BusinessException the business exception
	 * @throws Exception 
	 */
	public void listenToNetworkDataLandingStreamTopic(@Payload String input);
}
