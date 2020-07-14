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
import io.kryptoblocks.msa.common.stream.sink.AppContainerPerformanceStreamSink;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
 

// TODO: Auto-generated Javadoc
/**
 * The Class AppContainerPerformanceStreamMessageReceiver.
 */
public class AppContainerPerformanceStreamMessageReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContainerPerformanceStreamMessageReceiver.class);
	
	/** The app container performance index builder. */
	@Autowired(required = false)	
	AppContainerPerformanceIndexBuilder appContainerPerformanceIndexBuilder;
	
	/** The db storage. */
	@Value("${service.app.container.performance.activity.db.storage}")
	boolean dbStorage;
	
	/** The elk storage. */
	@Value("${service.app.container.performance.activity.elk.storage}")
	boolean elkStorage;
	
	/** The elk enabled. */
	@Value("${elk.enabled}")
	boolean elkEnabled;
	
	
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
	@ServiceActivator(inputChannel = AppContainerPerformanceStreamSink.APP_CONTAINER_PERFORMANCE_INPUT)	
    public void processMessage(Message<?> message) {
		String payloadAsString = (String)message.getPayload();	
		
		LOGGER.debug("app container performance activity sink received message: {}", payloadAsString);		
		 
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
			AppContPerfActivity appContPerfEntity = objMapper.readValue(message, AppContPerfActivity.class);
			String indexType = appContPerfEntity.getCollectionType();			
			IndexRequestBuilder iBuilder = appContainerPerformanceIndexBuilder.getPerformanceMetricIndexBuilder(indexType);
			iBuilder.setSource(message);
			IndexResponse response = iBuilder.get();
			LOGGER.debug("elk storage activity reponse: {}", response);
		}catch(Exception e) {
			LOGGER.error("exception in container performance entity elk storage method: {}", ExceptionUtils.getFullStackTrace(e));			
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
			AppContPerfActivity appContPerfEntity = objMapper.readValue(message, AppContPerfActivity.class);
			//TODO
			repositoryService.saveRepositoryObject(appContPerfEntity);
			}catch(Exception e) {
				LOGGER.error("exception in container performance activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
			}		
	}
}
