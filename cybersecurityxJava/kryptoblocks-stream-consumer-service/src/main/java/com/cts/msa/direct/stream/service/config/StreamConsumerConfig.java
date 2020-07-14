package com.cts.msa.direct.stream.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.kryptoblocks.msa.common.config.DirectStreamSinkConfig;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.common.config.MultiBeanFactoryConfig;
 


@Configuration
@Import({DirectStreamSinkConfig.class, MultiBeanFactoryConfig.class})
public class StreamConsumerConfig {
	
	@Bean
	JsonUtil jsonUtil() {
		return new JsonUtil();
	}

}
