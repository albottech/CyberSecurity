package io.kryptoblocks.msa.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.util.TraceActivityMessageConverter;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonDispatcherConfig.
 */
@Configuration
@Import({CommonElkConfig.class, CommonStreamSinkConfig.class, NfrRepositoryConfig.class})
public class CommonDispatcherConfig {
	
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
