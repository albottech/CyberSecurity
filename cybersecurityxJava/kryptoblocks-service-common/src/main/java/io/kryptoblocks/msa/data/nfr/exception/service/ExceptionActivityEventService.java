package io.kryptoblocks.msa.data.nfr.exception.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.ExceptionActivityStreamMessageSender;
import io.kryptoblocks.msa.data.nfr.exception.model.ExceptionActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivityEventService.
 */
public class ExceptionActivityEventService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionActivityEventService.class);
	
	/** The logged. */
	boolean logged = false;
	
	/** The repo enabled. */
	@Value("${repository.enabled}")
	boolean repoEnabled;
	
	/** The exception stream message sender. */
	@Autowired
	ExceptionActivityStreamMessageSender exceptionStreamMessageSender;

	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {

	}

	/**
	 * Process service exception.
	 *
	 * @param exceptionActivity the exception activity
	 */
	public void processServiceException(ExceptionActivity exceptionActivity) {

		StreamType streamType = StreamType.findByValue(exceptionActivity.getStreamType());
		LOGGER.debug("exception activity event service : stream type : {}", exceptionActivity.getStreamType());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String exceptionAsJson = null;
		try {
			exceptionAsJson = objectMapper.writeValueAsString(exceptionActivity);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
		switch (streamType) {
		case LOG:
			postToLogFile(exceptionAsJson);
			logged = true;	
			break;
		case RABBITMQ:
			postToRabbitMQ(exceptionAsJson);
			break;
		case KAFKA:
			postToKafka(exceptionAsJson);
			break;

		default:
			if(!logged) {
				postToLogFile(exceptionAsJson);
			}
			if(repoEnabled) {
				postToCassandraRepository(exceptionActivity);				
			}
			break;
		}
	}

	/**
	 * Post to rabbit MQ.
	 *
	 * @param serviceExceptionAsString the service exception as string
	 */
	private void postToRabbitMQ(String serviceExceptionAsString) {
		try {
			LOGGER.debug("sending exception message to rabbit mq: {}", serviceExceptionAsString);
			exceptionStreamMessageSender.processMessage(serviceExceptionAsString);
		} catch (Exception e) {
			LOGGER.error("exception in post to rabbit mq method: for exception activity: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Post to kafka.
	 *
	 * @param serviceExceptionAsString the service exception as string
	 */
	private void postToKafka(String serviceExceptionAsString) {
		try {
			LOGGER.debug("sending exception message to kafka:  {}", serviceExceptionAsString);
			// TODO
		} catch (Exception e) {
			LOGGER.error("exception in post to kafkamethod: for exception activity: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Post to log file.
	 *
	 * @param serviceExceptionAsString the service exception as string
	 */
	private void postToLogFile(String serviceExceptionAsString) {
		try {
			Logger exceptionMetricLogger = LoggerFactory.getLogger("ExceptionMetricLogger");
			exceptionMetricLogger.info(serviceExceptionAsString);
		} catch (Exception e) {
			LOGGER.error("exception in post to log method: for exception activity: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Post to cassandra repository.
	 *
	 * @param exceptionActivity the exception activity
	 */
	private void postToCassandraRepository(ExceptionActivity exceptionActivity) {
		try {
			repositoryService.saveRepositoryObject(exceptionActivity);
		} catch (Exception e) {
			LOGGER.error("exception in post to repository method: for exception activity: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

}
