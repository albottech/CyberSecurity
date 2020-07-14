/**
 * The application custom property bean definition. This class is used to load multiple YAML file properties from the service resource path and provide to 
 * the servce context and a single consolidated property set definition
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.properties;

import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;


// TODO: Auto-generated Javadoc
/**
 * The Class AppProperty.
 */
public class AppProperty {
	
		
	
	/**
	 * Gets the property sources placeholder configurer.
	 *
	 * @return the property sources placeholder configurer
	 */
	public PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
		  PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		  
		  //TODO
		  /*
		   * decide which property files are necessary to support custom business specific configuration at the service container level
		   * create the property YAML files in the project resource folder and use the following logic to load them
		  */
		  
		  /*
		  YamlPropertiesFactoryBean facebookConfigYML = new YamlPropertiesFactoryBean();		  
		  YamlPropertiesFactoryBean twitterConfigYML = new YamlPropertiesFactoryBean();
		  YamlPropertiesFactoryBean googleConfigYML = new YamlPropertiesFactoryBean();		  
		  YamlPropertiesFactoryBean localConfigYML = new YamlPropertiesFactoryBean();
		  
		    
		  facebookConfigYML.setResources(new ClassPathResource(FACEBOOK_CONFIG_YML));		   
		  twitterConfigYML.setResources(new ClassPathResource(TWITTER_CONFIG_YML));
		  localConfigYML.setResources(new ClassPathResource(LOCAL_CONFIG_YML));		   
		  googleConfigYML.setResources(new ClassPathResource(GOOGLE_CONFIG_YML));
		  	  
		  Properties facebookConfigProperties = facebookConfigYML.getObject();		  
		  Properties twitterConfigProperties = twitterConfigYML.getObject();
		  Properties localConfigProperties = localConfigYML.getObject();		  
		  Properties googleConfigProperties = googleConfigYML.getObject();
		  	  
		  Properties mergedProperties = new Properties();
		  mergedProperties.putAll(facebookConfigProperties);		   
		  mergedProperties.putAll(twitterConfigProperties);	 
		  mergedProperties.putAll(localConfigProperties);		   
		  mergedProperties.putAll(googleConfigProperties);	 
		  
		  propertySourcesPlaceholderConfigurer.setProperties(mergedProperties);	 
		  */
		  return propertySourcesPlaceholderConfigurer;
		}

	
	 
	
}
