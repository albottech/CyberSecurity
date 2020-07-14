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

import io.kryptoblocks.msa.common.elastic.SecurityActivityIndexBuilder;
import io.kryptoblocks.msa.common.stream.sink.SecurityActivityStreamSink;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import io.kryptoblocks.msa.data.nfr.security.model.SecurityActivity;;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityStreamMessageReceiver.
 */
public class SecurityStreamMessageReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityStreamMessageReceiver.class);

	/** The security activity index builder. */
	@Autowired(required = false)
	SecurityActivityIndexBuilder securityActivityIndexBuilder;

	
	/** The db storage. */
	@Value("${service.security.activity.db.storage}")
	boolean dbStorage;
	
	/** The elk enabled. */
	@Value("${elk.enabled}")
	boolean elkEnabled;

	/** The elk storage. */
	@Value("${service.security.activity.elk.storage}")
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
	@ServiceActivator(inputChannel = SecurityActivityStreamSink.SECURITY_ACTIVITY_INPUT)
	public void processMessage(Message<?> message) {
		String payloadAsString = (String) message.getPayload();
		LOGGER.debug("security sink message receiver msg: {}", payloadAsString);
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
			SecurityActivity securityActivity = objMapper.readValue(message, SecurityActivity.class);
			String indexType = securityActivity.getCollectionType();			
			IndexRequestBuilder iBuilder = securityActivityIndexBuilder.getSecurityActivityIndexBuilder(indexType);
			iBuilder.setSource(message);
			IndexResponse response = iBuilder.get();
			LOGGER.debug("elk storage activity reponse for security activity: {}", response);
		}catch(Exception e) {
			LOGGER.error("exception in elk storage activity for security activity: {}", ExceptionUtils.getFullStackTrace(e));			
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
				LOGGER.error("exception in security activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
			}		
	}
}
