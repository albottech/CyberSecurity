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

import io.kryptoblocks.msa.common.elastic.ExceptionActivityIndexBuilder;
import io.kryptoblocks.msa.common.stream.sink.ExceptionActivityStreamSink;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.exception.model.ExceptionActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionStreamMessageReceiver.
 */
public class ExceptionStreamMessageReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionStreamMessageReceiver.class);

	/** The exception activity index builder. */
	@Autowired(required = false)
	ExceptionActivityIndexBuilder exceptionActivityIndexBuilder;

	/** The db storage. */
	@Value("${service.exception.activity.db.storage}")
	boolean dbStorage;

	/** The elk storage. */
	@Value("${service.exception.activity.elk.storage}")
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
	@ServiceActivator(inputChannel = ExceptionActivityStreamSink.EXCEPTION_ACTIVITY_INPUT)
	public void processMessage(Message<?> message) {
		String payloadAsString = (String) message.getPayload();
		LOGGER.debug("exception sink message receiver msg: {}", payloadAsString);
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
			ExceptionActivity exceptonActivity = objMapper.readValue(message, ExceptionActivity.class);
			String indexType = exceptonActivity.getCollectionType();			
			IndexRequestBuilder iBuilder = exceptionActivityIndexBuilder.getExceptionIndexBuilder(indexType);
			iBuilder.setSource(message);
			IndexResponse response = iBuilder.get();
			LOGGER.debug("elk storage activity reponse for exception activity: {}", response);
		}catch(Exception e) {
			LOGGER.error("exception in elk storage activity for exception activity: {}", ExceptionUtils.getFullStackTrace(e));			
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
				LOGGER.error("exception in exception activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
			}		
	}
}
