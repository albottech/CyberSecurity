package io.kryptoblocks.msa.common.cache.repository;

 

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
 

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache storage config.
 */
@Data
public class CacheStorageConfig {
	
	/** The Queue size. */
	@Value("${STORAGE.QUEUE.SIZE}")
	String QueueSize;
	
	/** The auto compact. */
	@Value("${STORAGE.AUTO.COMPACT}")
	String autoCompact;
	
	/** The max operation log size. */
	@Value("${STORAGE.MAX.OPERATION.LOG.SIZE}")
	String maxOperationLogSize;
	
	/** The time interval. */
	@Value("${STORAGE.TIME.INTERVAL}")
	String timeInterval;
	
	/** The disk primary location. */
	@Value("${STORAGE.DISK.PRIMARY.LOCATION}")
	String diskPrimaryLocation;
	
	/** The disk primary location size. */
	@Value("${STORAGE.DISK.PRIMARY.LOCATION.SIZE}")
	String diskPrimaryLocationSize;
	
	/** The disk secondary location. */
	@Value("${STORAGE.DISK.SECONDARY.LOCATION}")
	String diskSecondaryLocation;
	
	/** The disk secondary location size. */
	@Value("${STORAGE.DISK.SECONDARY.LOCATION.SIZE}")
	String diskSecondaryLocationSize;
		
}
