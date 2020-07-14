package io.kryptoblocks.msa.common.cache.repository;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

 
// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache locator config.
 */
@Data
public class CacheLocatorConfig {
	
	 
			
	/** The localcount. */
	@Value("${CACHE.LOCATOR.LOCAL.COUNT}")	
	String localcount;	
	
	/** The local spec. */
	@Value("${CACHE.LOCATOR.LOCAL.SPEC}")	 
	String localSpec;
	
	
	/** The local host. */
	@Value("${CACHE.LOCATOR.LOCAL.HOST}")
	String localHost;
	
	/** The local port. */
	@Value("${CACHE.LOCATOR.LOCAL.PORT}")
	String localPort;	
	
	 
			
	/** The dockercount. */
	@Value("${CACHE.LOCATOR.DOCKER.COUNT}")	
	String dockercount;	
	
	/** The docker spec. */
	@Value("${CACHE.LOCATOR.DOCKER.SPEC}")	 
	String dockerSpec;
	
	
	/** The docker host. */
	@Value("${CACHE.LOCATOR.DOCKER.HOST}")
	String dockerHost;
	
	/** The docker port. */
	@Value("${CACHE.LOCATOR.DOCKER.PORT}")
	String dockerPort;	
	
	/** The docker enabled. */
	@Value("${CACHE.LOCATOR.DOCKER.ENABLED}")
	boolean dockerEnabled;	

	
}
