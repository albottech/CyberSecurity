package io.kryptoblocks.msa.network.stream.service.business;

import java.util.List;

import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.ElasticSearch.NetworkSourcingModelElasticSearch;

public interface NetworkSaveDataElasticSearch {
	
		public void saveSIEMDatatoElasticSearch(List<NetworkSourcingModelElasticSearch> siemNetworkDataSourcingModelListES);
		
		public void saveNetflowDatatoElasticSearch(List<NetworkDataSourcingModel> networkDataSourcingModelList);
		
}