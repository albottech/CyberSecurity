package io.kryptoblocks.msa.network.stream.service.config;

import java.net.InetSocketAddress;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.repository.key.NetworkDataSourcingKey;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingListnerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSourcingSender;
import io.kryptoblocks.msa.network.stream.service.business.impl.MappingJSONCommonModel;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataLandingListenerImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataLandingListnerServiceImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSourcingSenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.RecievedJSONModel;


@Configuration
@ConditionalOnExpression("${network.data.landing.input.enabled} && ${network.data.sourcing.output.enabled}")
public class DataSourcingConfig {
	
	@Bean
	RecievedJSONModel recievedJSONModel() {
		return new RecievedJSONModel();
	}
	
	@Bean
	MappingJSONCommonModel mappingJSONCommonModel() {
		return new MappingJSONCommonModel();
	}
	
	@Bean
	NetworkDataLandingListener networkDataLandingListener() {
		return new NetworkDataLandingListenerImpl();
	}
	
	@Bean
	NetworkDataSourcingModel networkDataSourcingModel() {
		return new NetworkDataSourcingModel();
	}
	
	@Bean
	NetworkDataSourcingKey networkDataSourcingKey() {
		return new NetworkDataSourcingKey();
	}
	
	@Bean
	NetworkDataLandingListnerService networkDataLandingListnerService() {
		return new NetworkDataLandingListnerServiceImpl();
	}
	
	@Bean
	NetworkServiceRepository networkServiceRepository() {
		return new NetworkServiceRepository();
	}
	
	@Bean 
	NetworkDataProcessActivityEventService networkDataProcessActivityEventService() {
		return new NetworkDataProcessActivityEventService();
	}
	
	@Bean
	NetworkDataSourcingSender networkDataSourcingSender() {
		return  new NetworkDataSourcingSenderImpl();
	}
	

}
