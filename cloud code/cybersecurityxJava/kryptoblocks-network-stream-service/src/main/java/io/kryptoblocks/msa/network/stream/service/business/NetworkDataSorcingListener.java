package io.kryptoblocks.msa.network.stream.service.business;

import org.springframework.messaging.handler.annotation.Payload;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;

public interface NetworkDataSorcingListener {
	/**
	 * Listen to network data sourcing stream topic.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @throws BusinessException the business exception
	 */
	public void listenToNetworkDataSourcingStreamTopic(@Payload NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException;

}
