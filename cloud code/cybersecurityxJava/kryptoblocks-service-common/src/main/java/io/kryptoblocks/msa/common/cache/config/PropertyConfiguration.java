package io.kryptoblocks.msa.common.cache.config;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyConfiguration.
 */
@Configuration
@ImportResource({"classpath:/annotation/spring-cache-context-min.xml"})
public class PropertyConfiguration {
	
	/** The Constant CACHE_DEFAULT_CONFIG_YML. */
	private static final String CACHE_DEFAULT_CONFIG_YML = "/annotation/yaml/CacheDefaultConfig.yml";
	
	/** The Constant CACHE_CUSTOM_CONFIG_YML. */
	private static final String CACHE_CUSTOM_CONFIG_YML = "/annotation/yaml/CacheCustomConfig.yml";	
	
	/** The Constant CACHE_SERVER_CONFIG_YML. */
	private static final String CACHE_SERVER_CONFIG_YML = "/annotation/yaml/CacheServerConfig.yml";
	
	/** The Constant CACHE_REGION_CONFIG_YML. */
	private static final String CACHE_REGION_CONFIG_YML = "/annotation/yaml/CacheRegionConfig.yml";	
	
	/** The Constant CACHE_ASYNC_EVENT_QUEUE_CONFIG_YML. */
	private static final String CACHE_ASYNC_EVENT_QUEUE_CONFIG_YML = "/annotation/yaml/CacheAsyncEventQueueConfig.yml";
	
	/** The Constant CACHE_STORAGE_CONFIG_YML. */
	private static final String CACHE_STORAGE_CONFIG_YML = "/annotation/yaml/CacheStorageConfig.yml";
	
	/** The Constant CACHE_QUERY_CONFIG_YML. */
	private static final String CACHE_QUERY_CONFIG_YML = "/annotation/yaml/CacheQueryConfig.yml";	
	
	/** The Constant CACHE_LOCATOR_CONFIG_YML. */
	private static final String CACHE_LOCATOR_CONFIG_YML = "/annotation/yaml/CacheLocatorConfig.yml";
	
	
	/**
	 * Cache property sources placeholder.
	 *
	 * @return the property sources placeholder configurer
	 */
	@Bean
	public PropertySourcesPlaceholderConfigurer cachePropertySourcesPlaceholder() {
	  PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
	  YamlPropertiesFactoryBean cacheDefaultConfigYML = new YamlPropertiesFactoryBean();
	  YamlPropertiesFactoryBean cacheCustomConfigYML = new YamlPropertiesFactoryBean();	
	  YamlPropertiesFactoryBean cacheServerConfigYML = new YamlPropertiesFactoryBean();	
	  YamlPropertiesFactoryBean cacheRegionConfigYML = new YamlPropertiesFactoryBean();	  
	  YamlPropertiesFactoryBean cacheQueryConfigYML = new YamlPropertiesFactoryBean();	
	  YamlPropertiesFactoryBean cacheLocatorConfigYML = new YamlPropertiesFactoryBean();	
	  
	  YamlPropertiesFactoryBean cacheAsyncEventQueueConfigYML = new YamlPropertiesFactoryBean();
	  YamlPropertiesFactoryBean cacheRegionStorageConfigYML = new YamlPropertiesFactoryBean();	  
	  cacheDefaultConfigYML.setResources(new ClassPathResource(CACHE_DEFAULT_CONFIG_YML));
	  cacheCustomConfigYML.setResources(new ClassPathResource(CACHE_CUSTOM_CONFIG_YML));
	  cacheServerConfigYML.setResources(new ClassPathResource(CACHE_SERVER_CONFIG_YML));
	  cacheRegionConfigYML.setResources(new ClassPathResource(CACHE_REGION_CONFIG_YML));	  
	  cacheAsyncEventQueueConfigYML.setResources(new ClassPathResource(CACHE_ASYNC_EVENT_QUEUE_CONFIG_YML));
	  cacheRegionStorageConfigYML.setResources(new ClassPathResource(CACHE_STORAGE_CONFIG_YML));
	  cacheQueryConfigYML.setResources(new ClassPathResource(CACHE_QUERY_CONFIG_YML));
	  cacheLocatorConfigYML.setResources(new ClassPathResource(CACHE_LOCATOR_CONFIG_YML));
	  
	  Properties cacheDefaultConfigProperties = cacheDefaultConfigYML.getObject();
	  Properties cacheCustomConfigProperties = cacheCustomConfigYML.getObject();
	  Properties cacheServerConfigProperties = cacheServerConfigYML.getObject();
	  Properties cacheRegionConfigProperties = cacheRegionConfigYML.getObject();
	  Properties cacheStorageConfigProperties = cacheRegionStorageConfigYML.getObject();
	  Properties cacheAsyncEventQueueConfigProperties = cacheAsyncEventQueueConfigYML.getObject();	  
	  Properties cacheQueryConfigProperties = cacheQueryConfigYML.getObject();
	  Properties cacheLocatorConfigProperties = cacheLocatorConfigYML.getObject();
	  
	  Properties mergedProperties = new Properties();
	  mergedProperties.putAll(cacheDefaultConfigProperties);
	  mergedProperties.putAll(cacheCustomConfigProperties);	 
	  mergedProperties.putAll(cacheServerConfigProperties);
	  mergedProperties.putAll(cacheRegionConfigProperties);	 
	  mergedProperties.putAll(cacheStorageConfigProperties);
	  mergedProperties.putAll(cacheAsyncEventQueueConfigProperties);
	  mergedProperties.putAll(cacheQueryConfigProperties);
	  mergedProperties.putAll(cacheLocatorConfigProperties);
	  propertySourcesPlaceholderConfigurer.setProperties(mergedProperties);	 
	  
	  return propertySourcesPlaceholderConfigurer;
	}
	
