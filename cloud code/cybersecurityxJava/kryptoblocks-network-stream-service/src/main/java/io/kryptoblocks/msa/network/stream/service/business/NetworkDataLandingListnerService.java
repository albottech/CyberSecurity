package io.kryptoblocks.msa.network.stream.service.business;

import java.util.List;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.ElasticSearch.NetworkSourcingModelElasticSearch;

public interface NetworkDataLandingListnerService {
	/**
	 * Source network data.
	 *
	 * @param cce the cce
	 * @return the network data sourcing model
	 * @throws BusinessException the business exception
	 */
	//public NetworkDataSourcingModel sourceNetworkData(Object cce) throws BusinessException;
	public NetworkDataSourcingModel sourceNetworkData(List<NetworkDataSourcingModel> networkSourceData) throws BusinessException;

}
