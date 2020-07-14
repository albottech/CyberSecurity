package io.kryptoblocks.msa.common.cache.repository;


 

import org.springframework.beans.factory.annotation.Value;

 
import lombok.Data;
 

 
// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache custom config.
 */
@Data
public class CacheCustomConfig {
	
	/** The cache close indicator. */
	@Value("${CACHE.CLOSE.INDICATOR}")
	String cacheCloseIndicator;
	
	/** The cache copy on read. */
	@Value("${CACHE.COPY.ON.READ}")
	String cacheCopyOnRead;
	
	/** The cache critical heap percentage. */
	@Value("${CACHE.CRITICAL.HEAP.PERCENTAGE}")
	String cacheCriticalHeapPercentage;
	
	/** The cache eviction heap percentage. */
	@Value("${CACHE.EVICTION.HEAP.PERCENTAGE}")
	String cacheEvictionHeapPercentage;
	
	/** The cache enable auto correct. */
	@Value("${CACHE.ENABLE.AUTO.CORRECT}")
	String cacheEnableAutoCorrect;
	
	/** The cache lock lease. */
	@Value("${CACHE.LOCK.LEASE}")
	String cacheLockLease;
	
	/** The cache lock timeout. */
	@Value("${CACHE.LOCK.TIMEOUT}")
	String cacheLockTimeout;
	
	/** The cache message sync interval. */
	@Value("${CACHE.MESSAGE.SYNC.INTERVAL}")
	String cacheMessageSyncInterval;
	
	/** The cache pdx persistent. */
	@Value("${CACHE.PDX.PERSISTENT}")
	String cachePdxPersistent;
	
	/** The cache pdx read serialized. */
	@Value("${CACHE.PDX.READ.SERIALIZED}")
	String cachePdxReadSerialized;
	
	/** The cache pdx ignore unread fields. */
	@Value("${CACHE.PDX.IGNORE.UNREAD.FIELDS}")
	String cachePdxIgnoreUnreadFields;
	
	/** The cache search time out. */
	@Value("${CACHE.SEARCH.TIMEOUT}")
	String cacheSearchTimeOut;
	
	/** The cache use cluster configuration. */
	@Value("${CACHE.USE.CLUSTER.CONFIGURATION}")
	String cacheUseClusterConfiguration;
	
	/** The cache lazy init. */
	@Value("${CACHE.LAZY.INIT}")
	String cacheLazyInit;
	
	
	
}
