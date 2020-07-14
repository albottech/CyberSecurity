package io.kryptoblocks.msa.direct.stream.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kryptoblocks.msa.common.config.DirectStreamSourceConfig;
import io.kryptoblocks.msa.direct.stream.service.scheduling.MessageProduceScheduler;
 

@Configuration
@Import(DirectStreamSourceConfig.class)
public class StreamProducerConfig {
	
	@Bean
	MessageProduceScheduler messageProduceScheduler() {
		return new MessageProduceScheduler();		
	}

}
