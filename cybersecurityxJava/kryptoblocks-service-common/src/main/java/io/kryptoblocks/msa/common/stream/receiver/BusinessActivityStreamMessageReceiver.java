package io.kryptoblocks.msa.common.stream.receiver;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.kryptoblocks.msa.common.elastic.AppContainerPerformanceIndexBuilder;
import io.kryptoblocks.msa.common.elastic.BusinessActivityIndexBuilder;
import io.kryptoblocks.msa.common.stream.sink.BusinessActivitityStreamSink;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
 


// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityStreamMessageReceiver.
 */
public class BusinessActivityStreamMessageReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivityStreamMessageReceiver.class);
	
	
	 
	
	/** The business activity index builder. */
	@Autowired(required = false)
	BusinessActivityIndexBuilder businessActivityIndexBuilder;
		 
	
	/** The db storage. */
	@Value("${service.business.activity.db.storage}")
	boolean dbStorage;
	
	/** The elk enabled. */
	@Value("${elk.enabled}")
	boolean elkEnabled;
	
	/** The elk storage. */
	@Value("${service.business.activity.elk.storage}")
	boolean elkStorage;
	
	/** The json util. */
	@Autowired
	JsonUtil jsonUtil;
	
	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService; 	
	
	
	
	
	/**
	 * Process message.
	 *
	 * @param message the message
	 */
	@ServiceActivator(inputChannel = BusinessActivitityStreamSink.BUSINESS_ACTIVITY_INPUT)	
    public void processMessage(Message<?> message) {
		String payloadAsString = (String)message.getPayload();	
		LOGGER.debug("business activity sink message receiver msg: {}", payloadAsString);
		if(dbStorage) {
			dbStorage(payloadAsString);
		}
		if(elkEnabled && elkStorage) {
			elkStorage(payloadAsString);
		}
    }
	
	/**
	 * Elk storage.
	 *
	 * @param message the message
	 */
	private void elkStorage(String message) {
		try {
			ObjectMapper objMapper = jsonUtil.getObjectMapper();
			BusinessActivity businessActivity = objMapper.readValue(message, BusinessActivity.class);
			String indexType = businessActivity.getCollectionType();			
			IndexRequestBuilder iBuilder = businessActivityIndexBuilder.getPerformanceMetricIndexBuilder(indexType);
			iBuilder.setSource(message);
			IndexResponse response = iBuilder.get();
			LOGGER.debug("elk storage activity reponse for business activity: {}", response);
		}catch(Exception e) {
			LOGGER.error("exception in business activity elk storage method: {}", ExceptionUtils.getFullStackTrace(e));			
		}
	}
	
	/**
	 * Db storage.
	 *
	 * @param message the message
	 */
	private void dbStorage(String message) {
		try {
			//TODO	
			ObjectMapper objMapper = jsonUtil.getObjectMapper();
			BusinessActivity businessActivity = objMapper.readValue(message, BusinessActivity.class);
			//TODO
			repositoryService.saveRepositoryObject(businessActivity);
			}catch(Exception e) {
				LOGGER.error("exception in business activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
			}		
	}

}
