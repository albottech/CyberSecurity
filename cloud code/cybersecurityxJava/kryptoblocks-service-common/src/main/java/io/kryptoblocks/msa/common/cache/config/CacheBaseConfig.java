package io.kryptoblocks.msa.common.cache.config;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.EvictionAttributesFactoryBean;
import org.springframework.data.gemfire.EvictionPolicyType;
import org.springframework.data.gemfire.ExpirationAttributesFactoryBean;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.GemfireTransactionManager;
import org.springframework.data.gemfire.IndexFactoryBean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.client.PoolFactoryBean;
import org.springframework.data.gemfire.server.CacheServerFactoryBean;
import org.springframework.data.gemfire.support.ConnectionEndpointList;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;

import com.gemstone.gemfire.cache.Cache;
 
import com.gemstone.gemfire.cache.DataPolicy;
import com.gemstone.gemfire.cache.DiskStore;
import com.gemstone.gemfire.cache.DiskStoreFactory;
import com.gemstone.gemfire.cache.EvictionAction;
import com.gemstone.gemfire.cache.EvictionAttributes;
import com.gemstone.gemfire.cache.ExpirationAction;
import com.gemstone.gemfire.cache.ExpirationAttributes;
import com.gemstone.gemfire.cache.PartitionAttributes;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventQueue;
import com.gemstone.gemfire.distributed.Locator;
import com.gemstone.gemfire.pdx.ReflectionBasedAutoSerializer;

import io.kryptoblocks.msa.common.cache.pdx.PdxObject;
import io.kryptoblocks.msa.common.cache.repository.CacheAsyncEventListener;
import io.kryptoblocks.msa.common.cache.repository.CacheAsyncEventQueueConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheCustomConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheDefaultConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheInitializer;
import io.kryptoblocks.msa.common.cache.repository.CacheLocatorConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheRegionConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheServerConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheServerPortGenerator;
import io.kryptoblocks.msa.common.cache.repository.CacheStorageConfig;
import io.kryptoblocks.msa.common.exception.ConfigException;
import io.kryptoblocks.msa.common.factory.MultiBeanFactory;
 
 

// TODO: Auto-generated Javadoc
/**
 * The Class CacheBaseConfig.
 */
