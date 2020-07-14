package io.kryptoblocks.msa.data.nfr.infra.container.statistics.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.InfraContainerPerformanceStreamMessageSender;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraContainerClientService.
 */
public class InfraContainerClientService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InfraContainerClientService.class);
	
	/** The logged. */
	boolean logged = false;

	/** The cloud client host name. */
	@Value("${spring.cloud.client.hostname}")
	private String cloudClientHostName;

	/** The app name. */
	@Value("${spring.application.name}")
	private String appName;

	/** The app host port. */
	@Value("${server.port}")
	int appHostPort;
	
	/** The repo enabled. */
	@Value("${repository.enabled}")
	boolean repoEnabled;

	/** The infra container performance stream message sender. */
	@Autowired
	InfraContainerPerformanceStreamMessageSender infraContainerPerformanceStreamMessageSender;

	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService;

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
	 * Process infra perf activity event.
	 *
	 * @param infraContPerfEntity the infra cont perf entity
	 */
	public void processInfraPerfActivityEvent(InfraContPerfActivity infraContPerfEntity) {
		try {

			String streamString = infraContPerfEntity.getStreamType();
			StreamType streamType = StreamType.findByValue(streamString);
			String infraContPerfEntityAsJson = jsonUtil.getObjectAsJson(infraContPerfEntity);
			switch (streamType) {
			 
			case LOG:
				postToLogFile(infraContPerfEntityAsJson);
				break;
			case RABBITMQ:
				postToRabbitMQ(infraContPerfEntityAsJson);
				break;

			case KAFKA:
				postToKafka(infraContPerfEntityAsJson);
				break;
			
			default:
				if(!logged){
					postToLogFile(infraContPerfEntityAsJson);					
				}
				if(repoEnabled) {
					postTOCassandra(infraContPerfEntity);					
				}
			}			

		} catch (Exception e) {
			LOGGER.error("exception in infra container performance event processing: {}", ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to kafka.
	 *
	 * @param businessActivity the business activity
	 */
	private void postToKafka(String businessActivity) {
		// TODO
	}

	/**
	 * Post to rabbit MQ.
	 *
	 * @param infraContPerfEntityAsJson the infra cont perf entity as json
	 */
	private void postToRabbitMQ(String infraContPerfEntityAsJson) {
		try {
			LOGGER.debug("sending infra container perform activity message to the rabbit mq: {}", infraContPerfEntityAsJson);
			infraContainerPerformanceStreamMessageSender.processMessage(infraContPerfEntityAsJson);
		} catch (Exception e) {
			LOGGER.error("exception in infraa container perform activity post to the rabbit MQ method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to log file.
	 *
	 * @param infraContPerfEntityAsJson the infra cont perf entity as json
	 */
	private void postToLogFile(String infraContPerfEntityAsJson) {
		try {
			Logger infraContPerfEventLogger = LoggerFactory.getLogger("InfraContainerPerformanceLogger");
			infraContPerfEventLogger.info(infraContPerfEntityAsJson);
		} catch (Exception e) {
			LOGGER.error("exception in infra container performance activity post to log file method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}
	
	/**
	 * Post TO cassandra.
	 *
	 * @param infraContPerfEntity the infra cont perf entity
	 */
	//TODO
	private void postTOCassandra(InfraContPerfActivity infraContPerfEntity) {
		try {
			repositoryService.saveRepositoryObject(infraContPerfEntity);

		} catch (Exception e) {
			LOGGER.error("exception in post to cassandra methos for notification activity service: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

}