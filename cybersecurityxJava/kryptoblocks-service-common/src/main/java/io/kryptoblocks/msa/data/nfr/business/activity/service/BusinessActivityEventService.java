package io.kryptoblocks.msa.data.nfr.business.activity.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.BusinessActivityStreamMessageSender;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import io.kryptoblocks.msa.data.nfr.trace.model.Trace;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceSpan;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityEventService.
 */
public class BusinessActivityEventService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivityEventService.class);
	
	/** The logged. */
	boolean logged = false;
	
	/** The repo enabled. */
	@Value("${repository.enabled}")
	boolean repoEnabled;
	
	/** The cloud client host name. */
	@Value("${spring.cloud.client.hostname}")
	private String cloudClientHostName;

	/** The app host port. */
	@Value("${server.port}")
	int appHostPort;
	
	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService; 	
	
	/** The business activity stream message sender. */
	@Autowired
	BusinessActivityStreamMessageSender businessActivityStreamMessageSender;
	
	/** The json util. */
	@Autowired
	JsonUtil jsonUtil;
	
	/**
	 * On start up.
	 */
	@PostConstruct
    public void onStartUp() { 
		 	
	}

	/**
	 * Process business activity.
	 *
	 * @param businessActivity the business activity
	 */
	public void processBusinessActivity(BusinessActivity businessActivity) {
		businessActivity.setProssedTimeTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		businessActivity.setInstance(cloudClientHostName + "|"+ appHostPort);
		StreamType streamType = StreamType.findByValue(businessActivity.getStreamType());
		LOGGER.debug("business activity event service : stream type : {}",businessActivity.getStreamType());
		String businessActivityAsJson = jsonUtil.getObjectAsJson(businessActivity);
		switch (streamType) {
		case LOG:			 
			postToLogFile(businessActivityAsJson, businessActivity);
			logged = true;			
			break;
		case RABBITMQ:
			postToRabbitMQ(businessActivityAsJson);
			break;
		case KAFKA:
			postToKafka(businessActivityAsJson);
			break;
		default:
			if(!logged) {
				postToLogFile(businessActivityAsJson, businessActivity);
			}
			if(repoEnabled) {
				postToCassandraRepository(businessActivity);
			}
			break;
		}
	}

	/**
	 * Post to rabbit MQ.
	 *
	 * @param businessActivity the business activity
	 */
	private void postToRabbitMQ(String businessActivity) {
		try {		
		LOGGER.debug("sending business activity message to the rabbit mq: {}", businessActivity );
		businessActivityStreamMessageSender.processMessage(businessActivity);
		}catch(Exception e) {
			LOGGER.error("exception in business activity post to the rabbit MQ method: {}", ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to kafka.
	 *
	 * @param businessActivity the business activity
	 */
	private void postToKafka(String businessActivity) {
		//TODO		 
	}

	 

	/**
	 * Post to log file.
	 *
	 * @param businessActivityJson the business activity json
	 * @param businessActivity the business activity
	 */
	private void postToLogFile(String businessActivityJson, BusinessActivity businessActivity) {
		try {
			Logger businessMetricLogger = LoggerFactory.getLogger("BusinessMetricLogger");
			businessMetricLogger.info(businessActivityJson);
			if (repoEnabled) {
				postTOCassandra(businessActivity);
			}
		}catch(Exception e) {
			LOGGER.error("exception in business activity post to log file method: {}", ExceptionUtils.getFullStackTrace(e));			
		}		
	}
	
	/**
	 * Post to cassandra repository.
	 *
	 * @param businessActivity the business activity
	 */
	private void postToCassandraRepository(BusinessActivity businessActivity) {	
		try {		
		repositoryService.saveRepositoryObject(businessActivity);
		}catch(Exception e) {
			LOGGER.error("exception in business activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * Post TO cassandra.
	 *
	 * @param businessActivity the business activity
	 */
	private void postTOCassandra(BusinessActivity businessActivity) {
		try {
			repositoryService.saveRepositoryObject(businessActivity);			 
		} catch (Exception e) {
			LOGGER.error("exception in post to cassandra methos for business activity service: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

}