@Configuration 
@Import({PropertyConfiguration.class})
public class CacheBaseConfig  {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CacheBaseConfig.class);
	
	 
	/** The Constant CACHE_ASYNC_EVENT_QUEUE_STORE_NAME. */
	private static final String CACHE_ASYNC_EVENT_QUEUE_STORE_NAME = "cacheAsyncEventQueueStore";
	
	/** The Constant REPLICATED_STORE_NAME. */
	private static final String REPLICATED_STORE_NAME = "replicatedStore";								
	
	/** The Constant PARTITIONED_STORE_NAME. */
	private static final String PARTITIONED_STORE_NAME = "partitionedStore";	
	
	/** The Constant PARTITIONED_REGION_NAME. */
	private static final String PARTITIONED_REGION_NAME = "partitionedRegion";	
	
	/** The Constant REPLICATED_REGION_NAME. */
	private static final String REPLICATED_REGION_NAME = "replicatedRegion"; 
	
	/** The Constant PDX_STORE_NAME. */
	private static final String PDX_STORE_NAME = "pdxStore";
	
	/** The Constant NON_PERSISTENT_REGION_ENTRY_TIMEOUT_DURATION. */
	private static final int  NON_PERSISTENT_REGION_ENTRY_TIMEOUT_DURATION = 120;
	
	/** The cache default config original. */
	@Autowired
	Properties cacheDefaultConfigOriginal;
	
	/**
	 * Cache custom properties.
	 *
	 * @return the cache custom properties
	 */
	@Bean	
	@ConfigurationProperties(prefix = "cache.custom")
	public CacheCustomProperties cacheCustomProperties() {
	    return new CacheCustomProperties();
	}

	/**
	 * Cache default config.
	 *
	 * @return the cache default config
	 */
	@Bean
	CacheDefaultConfig cacheDefaultConfig() {
		CacheDefaultConfig cacheDefaultConfig = new CacheDefaultConfig();
		return cacheDefaultConfig;
	}
	
	/**
	 * Cache custom config.
	 *
	 * @return the cache custom config
	 */
	@Bean
	CacheCustomConfig cacheCustomConfig() {
		CacheCustomConfig cacheCustomConfig = new CacheCustomConfig();
		return cacheCustomConfig;
	}
	
	/**
	 * Cache server config.
	 *
	 * @return the cache server config
	 */
	@Bean
	CacheServerConfig cacheServerConfig() {
		CacheServerConfig cacheServerConfig = new CacheServerConfig();
		return cacheServerConfig;
	}
	
	/**
	 * Cache async event queue config.
	 *
	 * @return the cache async event queue config
	 */
	@Bean
	CacheAsyncEventQueueConfig cacheAsyncEventQueueConfig() {
		CacheAsyncEventQueueConfig cacheAsyncEventQueueConfig = new CacheAsyncEventQueueConfig();
		return cacheAsyncEventQueueConfig;
	}
	
	/**
	 * Cache storage config.
	 *
	 * @return the cache storage config
	 */
	@Bean
	CacheStorageConfig cacheStorageConfig() {
		CacheStorageConfig cacheStorageConfig = new CacheStorageConfig();
		return cacheStorageConfig;
	}

	/**
	 * Cache server port generator.
	 *
	 * @return the cache server port generator
	 */
	@Bean
	CacheServerPortGenerator cacheServerPortGenerator() {
		CacheServerPortGenerator cacheServerPortGenerator = new CacheServerPortGenerator();
		return cacheServerPortGenerator;
	}

	/**
	 * Cache region config.
	 *
	 * @return the cache region config
	 */
	@Bean
	CacheRegionConfig cacheRegionConfig() {
		CacheRegionConfig cacheRegionConfig = new CacheRegionConfig();
		return cacheRegionConfig;
	}
	
	/**
	 * Cache locator config.
	 *
	 * @return the cache locator config
	 */
	@Bean
	CacheLocatorConfig cacheLocatorConfig() {
		CacheLocatorConfig cacheLocatorConfig = new CacheLocatorConfig();
		return cacheLocatorConfig;
	}
	
	

	/**
	 * Pdx serializer.
	 *
	 * @param cacheRegionConfig the cache region config
	 * @return the reflection based auto serializer
	 */
	@SuppressWarnings("deprecation")
	@Bean
	ReflectionBasedAutoSerializer pdxSerializer(@Qualifier("cacheRegionConfig") CacheRegionConfig cacheRegionConfig) {
		List<String> domainModelList = new ArrayList<String>();
		domainModelList.add(cacheRegionConfig.getDomainModelBasicPackageName());
		ReflectionBasedAutoSerializer reflectionBasedAutoSerializer = new ReflectionBasedAutoSerializer(
				domainModelList);
		
		return reflectionBasedAutoSerializer;
	}

	/**
	 * Data cache.
	 *
	 * @param cacheCustomConfig the cache custom config
	 * @param pdxSerializer the pdx serializer
	 * @return the cache factory bean
	 */
	@Bean
	CacheFactoryBean dataCache(@Qualifier("cacheCustomConfig") CacheCustomConfig cacheCustomConfig,
			@Qualifier("pdxSerializer") ReflectionBasedAutoSerializer pdxSerializer) {
		
		String locatorProperty = getLocatorProperty();		
		cacheDefaultConfigOriginal.put("locators", "localhost[10334]");
		CacheFactoryBean dataCache = new CacheFactoryBean();
		dataCache.setProperties(cacheDefaultConfigOriginal);		 
		
		//dataCache.setClose(new Boolean(cacheCustomConfig.getCacheCloseIndicator()).booleanValue());
		dataCache.setClose(false);
		dataCache.setCopyOnRead(new Boolean(cacheCustomConfig.getCacheCopyOnRead()));
		dataCache.setCriticalHeapPercentage(new Float(cacheCustomConfig.getCacheCriticalHeapPercentage()));
		dataCache.setEvictionHeapPercentage(new Float(cacheCustomConfig.getCacheEvictionHeapPercentage()));
		dataCache.setEnableAutoReconnect(new Boolean(cacheCustomConfig.getCacheEnableAutoCorrect()));
		dataCache.setLockLease(new Integer(cacheCustomConfig.getCacheLockLease()));
		dataCache.setLockTimeout(new Integer(cacheCustomConfig.getCacheLockTimeout()));
		dataCache.setMessageSyncInterval(new Integer(cacheCustomConfig.getCacheMessageSyncInterval()));
		dataCache.setPdxSerializer(pdxSerializer);
		dataCache.setPdxPersistent(new Boolean(true));
		 
		//dataCache.setPdxPersistent(new Boolean(cacheCustomConfig.getCachePdxPersistent()));		 
		dataCache.setPdxReadSerialized(new Boolean(cacheCustomConfig.getCachePdxReadSerialized()));
		dataCache.setPdxIgnoreUnreadFields(new Boolean(cacheCustomConfig.getCachePdxIgnoreUnreadFields()));
		dataCache.setSearchTimeout(new Integer(cacheCustomConfig.getCacheSearchTimeOut()));
		dataCache.setUseClusterConfiguration(new Boolean(cacheCustomConfig.getCacheUseClusterConfiguration()));
		
		return dataCache;
	}

	/**
	 * Data cache server.
	 *
	 * @param dataCache the data cache
	 * @param cacheServerConfig the cache server config
	 * @param cacheServerPortGenerator the cache server port generator
	 * @return the cache server factory bean
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Bean
	CacheServerFactoryBean dataCacheServer(@Qualifier("dataCache") Cache dataCache,
			@Qualifier("cacheServerConfig") CacheServerConfig cacheServerConfig,
			@Qualifier("cacheServerPortGenerator") CacheServerPortGenerator cacheServerPortGenerator) throws IOException {

		CacheServerFactoryBean dataCacheServer = new CacheServerFactoryBean();

		dataCacheServer.setAutoStartup(new Boolean(cacheServerConfig.getAutoStartup()).booleanValue());
		dataCacheServer.setBindAddress(cacheServerConfig.getBindAddddress());
		int portGenMinValue = new Integer(cacheServerConfig.getPortRangeMinValue()).intValue();
		int portGenMaxValue = new Integer(cacheServerConfig.getPortRangeMaxValue()).intValue();
		int dynamicPort = cacheServerPortGenerator.generatePort(portGenMinValue, portGenMaxValue);		 
		dataCacheServer.setPort(dynamicPort);
		dataCacheServer.setCache(dataCache);
		dataCacheServer.setHostNameForClients(cacheServerConfig.getHostForClients());
		dataCacheServer.setLoadPollInterval(new Long(cacheServerConfig.getPollInterval()).longValue());
		dataCacheServer.setMaxConnections(new Integer(cacheServerConfig.getMaxConnections()).intValue());
		dataCacheServer.setMaxThreads(new Integer(cacheServerConfig.getMaxThreads()).intValue());
		dataCacheServer.setMaxTimeBetweenPings(new Integer(cacheServerConfig.getMaxTimeBetweenPings()).intValue());
		String[] serverGrpList = new String [1];
		serverGrpList[0] = cacheServerConfig.getGroups();
		dataCacheServer.setServerGroups(serverGrpList);
		return dataCacheServer;
	}
		 
	
	/**
	 * Locator pool.
	 *
	 * @param cacheLocatorConfig the cache locator config
	 * @return the pool factory bean
	 * @throws UnknownHostException the unknown host exception
	 */
	@Bean
	PoolFactoryBean locatorPool(@Qualifier("cacheLocatorConfig")CacheLocatorConfig cacheLocatorConfig) throws UnknownHostException {
		PoolFactoryBean locatorPool = new PoolFactoryBean();	
		InetAddress host = InetAddress.getByName(cacheLocatorConfig.getLocalHost());
		int port = new Integer(cacheLocatorConfig.getLocalPort()).intValue();		
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
		ConnectionEndpointList locatorConnectionPoint = ConnectionEndpointList.from(inetSocketAddress);		 	
		locatorPool.setLocators(locatorConnectionPoint);
		locatorPool.setSubscriptionEnabled(true);
		return locatorPool;		
	}
	
	/**
	 * Cache transaction manager.
	 *
	 * @param dataCache the data cache
	 * @return the gemfire transaction manager
	 */
	@Bean
	GemfireTransactionManager cacheTransactionManager(@Qualifier("dataCache") Cache dataCache){		
		GemfireTransactionManager cacheTransactionManager = new GemfireTransactionManager();
		cacheTransactionManager.setCache(dataCache);		
		return cacheTransactionManager;				
	}
	 
	/**
	 * Cache async event listener.
	 *
	 * @return the cache async event listener
	 */
	@Bean
	CacheAsyncEventListener cacheAsyncEventListener() {
		CacheAsyncEventListener cacheAsyncEventListener = new CacheAsyncEventListener();
		return cacheAsyncEventListener;
	}

	/**
	 * Cache async event queue.
	 *
	 * @param dataCache the data cache
	 * @param cacheAsyncEventQueueStore the cache async event queue store
	 * @param cacheAsyncEventListener the cache async event listener
	 * @param cacheAsyncEventQueueConfig the cache async event queue config
	 * @return the async event queue factory bean
	 */
	@Bean
	AsyncEventQueueFactoryBean cacheAsyncEventQueue(@Qualifier("dataCache") Cache dataCache,			
			@Qualifier("cacheAsyncEventQueueStore") DiskStore cacheAsyncEventQueueStore,
			@Qualifier("cacheAsyncEventListener") CacheAsyncEventListener cacheAsyncEventListener,
			@Qualifier("cacheAsyncEventQueueConfig") CacheAsyncEventQueueConfig cacheAsyncEventQueueConfig) {
		
		AsyncEventQueueFactoryBean cacheAsyncEventQueue = new AsyncEventQueueFactoryBean(dataCache);
		cacheAsyncEventQueue.setBatchSize(new Integer(cacheAsyncEventQueueConfig.getBatchSize()).intValue());
		cacheAsyncEventQueue.setPersistent(new Boolean(cacheAsyncEventQueueConfig.getPersistent()).booleanValue());
		cacheAsyncEventQueue.setParallel(new Boolean(cacheAsyncEventQueueConfig.getParallel()).booleanValue());
		cacheAsyncEventQueue.setDiskStoreRef(cacheAsyncEventQueueStore.getName());
		cacheAsyncEventQueue.setMaximumQueueMemory(new Integer(cacheAsyncEventQueueConfig.getMaxQueueMemory()));
		cacheAsyncEventQueue.setAsyncEventListener(cacheAsyncEventListener); 
		 
		return cacheAsyncEventQueue;
	}
	
	 

	/**
	 * Cache async event queue store.
	 *
	 * @param dataCache the data cache
	 * @param cacheStorageConfig the cache storage config
	 * @return the disk store
	 */
	@Bean
	DiskStore cacheAsyncEventQueueStore(@Qualifier("dataCache") Cache dataCache,
			@Qualifier("cacheStorageConfig") CacheStorageConfig cacheStorageConfig) {
		
		DiskStore returnDiskStore = null;
		
		returnDiskStore = dataCache.findDiskStore(CACHE_ASYNC_EVENT_QUEUE_STORE_NAME);
		
		if (returnDiskStore == null) {			
			returnDiskStore = createDiskStore(CACHE_ASYNC_EVENT_QUEUE_STORE_NAME,  cacheStorageConfig, dataCache);
		}
		return returnDiskStore;
	}
	
	
	/**
	 * Replicated store.
	 *
	 * @param dataCache the data cache
	 * @param cacheStorageConfig the cache storage config
	 * @return the disk store
	 */
	@Bean
	DiskStore replicatedStore(@Qualifier("dataCache") Cache dataCache,
			@Qualifier("cacheStorageConfig") CacheStorageConfig cacheStorageConfig) {

		DiskStore returnDiskStore = null;
		returnDiskStore = dataCache.findDiskStore(REPLICATED_STORE_NAME);
		if (returnDiskStore == null) {			
			returnDiskStore = createDiskStore(REPLICATED_STORE_NAME,  cacheStorageConfig, dataCache);
		}
		return returnDiskStore;	
	}
	
	/**
	 * Partitioned store.
	 *
	 * @param dataCache the data cache
	 * @param cacheStorageConfig the cache storage config
	 * @return the disk store
	 */
	@Bean
	DiskStore partitionedStore(@Qualifier("dataCache") Cache dataCache,
			@Qualifier("cacheStorageConfig") CacheStorageConfig cacheStorageConfig) {

		DiskStore returnDiskStore = null;
		returnDiskStore = dataCache.findDiskStore(PARTITIONED_STORE_NAME);
		if (returnDiskStore == null) {			
			returnDiskStore = createDiskStore(PARTITIONED_STORE_NAME,  cacheStorageConfig, dataCache);
		}
		return returnDiskStore;		
	}
	
	 
	/**
	 * Pdx store.
	 *
	 * @param dataCache the data cache
	 * @param cacheStorageConfig the cache storage config
	 * @return the disk store
	 */
	@Bean()
	DiskStore pdxStore(@Qualifier("dataCache") Cache dataCache,
			@Qualifier("cacheStorageConfig") CacheStorageConfig cacheStorageConfig) {
		
		DiskStore returnDiskStore = null;
		returnDiskStore = dataCache.findDiskStore(PDX_STORE_NAME);
		if (returnDiskStore == null) {			
			returnDiskStore = createDiskStore(PDX_STORE_NAME,  cacheStorageConfig, dataCache);
		}
		return returnDiskStore;
		
	}
	
	/**
	 * Partitioned region.
	 *
	 * @param dataCache the data cache
	 * @param cacheRegionConfig the cache region config
	 * @param partitionedStore the partitioned store
	 * @param partitionedRegionAttributes the partitioned region attributes
	 * @return the partitioned region factory bean
	 * @throws Exception the exception
	 */
	@Bean()
	PartitionedRegionFactoryBean partitionedRegion(@Qualifier("dataCache") Cache dataCache, 
			@Qualifier("cacheRegionConfig") CacheRegionConfig cacheRegionConfig, 
			@Qualifier("partitionedStore") DiskStore partitionedStore,
			@Qualifier("partitionedRegionAttributes")RegionAttributesFactoryBean partitionedRegionAttributes) throws Exception{
		PartitionedRegionFactoryBean partitionedRegion = new PartitionedRegionFactoryBean();
		partitionedRegion.setCache(dataCache);
		//partitionedRegion.setPersistent(new Boolean(cacheRegionConfig.getPersistence()));
		partitionedRegion.setPersistent(true);
		partitionedRegion.setName(PARTITIONED_REGION_NAME);
		 
		partitionedRegion.setAttributes(partitionedRegionAttributes.getObject());
		partitionedRegion.setDiskStoreName(partitionedStore.getName());
		 
		return partitionedRegion;
	}
		
	/**
	 * Replicated region.
	 *
	 * @param dataCache the data cache
	 * @param cacheRegionConfig the cache region config
	 * @param replicatedStore the replicated store
	 * @param cacheAsyncEventQueue the cache async event queue
	 * @return the replicated region factory bean
	 * @throws Exception the exception
	 */
	@Bean()
	ReplicatedRegionFactoryBean replicatedRegion(@Qualifier("dataCache") Cache dataCache, @Qualifier("cacheRegionConfig") CacheRegionConfig cacheRegionConfig, 
			@Qualifier("replicatedStore") DiskStore replicatedStore,
			@Qualifier("cacheAsyncEventQueue")AsyncEventQueueFactoryBean cacheAsyncEventQueue
			) throws Exception{
		ReplicatedRegionFactoryBean replicatedRegion = new ReplicatedRegionFactoryBean();
		replicatedRegion.setCache(dataCache);
		//replicatedRegion.setPersistent(new Boolean(cacheRegionConfig.getPersistence()));
		replicatedRegion.setPersistent(true);
		replicatedRegion.setName(REPLICATED_REGION_NAME);		 
		replicatedRegion.setDiskStoreName(replicatedStore.getName());
		
		List<AsyncEventQueue> aQueueList = new ArrayList<AsyncEventQueue>();
		aQueueList.add(cacheAsyncEventQueue.getObject());
		replicatedRegion.setAsyncEventQueues(aQueueList.toArray(new AsyncEventQueue[aQueueList.size()]));
		return replicatedRegion;
	}
	
	 
	
	/**
	 * Partitioned region template.
	 *
	 * @param partitionedRegion the partitioned region
	 * @return the gemfire template
	 */
	@Bean 
	GemfireTemplate partitionedRegionTemplate(@Qualifier("partitionedRegion") Region partitionedRegion){
		GemfireTemplate partitionedRegionTemplate = new GemfireTemplate(partitionedRegion);		
		partitionedRegionTemplate.setRegion(partitionedRegion);
		return partitionedRegionTemplate ;
	}
	
	/**
	 * Replicated region template.
	 *
	 * @param replicatedRegion the replicated region
	 * @return the gemfire template
	 */
	@Bean 
	GemfireTemplate replicatedRegionTemplate(@Qualifier("replicatedRegion")  Region replicatedRegion){
		GemfireTemplate replicatedRegionTemplate = new GemfireTemplate(replicatedRegion);		
		replicatedRegionTemplate.setRegion(replicatedRegion);
		return replicatedRegionTemplate ;
	}
		 
	/**
	 * Partitioned region attributes.
	 *
	 * @param partitionAttributes the partition attributes
	 * @param evictionAttributes the eviction attributes
	 * @param expirationAttributes the expiration attributes
	 * @return the region attributes factory bean
	 */
	@SuppressWarnings("deprecation")
	@Bean
	  public RegionAttributesFactoryBean partitionedRegionAttributes(@SuppressWarnings("rawtypes") PartitionAttributes partitionAttributes,
	      EvictionAttributes evictionAttributes, ExpirationAttributes expirationAttributes) {

	    RegionAttributesFactoryBean partitionedRegionAttributes = new RegionAttributesFactoryBean();
	    partitionedRegionAttributes.setRegionTimeToLive(expirationAttributes);
	    partitionedRegionAttributes.setEvictionAttributes(evictionAttributes);
	    //partitionedRegionAttributes.setPartitionAttributes(partitionAttributes);
	    partitionedRegionAttributes.setDataPolicy(DataPolicy.PERSISTENT_PARTITION);
	    return partitionedRegionAttributes;
	  }
	
	/**
	 * Replicated region attributes.
	 *
	 * @param partitionAttributes the partition attributes
	 * @param evictionAttributes the eviction attributes
	 * @param expirationAttributes the expiration attributes
	 * @return the region attributes factory bean
	 */
	@SuppressWarnings("deprecation")
	@Bean
	  public RegionAttributesFactoryBean replicatedRegionAttributes(@SuppressWarnings("rawtypes") PartitionAttributes partitionAttributes,
	      EvictionAttributes evictionAttributes, ExpirationAttributes expirationAttributes) {

	    RegionAttributesFactoryBean replicatedRegionAttributes = new RegionAttributesFactoryBean();
	    replicatedRegionAttributes.setRegionTimeToLive(expirationAttributes);
	    replicatedRegionAttributes.setEvictionAttributes(evictionAttributes);
	    //replicatedRegionAttributes.setPartitionAttributes(partitionAttributes);	    
	    replicatedRegionAttributes.setDataPolicy(DataPolicy.PERSISTENT_PARTITION);
	    return replicatedRegionAttributes;
	  }
	
	
	/**
	 * Non persistent region attributes.
	 *
	 * @param nonPersistentEvictionAttributes the non persistent eviction attributes
	 * @param nonPersistentExpirationAttributes the non persistent expiration attributes
	 * @param evictionAttributes the eviction attributes
	 * @return the region attributes factory bean
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public RegionAttributesFactoryBean nonPersistentRegionAttributes(
			@Qualifier("nonPersistentEvictionAttributes")EvictionAttributes nonPersistentEvictionAttributes, 
			@Qualifier("nonPersistentExpirationAttributes")ExpirationAttributes nonPersistentExpirationAttributes,
			@Qualifier("evictionAttributes")EvictionAttributesFactoryBean evictionAttributes
			) {

	    RegionAttributesFactoryBean nonPersistentRegionAttributes = new RegionAttributesFactoryBean();
	    nonPersistentRegionAttributes.setEntryTimeToLive(nonPersistentExpirationAttributes);
	    nonPersistentRegionAttributes.setEvictionAttributes(evictionAttributes.getObject());
	    nonPersistentRegionAttributes.setDataPolicy(DataPolicy.DEFAULT);
	    return nonPersistentRegionAttributes;
	  }
	
	 /**
 	 * Partition attributes.
 	 *
 	 * @return the partition attributes factory bean
 	 */
 	@Bean
	  public PartitionAttributesFactoryBean partitionAttributes()
	  {
	    PartitionAttributesFactoryBean partitionAttributes = new PartitionAttributesFactoryBean();
	    return partitionAttributes;
	  }
	   
	   
	 /**
 	 * Eviction attributes.
 	 *
 	 * @return the eviction attributes factory bean
 	 */
 	@Bean
	  public EvictionAttributesFactoryBean evictionAttributes()
	  {
	    //modify		 
		EvictionAttributesFactoryBean evictionAttributes = new EvictionAttributesFactoryBean();
	    evictionAttributes.setAction(EvictionAction.OVERFLOW_TO_DISK);	     
	    evictionAttributes.setType(EvictionPolicyType.MEMORY_SIZE);
	    return evictionAttributes;
	  }
	  
	 /**
 	 * Expiration attributes.
 	 *
 	 * @return the expiration attributes factory bean
 	 */
 	@Bean
	  public ExpirationAttributesFactoryBean expirationAttributes()
	  {
		//modify
		 ExpirationAttributesFactoryBean expirationAttributes = new ExpirationAttributesFactoryBean();
	    expirationAttributes.setAction(ExpirationAction.LOCAL_DESTROY);	    
	    return expirationAttributes;
	  }
	 
	 /**
 	 * Non persistent expiration attributes.
 	 *
 	 * @return the expiration attributes factory bean
 	 */
 	@Bean
	  public ExpirationAttributesFactoryBean nonPersistentExpirationAttributes()
	  {
		//modify
		ExpirationAttributesFactoryBean nonPersistentExpirationAttributes = new ExpirationAttributesFactoryBean();
		nonPersistentExpirationAttributes.setAction(ExpirationAction.DESTROY);	 
		nonPersistentExpirationAttributes.setTimeout(new Integer(NON_PERSISTENT_REGION_ENTRY_TIMEOUT_DURATION));
	    return nonPersistentExpirationAttributes;
	  }
	 
	 /**
 	 * Non persistent eviction attributes.
 	 *
 	 * @return the eviction attributes factory bean
 	 */
 	@Bean
	  public EvictionAttributesFactoryBean nonPersistentEvictionAttributes()
	  {
	    //modify		 
		EvictionAttributesFactoryBean nonPersistentEvictionAttributes = new EvictionAttributesFactoryBean();
		nonPersistentEvictionAttributes.setAction(EvictionAction.DEFAULT_EVICTION_ACTION);	     
		nonPersistentEvictionAttributes.setType(EvictionPolicyType.MEMORY_SIZE);
	    return nonPersistentEvictionAttributes;
	  }
	  
	 
	 /**
 	 * Index.
 	 *
 	 * @param dataCache the data cache
 	 * @return the index factory bean
 	 */
 	//@Bean
	  public IndexFactoryBean index(Cache dataCache) {
	    
	    
		//correct later
		  
		//IndexFactoryBean index = new IndexFactoryBean();
	    //index.setCache(dataCache);
	    //index.setName("CustomerIdIdx");
	    //index.setExpression("id");
	    //index.setFrom("/Customers");
	    //index.setType(IndexType.PRIMARY_KEY);
		  return null;
	  }
	
	  
	/**
	 * Cache initializer.
	 *
	 * @return the cache initializer
	 */
	@Bean ()
	CacheInitializer CacheInitializer(){
		CacheInitializer cacheInitializer = new CacheInitializer();
		return cacheInitializer;
	}
	
	 
	
	/**
	 * Creates the disk store.
	 *
	 * @param name the name
	 * @param cacheStorageConfig the cache storage config
	 * @param dataCache the data cache
	 * @return the disk store
	 */
	private DiskStore createDiskStore(String name,  CacheStorageConfig cacheStorageConfig, Cache dataCache) {

		DiskStore returDiskStore = null;
		returDiskStore = dataCache.findDiskStore(name);
		if (returDiskStore == null) {
			DiskStoreFactory diskStoreFactory = dataCache.createDiskStoreFactory();
			diskStoreFactory.setAutoCompact(new Boolean(cacheStorageConfig.getAutoCompact()));
			//File array for primary and secondary file 
			File[] diskStoreFileArray = new File[2];
			diskStoreFileArray[0] = new File(cacheStorageConfig.getDiskPrimaryLocation() + "/" + name);
			diskStoreFileArray[1] = new File(cacheStorageConfig.getDiskSecondaryLocation()+ "/" + name);
			//File size array for primary and secondary
			int[] diskStoreSizeArray = new int[2];
			diskStoreSizeArray[0] = (new Integer(cacheStorageConfig.getDiskPrimaryLocationSize()));
			diskStoreSizeArray[1] = (new Integer(cacheStorageConfig.getDiskSecondaryLocationSize()));
			diskStoreFactory.setDiskDirsAndSizes(diskStoreFileArray, diskStoreSizeArray);
			//diskStoreFactory.setDiskUsageCriticalPercentage(cacheStorageConfig.);
			//diskStoreFactory.setDiskUsageWarningPercentage((new Long());
			diskStoreFactory.setMaxOplogSize(new Long(cacheStorageConfig.getMaxOperationLogSize()));
			diskStoreFactory.setQueueSize(new Integer(cacheStorageConfig.getQueueSize()));
			diskStoreFactory.setTimeInterval((new Long(cacheStorageConfig.getTimeInterval())));
			//diskStoreFactory.setWriteBufferSize(new Integer));
			returDiskStore = diskStoreFactory.create(name);
		}
		return returDiskStore;
	}
	
	/**
	 * Gets the locator property.
	 *
	 * @return the locator property
	 */
	private String getLocatorProperty(){
		if(cacheLocatorConfig().isDockerEnabled()){
			String[] locatorSpecSplit = cacheLocatorConfig().getDockerSpec().split("\\|");
			
			return locatorSpecSplit[0];			
		}else{
			String[] locatorSpecSplit = cacheLocatorConfig().getLocalSpec().split("\\|");
			return locatorSpecSplit[0];	
			
		}
	}
	
}