package io.kryptoblocks.msa.network.stream.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataAISender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichListenerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataIndexingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListenerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORSender;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataAISenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataEnrichListenerImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataEnrichListenerServiceImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataIndexingSenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSORListenerImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSORListenerServiceImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSORSenderImpl;


@Configuration
@ConditionalOnExpression("${network.data.enrichment.input.enabled} && ${network.data.sor.output.enabled}")
public class DataSORConfig {
	@Bean
	NetworkDataEnrichListener networkDataEnrichListener() {
		return new NetworkDataEnrichListenerImpl();
	}
	@Bean
	NetworkDataEnrichListenerService networkDataEnrichListenerService() {
		return new NetworkDataEnrichListenerServiceImpl();
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
	NetworkDataSORSender networkDataSORSender() {
		return new NetworkDataSORSenderImpl();
	}
	
	
}