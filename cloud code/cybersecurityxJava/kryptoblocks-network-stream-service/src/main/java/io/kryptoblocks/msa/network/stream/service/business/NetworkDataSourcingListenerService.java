package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;

public interface NetworkDataSourcingListenerService {
	/**
	 * Enrich network data.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @return the network data enrichment model
	 * @throws BusinessException the business exception
	 */
	public NetworkDataEnrichmentModel enrichNetworkData(NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException;
}
