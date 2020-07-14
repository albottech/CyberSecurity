package io.kryptoblocks.msa.network.stream.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichListenerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSorcingListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSourcingListenerService;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataEnrichListenerImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataEnrichListenerServiceImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataEnrichSenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSORSenderImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSourcingListenerImpl;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataSourcingListenerServiceImpl;

@Configuration
@ConditionalOnExpression("${network.data.sourcing.input.enabled} && ${network.data.enrichment.output.enabled}")
public class DataEnrichConfig {
	
	@Bean
	NetworkDataSorcingListener networkDataSourcingListener() {
		return new NetworkDataSourcingListenerImpl();
	}
	
	@Bean
	NetworkDataSourcingListenerService networkDataSourcingListenerService() {
		return new NetworkDataSourcingListenerServiceImpl();
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
	NetworkDataEnrichSender networkDataEnrichSender() {
		return new NetworkDataEnrichSenderImpl();
	}
}
