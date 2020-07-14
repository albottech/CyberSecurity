/**
 * The gateway service filter configuration class. This class configure all the pre and post custom filter for the gateway service
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.gateway.filter.*;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterConfig.
 */
@Configuration
public class FilterConfig {
	
	/**
	 * Authorization filter bean creation method.
	 *
	 * @return the auth pre filter
	 */
	@Bean
	AuthPreFilter AuthFilter() {		
		return new AuthPreFilter();
	}
	
	/**
	 * Authorization filter bean creation method.
	 *
	 * @return the check post filter
	 */
	@Bean
	CheckPostFilter CheckPostFilter() {		
		return new CheckPostFilter();
	}

}
