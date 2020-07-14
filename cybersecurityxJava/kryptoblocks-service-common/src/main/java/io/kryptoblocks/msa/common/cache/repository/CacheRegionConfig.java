package io.kryptoblocks.msa.common.cache.repository;

 

 

import org.springframework.beans.factory.annotation.Value;

 
import lombok.Data;
 

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache region config.
 */
@Data
public class CacheRegionConfig {
	
	/** The persistence. */
	@Value("${REGION.STORAGE.PERSISTENCE}")
	String persistence;
	
	/** The copies. */
	@Value("${REGION.STORAGE.NUMBER_OF_COPIES}")
	String copies;
	
	
	/** The domain model basic package name. */
	@Value("${REGION.DOMAIN.MODEL.PACKAGE}")
	String domainModelBasicPackageName;
	
	
	 
	
	
	
	
}
