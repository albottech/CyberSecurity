package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;

public interface NetworkDataEnrichListenerService {
	/**
	 * Sor network data.
	 *
	 * @param networkDataEnrichmentModel the network data enrichment model
	 * @return the network data SOR model
	 * @throws BusinessException the business exception
	 */
	public NetworkDataSORModel sorNetworkData(NetworkDataEnrichmentModel networkDataEnrichmentModel) throws BusinessException;
}
