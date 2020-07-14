package io.kryptoblocks.msa.security.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kryptoblocks.msa.common.config.CommonConfig;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;



// TODO: Auto-generated Javadoc
/**
 * The Class LibConfig.
 */
@Configuration
@Import(CommonConfig.class)
public class LibConfig {
	
	 /**
 	 * Default sampler.
 	 *
 	 * @return the sampler
 	 */
 	@Bean
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	} 

}
