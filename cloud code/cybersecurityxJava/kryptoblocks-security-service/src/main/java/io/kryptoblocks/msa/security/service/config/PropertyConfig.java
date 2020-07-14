/**
 * The bean configuration class.
 * <p>
 *  
 * @author      Lxur LLC
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.config;

 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import io.kryptoblocks.msa.security.service.properties.AppProperty;

 
// TODO: Auto-generated Javadoc
/**
 * The Class PropertyConfig.
 */
@Configuration 
public class PropertyConfig {	
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConfig.class);
	   
	
	/**
	 * Property sources placeholder configurer.
	 *
	 * @return the property sources placeholder configurer
	 */
	@Bean
	PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){		
		AppProperty appProperty = new AppProperty();
		return appProperty.getPropertySourcesPlaceholderConfigurer();		
	}
		 

}
