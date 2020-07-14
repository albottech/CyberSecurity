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

import io.kryptoblocks.msa.common.elastic.InfraContainerPerformanceIndexBuilder;
import io.kryptoblocks.msa.common.stream.sink.InfraContainerPerformanceStreamSink;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraContainerPerformanceStreamMessageReceiver.
 */
public class InfraContainerPerformanceStreamMessageReceiver {

/** The Constant LOGGER. */
private static final Logger LOGGER = LoggerFactory.getLogger(InfraContainerPerformanceStreamMessageReceiver.class);
	
	/** The infra container performance index builder. */
	@Autowired(required = false)
	InfraContainerPerformanceIndexBuilder infraContainerPerformanceIndexBuilder;
	
		
	
	/** The db storage. */
	@Value("${service.infra.container.performance.activity.db.storage}")
	boolean dbStorage;
	
	/** The elk enabled. */
	@Value("${elk.enabled}")
	boolean elkEnabled;
	
	/** The elk storage. */
	@Value("${service.infra.container.performance.activity.elk.storage}")
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
	@ServiceActivator(inputChannel = InfraContainerPerformanceStreamSink.INFRA_CONTAINER_PERFORMANCE_INPUT)	
    public void processMessage(Message<?> message) {
		String payloadAsString = (String)message.getPayload();	
		LOGGER.debug("infra container performance activity sink message receiver msg: {}", payloadAsString);
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
			IndexRequestBuilder iBuilder = infraContainerPerformanceIndexBuilder.getInfraContainerPerformanceIndexBuilder(indexType);
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
