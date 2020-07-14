package io.kryptoblocks.msa.common.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * The Class CacheXmlConfig.
 */
@Configuration
@ImportResource({"classpath:/spring-cache-context.xml"})
public class CacheXmlConfig {

}
