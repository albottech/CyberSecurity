package io.kryptoblocks.msa.network.stream.service.model;

import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.network.stream.service.business.impl.MappingJSONCommonModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ElasticSearchIndexModel {
	
	@Autowired 
	MappingJSONCommonModel mappingJSONCommonModel;
	
	private String _id;

	@Override
	public String toString() {
		return "ElasticSearchIndexModel [mappingJSONCommonModel=" + mappingJSONCommonModel + ", _id=" + _id + "]";
	}
	
		
}
