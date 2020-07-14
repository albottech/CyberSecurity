package io.kryptoblocks.msa.network.stream.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataAISender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataIndexingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListenerService;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataAISenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataIndexingSenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSORListenerImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSORListenerServiceImpl;

@Configuration
@ConditionalOnExpression("${network.data.sor.input.enabled} && ${network.data.ai.output.enabled} && ${network.data.indexing.output.enabled}")
public class AIIndexingConfig {
	@Bean
	NetworkDataSORListener networkDataSORListener() {
		return new NetworkDataSORListenerImpl();
	}
	
	@Bean
	NetworkDataSORListenerService networkDataSORListenerService() {
		return new NetworkDataSORListenerServiceImpl();
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
	NetworkDataAISender networkDataAISender() {
		return new NetworkDataAISenderImpl();
	}
	
	@Bean
	NetworkDataIndexingSender networkDataIndexingSender() {
		return new NetworkDataIndexingSenderImpl();
	}
}
