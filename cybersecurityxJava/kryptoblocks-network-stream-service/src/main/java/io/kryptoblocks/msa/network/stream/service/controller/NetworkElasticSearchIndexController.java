package io.kryptoblocks.msa.network.stream.service.controller;

import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kryptoblocks.msa.network.stream.service.business.impl.MappingJSONCommonModel;
import io.kryptoblocks.msa.network.stream.service.model.ElasticSearchIndexModel;

@RestController
@RequestMapping("/api/v1/network/stream/elasticindex")
public class NetworkElasticSearchIndexController {
	
	@Autowired
	private NetworkElasticSearchService networkElasticSearchService;
	
	
	public NetworkElasticSearchIndexController (NetworkElasticSearchService networkElasticSearchService) {
		this.networkElasticSearchService = networkElasticSearchService;
	}
	
	/*
	 * @PostMapping public ResponseEntity createProfile(@RequestBody
	 * ElasticSearchIndexModel document) throws Exception { return new
	 * ResponseEntity(networkElasticSearchService.createDocument(document),
	 * HttpStatus.CREATED); }
	 */

}

