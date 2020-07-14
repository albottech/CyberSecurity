package io.kryptoblocks.msa.network.stream.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.kryptoblocks.msa.network.stream.service.business.NetworkControllerService;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkControllerServiceImpl;

@Configuration
public class ControllerConfig {
	
	@Bean
	NetworkControllerService networkControllerService() {
		return new NetworkControllerServiceImpl();
	}
	
}
