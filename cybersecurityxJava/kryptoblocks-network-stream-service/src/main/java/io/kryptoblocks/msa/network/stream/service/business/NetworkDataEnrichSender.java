package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;

public interface NetworkDataEnrichSender {
	/**
	 * Send to network data enrichment stream topic.
	 *
	 * @param networkDataEnrichmentModel the network data enrichment model
	 * @throws BusinessException the business exception
	 */
	public void sendToNetworkDataEnrichmentStreamTopic(NetworkDataEnrichmentModel networkDataEnrichmentModel)throws BusinessException;
}
