package io.kryptoblocks.msa.common.cache.function;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.DataPolicy;
import com.gemstone.gemfire.cache.DiskStore;
import com.gemstone.gemfire.cache.DiskStoreFactory;
import com.gemstone.gemfire.cache.EvictionAction;
import com.gemstone.gemfire.cache.EvictionAttributes;
import com.gemstone.gemfire.cache.ExpirationAction;
import com.gemstone.gemfire.cache.ExpirationAttributes;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionFactory;
import com.gemstone.gemfire.cache.Scope;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventQueue;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.ResultSender;
import com.gemstone.gemfire.cache.query.SelectResults;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.gson.Gson;

import io.kryptoblocks.msa.common.cache.pdx.PdxObject;
import io.kryptoblocks.msa.common.cache.region.RegionMetaData;
import io.kryptoblocks.msa.common.cache.repository.CacheAsyncEventListener;
import io.kryptoblocks.msa.common.cache.repository.CacheAsyncEventQueueConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheRegionConfig;
import io.kryptoblocks.msa.common.cache.repository.CacheStorageConfig;

// TODO: Auto-generated Javadoc
/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Hash code.
 *
 * @return the int
 */
@EqualsAndHashCode(callSuper=false)
public class CacheFuction extends FunctionAdapter implements ApplicationContextAware {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CacheFuction.class);

	/** The registered name. */
	private static String REGISTERED_NAME = "CACHE_FUNCTION";

	 

	/** The partitioned region template. */
	@Autowired
	@Qualifier("ridMgmtBidPartitionedRegionTemplate")
	GemfireTemplate partitionedRegionTemplate;
	
	
	/** The replicated region template. */
	@Autowired
	@Qualifier("ridMgmtBidReplicatedRegionTemplate")
	GemfireTemplate replicatedRegionTemplate;
	
	 
	
	/** The cache. */
	Cache cache;
	
	 
	
	/** The context. */
	ApplicationContext context;

	/** The gson. */
	private Gson gson = new Gson();
	
	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@PostConstruct
	public void init() throws Exception {
		cache = CacheFactory.getAnyInstance();
		
		 
		 
	}

	/**
	 * Instantiates a new cache fuction.
	 */
	public CacheFuction() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return REGISTERED_NAME;
	}

	/**
	 * Execute.
	 *
	 * @param functionContext the function context
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(FunctionContext functionContext) {

		this.cache = CacheFactory.getAnyInstance();
		/*
		 * DistributedLockService dLS = DistributedLockService.create("distLockService",
		 * cache.getDistributedSystem()); exampleRegionLock =
		 * dLS.lock("exampleRegionLock", 30000, 60000); if (!exampleRegionLock) { } else
		 * { } if (exampleRegionLock) { dLS.unlock("exampleRegionLock"); }
		 */
		// CacheTransactionManager transactionMgr =
		// CacheFactory.getAnyInstance().getCacheTransactionManager();
		ResultSender<Object> resultSender = functionContext.getResultSender();
		try {
			// transactionMgr.begin();
			// locate the function input details
			Map<String, Object> input = (Map<String, Object>) functionContext.getArguments();
			String operationType = (String) input.get(OperationInputKey.OPERATION_TYPE);
			LOGGER.debug("Executig cache function execute method: input = {}, operatio type = {}", gson.toJson(input),
					operationType);
			if (operationType != null) {
				switch (RegionOperationType.valueOf(operationType.toUpperCase())) {
				case SINGLE_REGION_OPERATION:
					LOGGER.debug("Calling cache single region function");
					SingleRegionOperationInput singleRegionOperationInput = prepareSingleRegionInput(input);
					FunctionResult functionResult = doSingleRegionOperation(functionContext,
							singleRegionOperationInput);
					resultSender.sendResult(functionResult);
					break;
				case MULTI_REGION_OPERATION:
					List<SingleRegionOperationInput> multiRegioOperationInput = (List<SingleRegionOperationInput>) input
							.get(OperationInputKey.MULTI_REGION_OPERATION_INPUT);
					if ((multiRegioOperationInput != null) && (!multiRegioOperationInput.isEmpty())) {
						for (SingleRegionOperationInput curInput : multiRegioOperationInput) {
							FunctionResult curFunctionResult = doSingleRegionOperation(functionContext, curInput);
							resultSender.sendResult(curFunctionResult);
						}
					}
					break;
				default:
					break;
				}
			}
			// transactionMgr.commit();
			// resultSender.lastResult(FunctionLastResultType.COMMIT.getValue());
		} catch (Exception e) {
			// transactionMgr.rollback();
			// resultSender.lastResult(FunctionLastResultType.ROLLBACK.getValue());
			LOGGER.info("Exception in function execute method: message = {}", e.getMessage());
			e.printStackTrace();
		} finally {
			resultSender.lastResult(FunctionLastResultType.COMMIT.getValue());
		}
	}

	/**
	 * Execute contains key operation.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @return true, if successful
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private boolean executeContainsKeyOperation(String regionName, String key) throws CacheFuctionExcectionException {

		boolean returnValue = false;
		LOGGER.info("Executinng executeContainsKeyOperation with : region = {}, key = {}", regionName, key);

		try {
			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:
				returnValue = replicatedRegionTemplate.containsKey(key);
				break;
			case PARTITIONED_REGION:
				returnValue = partitionedRegionTemplate.containsKey(key);
				break;

			default:
				break;

			}
		} catch (Exception e) {
			LOGGER.info("Exception in executeContainsKeyOperation method: message = {}", e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}
	
	/**
	 * Execute contains value operation.
	 *
	 * @param regionName the region name
	 * @param value the value
	 * @return true, if successful
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	/*
	private Region<String, PdxObject> createRegion(String name, String storeName) throws Exception {
		 
		Region<String, PdxObject> region = cache.getRegion(name);
		
		if(region == null) {
			LOGGER.debug("create dynamic region name: {}", name);
		PartitionedRegionFactoryBean<String, PdxObject> regionFactory = new PartitionedRegionFactoryBean<String, PdxObject>();
		regionFactory.setCache(dataCache.getObject());
		regionFactory.setPersistent(new Boolean(cacheRegionConfig.getPersistence()));
		regionFactory.setName(name);
		
		DiskStore verificationRegionDiskStore = cache.findDiskStore(storeName);
		if (verificationRegionDiskStore == null) {			
			verificationRegionDiskStore = createDiskStore(storeName,  cacheStorageConfig);
		}
		
		regionFactory.setDiskStoreName(verificationRegionDiskStore.getName());
		List<AsyncEventQueue> aQueueList = new ArrayList<AsyncEventQueue>();
		aQueueList.add(cacheAsyncEventQueue.getObject());
		regionFactory.setAsyncEventQueues(aQueueList.toArray(new AsyncEventQueue[aQueueList.size()]));
		region = regionFactory.getObject();
		}else {
			LOGGER.debug("create dynamic region: name: {} already exist", name);
		}
		return region;
	}
	*/
	private boolean executeContainsValueOperation(String regionName, PdxObject value)
			throws CacheFuctionExcectionException {

		boolean returnValue = false;

		LOGGER.info("Executing executeContainsValueOperation with: region = {}, value = {} ", regionName,
				gson.toJson(value));

		try {

			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:

				returnValue = replicatedRegionTemplate.containsValue(value);
				break;
			case PARTITIONED_REGION:

				returnValue = partitionedRegionTemplate.containsValue(value);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			LOGGER.info("exception in executeContainsValueOperation method: message = {}", e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}

	/**
	 * Execute contains value for the key.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @return true, if successful
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private boolean executeContainsValueForTheKey(String regionName, String key) throws CacheFuctionExcectionException {

		boolean returnValue = false;

		LOGGER.info("Executing executeContainsValueForTheKey with: region =  {}, key = {}" + regionName, key);

		try {

			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:
				returnValue = replicatedRegionTemplate.containsValueForKey(key);
				break;
			case PARTITIONED_REGION:

				returnValue = partitionedRegionTemplate.containsValueForKey(key);
				break;

			default:
				break;

			}

		} catch (Exception e) {
			LOGGER.info("Exception in  executeContainsValueForTheKey method : message = {}" + e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}

	/**
	 * Execute create.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @param value the value
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private void executeCreate(String regionName, String key, PdxObject value) throws CacheFuctionExcectionException {

		LOGGER.info("Executing executeCreate with: region = {}, key = {}, value = {} " + regionName, key,
				gson.toJson(value));

		try {

			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:

				replicatedRegionTemplate.create(key, value);
				break;
			case PARTITIONED_REGION:

				partitionedRegionTemplate.create(key, value);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			LOGGER.info("Exception in executeCreate method : message = {}", e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

	}

	/**
	 * Execute find.
	 *
	 * @param regionName the region name
	 * @param query the query
	 * @param queryParam the query param
	 * @return the select results
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private SelectResults<Object> executeFind(String regionName, String query, Object[] queryParam)
			throws CacheFuctionExcectionException {

		SelectResults<Object> returnValue = null;

		LOGGER.info("Executing executeFind with: region = {}, query = {}, query param = {} ", regionName, query,
				gson.toJson(queryParam));

		try {
			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:
				returnValue = replicatedRegionTemplate.find(query, queryParam);
				break;
			case PARTITIONED_REGION:

				returnValue = partitionedRegionTemplate.find(query, queryParam);
				break;

			default:
				break;

			}

		} catch (Exception e) {
			LOGGER.info("exception details : " + "\n" + e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}

	/**
	 * Execute find unique.
	 *
	 * @param regionName the region name
	 * @param query the query
	 * @param queryParam the query param
	 * @return the object
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private Object executeFindUnique(String regionName, String query, Object[] queryParam)
			throws CacheFuctionExcectionException {

		Object returnValue = null;
		LOGGER.info("Executing executeFindUnique with: region =  {}, query = {}, query param = {}", regionName, query,
				gson.toJson(queryParam));
		try {

			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:
				returnValue = replicatedRegionTemplate.findUnique(query, queryParam);
				break;
			case PARTITIONED_REGION:

				returnValue = partitionedRegionTemplate.findUnique(query, queryParam);
				break;

			default:
				break;

			}

		} catch (Exception e) {
			LOGGER.info("exception details : " + "\n" + e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}

	/**
	 * Execute get.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @return the object
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private Object executeGet(String regionName, String key) throws CacheFuctionExcectionException {

		Object returnValue = null;

		LOGGER.info("Executing executeGet with : region = {}, key = {} ", regionName, key);

		try {

			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:
				returnValue = replicatedRegionTemplate.get(key);
				// securityMasterReplicatedRegionTemplate.getRegion().get(arg0)
				break;
			case PARTITIONED_REGION:

				returnValue = partitionedRegionTemplate.get(key);
				break;

			default:
				break;

			}

		} catch (Exception e) {
			LOGGER.info("exception details : " + "\n" + e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}

	/**
	 * Execute get all.
	 *
	 * @param regionName the region name
	 * @return the select results
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private SelectResults<Object> executeGetAll(String regionName) throws CacheFuctionExcectionException {

		SelectResults<Object> returnValue = null;
		LOGGER.info("executing executeGetAll with: " + "region = " + regionName);
		String selectAllQuery = "key !=null";
		returnValue = executeQuery(regionName, selectAllQuery);
		return returnValue;
	}

	/**
	 * Execute put.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @param value the value
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private void executePut(String regionName, String key, PdxObject value) throws CacheFuctionExcectionException {

		LOGGER.info("Executing executePut with: region = {}, key = {}, value = {} ", regionName, key,
				gson.toJson(value));

		try {
			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {

			case REPLICATED_REGION:

				replicatedRegionTemplate.put(key, value);
				break;
			case PARTITIONED_REGION:
				partitionedRegionTemplate.put(key, value);
				break;
				
			case REPLICATEDREGION:
				replicatedRegionTemplate.put(key, value);
				break;
			case PARTITIONEDREGION:
				partitionedRegionTemplate.put(key, value);
				break;
				
			case RIDMGMTBIDREPLICATEDREGION:
				replicatedRegionTemplate.put(key, value);
				break;
			case RIDEMGMTEXECUTIONPARTITIONEDREGION:
				partitionedRegionTemplate.put(key, value);
				break;
				
				 

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("exception executePut : message = {}", e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

	}

	/**
	 * Execute put all.
	 *
	 * @param regionName the region name
	 * @param values the values
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private void executePutAll(String regionName, Map<String, PdxObject> values) throws CacheFuctionExcectionException {

		// String[] keyArray = (String[]) values.keySet().toArray();

		LOGGER.info("executing executePutAll with: region =  {}, values = {}", regionName, gson.toJson(values));

		try {
			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {
			case REPLICATED_REGION:
				// int keySecurityReplicatedValueCount = 0;
				Map<String, PdxObject> updatedPartitionedInput = new HashMap<String, PdxObject>();

				for (Map.Entry<String, PdxObject> trnEntry : values.entrySet()) {
					PdxObject value = (PdxObject) trnEntry.getValue();
					String key = value.getKey();
					updatedPartitionedInput.put(key, value);
				}
				/*
				 * for (Object obj : values.entrySet()) { String key =
				 * keyArray[keySecurityReplicatedValueCount]; SecurityMaster value =
				 * (SecurityMaster) obj; updatedSecurityPartitionedInput.put(key, value);
				 * keySecurityReplicatedValueCount = keySecurityReplicatedValueCount + 1; }
				 */
				replicatedRegionTemplate.putAll(updatedPartitionedInput);
				break;
			case PARTITIONED_REGION:
				int keySecurityPartitionedValueCount = 0;
				Map<String, Object> updatedReplicatedInput = new HashMap<String, Object>();

				for (Map.Entry<String, PdxObject> trnEntry : values.entrySet()) {
					PdxObject value = (PdxObject) trnEntry.getValue();
					String key = value.getKey();
					updatedReplicatedInput.put(key, value);
					keySecurityPartitionedValueCount = keySecurityPartitionedValueCount + 1;
				}
				partitionedRegionTemplate.putAll(updatedReplicatedInput);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			LOGGER.info("Exception in executePutAll method : message = {} ", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}
	}

	/**
	 * Execute put if absent.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @param value the value
	 */
	private void executePutIfAbsent(String regionName, String key, PdxObject value) {
		LOGGER.info("Executing executePutIfAbsent with: region = {}, value = {} ", regionName, gson.toJson(value));
		try {
			// taskEventPartitionedRegionTemplate.putIfAbsent(arg0, arg1)

		} catch (Exception e) {
			LOGGER.info("Exception in executePutIfAbsent method: message = {}", e.getMessage());
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}
	}

	/**
	 * Execute query.
	 *
	 * @param regionName the region name
	 * @param query the query
	 * @return the select results
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private SelectResults<Object> executeQuery(String regionName, String query) throws CacheFuctionExcectionException {

		SelectResults<Object> returnValue = null;
		System.out.println("Executing executeQuery with: region = {}, query = {} " + regionName + "  " + query);

		try {
			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {
			case REPLICATED_REGION:
				returnValue = replicatedRegionTemplate.query(query);
				break;

			case PARTITIONED_REGION:
				returnValue = partitionedRegionTemplate.query(query);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			LOGGER.info("Exception in executeQuery method: message = {}", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}

	/**
	 * Execute remove.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private void executeRemove(String regionName, String key) throws CacheFuctionExcectionException {

		LOGGER.info("Executing executeRemove with: region = {}, key = {}", regionName, key);

		try {

			switch (CacheFunctionEnabledDataRegion.valueOf(regionName.toUpperCase())) {
			case REPLICATED_REGION:
				replicatedRegionTemplate.remove(key);
				break;

			case PARTITIONED_REGION:
				partitionedRegionTemplate.remove(key);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			LOGGER.info("Exception in executeRemove method: message = {}", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}
	}

	/**
	 * Execute replace.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @param values the values
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private void executeReplace(String regionName, String key, PdxObject values) throws CacheFuctionExcectionException {

		LOGGER.info("Executing executeReplace with: region =  {}, key = {}", regionName, key);

		try {
			// taskEventPartitionedRegionTemplate.replace(arg0, arg1)

		} catch (Exception e) {
			LOGGER.info("Exception in executeReplace method : message = {}", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

	}

	/**
	 * Execute replace with verification.
	 *
	 * @param regionName the region name
	 * @param key the key
	 * @param values the values
	 * @return the object
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	private Object executeReplaceWithVerification(String regionName, String key, PdxObject values)
			throws CacheFuctionExcectionException {

		Object returnValue = null;
		LOGGER.info("Executing executeReplaceWithVerification with: region = {}, key = {}, values = {} ", regionName,
				key, gson.toJson(values));

		try {

			// returnValue = taskEventPartitionedRegionTemplate.replace(key,
			// values);

		} catch (Exception e) {
			LOGGER.info("Exception in executeReplaceWithVerification method: message = {}", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

		return returnValue;

	}
	
	 
	/**
	 * Clear region.
	 *
	 * @param regionName the region name
	 * @throws CacheFuctionExcectionException the cache fuction excection exception
	 */
	/*
	public Region<String, PdxObject> createOrRetrieveRegion(Map<String, Object> regionValueMap)
			throws CacheFuctionExcectionException {

		LOGGER.info("Executing createOrRetrieveRegion with  regionValueMap = {}", gson.toJson(regionValueMap));

		Region<String, PdxObject> region = null;
		try {
			String regionName = (String) regionValueMap.get(RegionMetaData.FULLY_QUALIFIED_NAME);
			String storeName = (String)regionValueMap.get(RegionMetaData.FULLY_QUALIFIED_STORE_NAME);
			region = cache.getRegion(regionName.toUpperCase());
			if (region == null) {
				region = createRegion(regionName.toUpperCase(), storeName.toUpperCase());
			}
		} catch (Exception e) {
			LOGGER.info("Exception in createOrRetrieveRegion method: message = {}", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException((String) regionValueMap.get(RegionMetaData.FULLY_QUALIFIED_NAME),
					e.getMessage());
		}

		return region;
	}
*/
	private void clearRegion(String regionName) throws CacheFuctionExcectionException {

		LOGGER.info("Executing executeCreate with: region = {}", regionName);

		try {

			Region<String, Object> region = this.cache.getRegion(regionName);
			if (region != null) {
				region.clear();
			}

		} catch (Exception e) {
			LOGGER.info("Exception in clearRegion method: message = {} ", e.getMessage());
			e.printStackTrace();
			throw new CacheFuctionExcectionException(regionName, e.getMessage());
		}

	}
 
	/**
	 * Do single region operation.
	 *
	 * @param functionContext the function context
	 * @param input the input
	 * @return the function result
	 */
	/*
	 
	private DiskStore createDiskStore(String name,  CacheStorageConfig cacheStorageConfig) throws Exception {

		DiskStore returDiskStore = null;
		returDiskStore = dataCache.getObject().findDiskStore(name);
		if (returDiskStore == null) {
			DiskStoreFactory diskStoreFactory = dataCache.getObject().createDiskStoreFactory();
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
	*/
	private FunctionResult doSingleRegionOperation(FunctionContext functionContext, SingleRegionOperationInput input) {

		FunctionResult returnResult = new FunctionResult();

		String region = input.getRegion();
		String operation = input.getOperation();
		String key = input.getKey();
		PdxObject value = (PdxObject) input.getValue();
		String query = input.getQuery();
		Object[] queryParam = input.getQueryParam();
		Map<String, PdxObject> valueMap = input.getValueMap();
		Map<String, Object> regionAttributes = input.getRegionAttributes();
		LOGGER.info("Executing doSingleRegionOperation with : input = {}", gson.toJson(input));
		if ((region != null) && EnumUtils.isValidEnum(CacheFunctionEnabledDataRegion.class, region.toUpperCase())) {

			LOGGER.info("Executing doSingleRegionOperation with : input = {}", gson.toJson(input));

			switch (CacheSupportedFunctionOperation.valueOf(operation.toUpperCase())) {

			case CONTAINSKEY:
				if ((key != null) && (key instanceof String)) {
					try {
						boolean functionResult = executeContainsKeyOperation(region.toUpperCase(), key);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setBooleanResult(functionResult);
						returnResult.setResultType(FunctionResultType.BOOLEAN.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeContainsKeyOperation : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case CONTAINSVALUE:
				if (value != null) {

					try {

						boolean functionResult = executeContainsValueOperation(region.toUpperCase(), value);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setBooleanResult(functionResult);
						returnResult.setResultType(FunctionResultType.BOOLEAN.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeContainsValueOperation : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}

				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case CONTAINSVALUEFORKEY:
				if ((key != null) && (key instanceof String)) {
					try {
						boolean functionResult = executeContainsValueForTheKey(region.toUpperCase(), key);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setBooleanResult(functionResult);
						returnResult.setResultType(FunctionResultType.BOOLEAN.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeContainsValueOperation : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case CREATE:
				if ((key != null) && (key instanceof String) && (value != null)) {
					try {
						executeCreate(region.toUpperCase(), key, value);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.VOID.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeContainsValueOperation : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());

					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case FIND:
				if ((query != null) && (query instanceof String) && (queryParam != null)
						&& queryParam instanceof Object[]) {
					try {
						SelectResults<Object> resultAsObj = executeFind(region.toUpperCase(), query, queryParam);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setListResult(resultAsObj.asList());
						returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeFind : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}

				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case FINDUNIQUE:

				if ((query != null) && (query instanceof String) && (queryParam != null)
						&& queryParam instanceof Object[]) {
					try {
						Object resultObj = executeFindUnique(region.toUpperCase(), query, queryParam);
						if (resultObj != null) {
							ArrayList<Object> resultAsList = new ArrayList<Object>();
							resultAsList.add(resultObj);
							returnResult.setStatus(Status.SUCCESSFUL.getValue());
							returnResult.setListResult(resultAsList);
							returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
						}
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeFindUnique : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}

				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
					;
				}
				break;

			case GET:
				if ((key != null) && (key instanceof String)) {
					try {
						Object resultAsObject = executeGet(region.toUpperCase(), key);
						ArrayList<Object> resultAsList = new ArrayList<Object>();
						resultAsList.add(resultAsObject);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setListResult(resultAsList);
						returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.info("Exception in calling executeGet : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case GET_FOR_UPDATE:
				if ((key != null) && (key instanceof String)) {
					try {
						Object resultAsObject = executeGet(region.toUpperCase(), key);
						ArrayList<Object> resultAsList = new ArrayList<Object>();
						resultAsList.add(resultAsObject);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setListResult(resultAsList);
						returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
						// returnResult.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
						returnResult.setGetForUpdate(true);
					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.info("Exception in calling executeGet : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case UPDATE:
				break;

			case GETALL:
				try {
					LOGGER.info("Executing executeGetAll function method with query : {}", " <trace> " + query);
					SelectResults<Object> resultAsList = executeGetAll(region);
					returnResult.setStatus(Status.SUCCESSFUL.getValue());
					returnResult.setListResult(resultAsList.asList());
					returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
				} catch (Exception e) {
					// e.printStackTrace();
					LOGGER.info("Exception in Executing getall function method : message : {}", e.getMessage());
					returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
				}

				break;
			case PUT:
				if ((key != null) && (key instanceof String) && (value != null)) {
					try {
						executePut(region.toUpperCase(), key, value);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.VOID.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling execute put : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case PUTALL:
				if ((valueMap != null) && (valueMap instanceof Map)) {

					try {
						executePutAll(region.toUpperCase(), valueMap);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.VOID.getValue());

					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.debug("Exception in calling executePutAll : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				} else {
					returnResult.setStatus(Status.INVALID_INPUT.name());

				}
				break;

			case PUTIFABSENT:
				if ((key != null) && (key instanceof String) && (value != null))

					try {

						executePutIfAbsent(region.toUpperCase(), key, value);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.VOID.getValue());

					} catch (Exception e) {
						LOGGER.debug("Exception in calling executePutIfAbsent : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());

					}
				else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
					;
				}
				break;

			case QUERY:

				if ((query != null) && (query instanceof String))
					try {
						LOGGER.info("Executing query function method with query : {}", " <trace> " + query);
						SelectResults<Object> resultAsList = executeQuery(region.toUpperCase(), query);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setListResult(resultAsList.asList());
						returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
					} catch (Exception e) {
						// e.printStackTrace();
						LOGGER.info("Exception in Executing query function method : message : {}", e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());

					}
				else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
					;
				}
				break;

			case REMOVE:
				if ((key != null) && (key instanceof String))
					try {
						executeRemove(region.toUpperCase(), key);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.VOID.getValue());
					} catch (Exception e) {
						LOGGER.info("Exception in calling executeRemove : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
				}
				break;

			case REPLACE:

				if ((key != null) && (key instanceof String) && (value != null))
					try {
						executeReplace(region.toUpperCase(), key, value);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.VOID.getValue());
					} catch (Exception e) {
						LOGGER.debug("Exception in calling executeReplace : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
					;
				}
				break;

			case REPLACEWITHVERIFICATION:
				if ((key != null) && (key instanceof String) && (value != null))
					try {
						Object resultAsObj = executeReplaceWithVerification(region.toUpperCase(), key, value);
						List<Object> returnList = new ArrayList<Object>();
						if (resultAsObj != null) {
							returnList.add(resultAsObj);
						}
						returnResult.setListResult(returnList);
						returnResult.setStatus(Status.SUCCESSFUL.getValue());
						returnResult.setResultType(FunctionResultType.OBJECTS.getValue());
					} catch (Exception e) {

						LOGGER.debug("Exception in calling executeReplaceWithVerification : " + e.getMessage());
						returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
					}
				else {
					returnResult.setStatus(Status.INVALID_INPUT.name());
					;
				}
				break;

			 
			case CLEAR:
				try {
					clearRegion(region);
					returnResult.setStatus(Status.SUCCESSFUL.getValue());
					returnResult.setResultType(FunctionResultType.VOID.getValue());
				} catch (Exception e) {
					LOGGER.debug("Exception in calling clearRegion : " + e.getMessage());
					returnResult.setStatus(Status.UNSUCCESSFUL.getValue());
				}
				break;

			default:
				returnResult.setStatus(Status.UNKNOWN_OPERATION.getValue());
				break;
			}
		}

		// - need to add check returnResult.

		return returnResult;
	}

	/**
	 * Prepare single region input.
	 *
	 * @param input the input
	 * @return the single region operation input
	 */
	private SingleRegionOperationInput prepareSingleRegionInput(Map<String, Object> input) {

		SingleRegionOperationInput singleRegionOperationInput = new SingleRegionOperationInput();

		String region = (String) input.get(OperationInputKey.REGION_NAME);
		singleRegionOperationInput.setRegion(region);

		String operation = (String) input.get(OperationInputKey.OPERATION);
		singleRegionOperationInput.setOperation(operation);

		String key = (String) input.get(OperationInputKey.KEY);
		singleRegionOperationInput.setKey(key);

		PdxObject value = (PdxObject) input.get(OperationInputKey.VALUE);
		singleRegionOperationInput.setValue(value);

		String query = (String) input.get(OperationInputKey.QUERY);
		singleRegionOperationInput.setQuery(query);

		Object[] queryParam = (Object[]) input.get(OperationInputKey.QUERY_PARAM);
		singleRegionOperationInput.setQueryParam(queryParam);
		@SuppressWarnings("unchecked")
		Map<String, PdxObject> valueMap = (Map<String, PdxObject>) input.get(OperationInputKey.VALUE_MAP);
		singleRegionOperationInput.setValueMap(valueMap);

		@SuppressWarnings("unchecked")
		Map<String, Object> regionAttributes = (Map<String, Object>) input.get(OperationInputKey.REGION_ATTRIBUTES);
		singleRegionOperationInput.setRegionAttributes(regionAttributes);

		return singleRegionOperationInput;

	}

	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the new application context
	 * @throws BeansException the beans exception
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		setContext(applicationContext);		
	}

}
