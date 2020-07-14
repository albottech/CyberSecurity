package io.kryptoblocks.msa.udp.listner.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kryptoblocks.msa.common.config.CommonConfig;
import io.kryptoblocks.msa.common.config.NfrRepositoryConfig;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.util.TraceActivityMessageConverter;
import io.kryptoblocks.msa.network.repository.config.NetworkRepositoryConfig;
 

@Configuration
@Import({CommonConfig.class})
public class LibConfig {
	
	@Bean
	JsonUtil jsonUtil() {
		return new JsonUtil();
	}
	
	@Bean
	TraceActivityMessageConverter traceActivityMessageConverter() {
		return new TraceActivityMessageConverter();
	}
}
