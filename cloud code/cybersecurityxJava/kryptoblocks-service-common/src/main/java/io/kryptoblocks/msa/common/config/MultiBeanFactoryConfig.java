package io.kryptoblocks.msa.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.factory.MultiBeanFactoryPostProcessor;

// TODO: Auto-generated Javadoc
/**
 * The Class MultiBeanFactoryConfig.
 */
@Configuration
public class MultiBeanFactoryConfig {
	
	/**
	 * Multi bean factory post processor.
	 *
	 * @return the multi bean factory post processor
	 */
	@Bean
	  public static MultiBeanFactoryPostProcessor multiBeanFactoryPostProcessor() {
	    return new MultiBeanFactoryPostProcessor();
	  }

}
