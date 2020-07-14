package io.kryptoblocks.msa.common.config;


 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.model.ExceptionActivityEntity;
 

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionConfig.
 */
@Configuration
public class ExceptionConfig {
	
	/**
	 * Exception entity.
	 *
	 * @return the exception activity entity
	 */
	@Bean
	ExceptionActivityEntity exceptionEntity() {
		return new ExceptionActivityEntity();
	}
	
	 

}
