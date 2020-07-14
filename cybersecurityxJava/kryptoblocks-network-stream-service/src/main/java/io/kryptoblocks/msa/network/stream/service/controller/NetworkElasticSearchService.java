package io.kryptoblocks.msa.network.stream.service.controller;

import io.kryptoblocks.msa.network.stream.service.model.ElasticSearchIndexModel;

public interface NetworkElasticSearchService {
	
	public void createDocument(ElasticSearchIndexModel document);
}