	/**
	 * Cache default properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheDefaultProperties() {
		YamlPropertiesFactoryBean cacheDefaultYML = new YamlPropertiesFactoryBean();
		cacheDefaultYML.setResources(new ClassPathResource(CACHE_DEFAULT_CONFIG_YML));
	  Properties cacheDefaultProperties = cacheDefaultYML.getObject();
	  return cacheDefaultProperties;
	}

	
	/**
	 * Cache custom properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheCustomProperties() {
		YamlPropertiesFactoryBean cacheYML = new YamlPropertiesFactoryBean();
		cacheYML.setResources(new ClassPathResource(CACHE_CUSTOM_CONFIG_YML));
	  Properties gemfireContextProperties = cacheYML.getObject();
	  return gemfireContextProperties;
	}
	
	/**
	 * Cache server properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheServerProperties() {
		YamlPropertiesFactoryBean cacheServerYML = new YamlPropertiesFactoryBean();
		cacheServerYML.setResources(new ClassPathResource(CACHE_SERVER_CONFIG_YML));
	  Properties cacheServerProperties = cacheServerYML.getObject();
	  return cacheServerProperties;
	}

	
	/**
	 * Cache region properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheRegionProperties() {
		YamlPropertiesFactoryBean cacheRegionYML = new YamlPropertiesFactoryBean();
		cacheRegionYML.setResources(new ClassPathResource(CACHE_REGION_CONFIG_YML));
	  Properties cacheRegionProperties = cacheRegionYML.getObject();
	  return cacheRegionProperties;
	}
	
	/**
	 * Cache async even queue properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheAsyncEvenQueueProperties() {
		YamlPropertiesFactoryBean cacheAsyncEvenQueueYML = new YamlPropertiesFactoryBean();
		cacheAsyncEvenQueueYML.setResources(new ClassPathResource(CACHE_ASYNC_EVENT_QUEUE_CONFIG_YML));
	  Properties cacheAsyncEventQueueProperties = cacheAsyncEvenQueueYML.getObject();
	  return cacheAsyncEventQueueProperties;
	}

	
	/**
	 * Cache storage queue properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheStorageQueueProperties() {
		YamlPropertiesFactoryBean casheStorageQueueYML = new YamlPropertiesFactoryBean();
		casheStorageQueueYML.setResources(new ClassPathResource(CACHE_STORAGE_CONFIG_YML));
	  Properties casheStorageQueueProperties = casheStorageQueueYML.getObject();
	  return casheStorageQueueProperties;
	}
	
	
	/**
	 * Cache query properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheQueryProperties() {
		YamlPropertiesFactoryBean cacheQueryYML = new YamlPropertiesFactoryBean();
		cacheQueryYML.setResources(new ClassPathResource(CACHE_QUERY_CONFIG_YML));
	  Properties cacheQueryProperties = cacheQueryYML.getObject();
	  return cacheQueryProperties;
	}
	
	/**
	 * Cache locator properties.
	 *
	 * @return the properties
	 */
	@Bean
	public Properties cacheLocatorProperties() {
		YamlPropertiesFactoryBean cacheLocatorYML = new YamlPropertiesFactoryBean();
		cacheLocatorYML.setResources(new ClassPathResource(CACHE_LOCATOR_CONFIG_YML));
	  Properties cacheLocatorProperties = cacheLocatorYML.getObject();
	  return cacheLocatorProperties;
	}


}
