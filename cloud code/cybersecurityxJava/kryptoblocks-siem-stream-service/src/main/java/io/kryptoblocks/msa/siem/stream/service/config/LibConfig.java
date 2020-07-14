package io.kryptoblocks.msa.siem.stream.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kryptoblocks.msa.common.config.CommonConfig;
import io.kryptoblocks.msa.common.config.NfrRepositoryConfig;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.util.TraceActivityMessageConverter;
import io.kryptoblocks.msa.siem.repository.config.SiemRepositoryConfig;
 

// TODO: Auto-generated Javadoc
/**
 * The Class LibConfig.
 */
@Configuration
@Import({CommonConfig.class, SiemRepositoryConfig.class, NfrRepositoryConfig.class})
public class LibConfig {
	
	/**
	 * Json util.
	 *
	 * @return the json util
	 */
	@Bean
	JsonUtil jsonUtil() {
		return new JsonUtil();
	}
	
	/**
	 * Trace activity message converter.
	 *
	 * @return the trace activity message converter
	 */
	@Bean
	TraceActivityMessageConverter traceActivityMessageConverter() {
		return new TraceActivityMessageConverter();
	}
}
