package io.kryptoblocks.msa.data.nfr.app.statistics.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.AutoConfigurationReportEndpoint;
import org.springframework.boot.actuate.endpoint.BeansEndpoint;
import org.springframework.boot.actuate.endpoint.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.actuate.endpoint.DumpEndpoint;
import org.springframework.boot.actuate.endpoint.EnvironmentEndpoint;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.boot.actuate.endpoint.LoggersEndpoint;
import org.springframework.boot.actuate.endpoint.ShutdownEndpoint;
import org.springframework.cloud.context.restart.RestartEndpoint;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.AppContainerPerformanceStreamMessageSender;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class AppContainerClientService.
 */
public class AppContainerClientService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContainerClientService.class);
			
	/** The logged. */
	boolean logged = false;
	
	/** The repo enabled. */
	@Value("${repository.enabled}")
	boolean repoEnabled;
	
	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService; 	
	
	
	/** The environment endpoint. */
	@Autowired
	EnvironmentEndpoint environmentEndpoint;
	
	/** The health endpoint. */
	@Autowired
	HealthEndpoint healthEndpoint;
	
	/** The info end point. */
	@Autowired
	InfoEndpoint infoEndPoint;
	
	/** The component endpoint. */
	@Autowired
	BeansEndpoint componentEndpoint;
	
	/** The auto configuration report endpoint. */
	@Autowired
	AutoConfigurationReportEndpoint autoConfigurationReportEndpoint;
	
	/** The configuration properties report endpoint. */
	@Autowired
	ConfigurationPropertiesReportEndpoint configurationPropertiesReportEndpoint;
	
	/** The dump endpoint. */
	@Autowired
	DumpEndpoint dumpEndpoint;
	
	/*@Autowired
	DataSourcePublicMetrics dataSourcePublicMetrics;*/
	
	/** The loggers endpoint. */
	@Autowired
	LoggersEndpoint loggersEndpoint;
	
	/** The restart endpoint. */
	@Autowired
	private RestartEndpoint restartEndpoint;
	
	/** The shutdown endpoint. */
	@Autowired
	private ShutdownEndpoint shutdownEndpoint;
	
	/** The app container performance stream message sender. */
	@Autowired
	AppContainerPerformanceStreamMessageSender appContainerPerformanceStreamMessageSender;
	
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
	 * Process cont perf activity.
	 *
	 * @param appContPerfEntity the app cont perf entity
	 */
	public void processContPerfActivity(AppContPerfActivity appContPerfEntity) {
		appContPerfEntity.setProcessedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		StreamType streamType = StreamType.findByValue(appContPerfEntity.getStreamType());
		LOGGER.debug("container perfomance activity event : stream type : {}",streamType.getValue());
		String appContPerfEntityAsJson = jsonUtil.getObjectAsJson(appContPerfEntity);
		switch (streamType) {
		case LOG:			 
			postToLogFile(appContPerfEntityAsJson);
			logged = true;			
			break;
		case RABBITMQ:
			postToRabbitMQ(appContPerfEntityAsJson);
			break;
		case KAFKA:
			postToKafka(appContPerfEntityAsJson);
			break;
		default:
			if(!logged) {
				postToLogFile(appContPerfEntityAsJson);
			}
			if(repoEnabled) {
				postToCassandraRepository(appContPerfEntity);
			}
			break;
		}
	}
		
		/**
		 * Post to rabbit MQ.
		 *
		 * @param message the message
		 */
		private void postToRabbitMQ(String message) {
			try {		
			LOGGER.debug("sending app container performance activity message to the rabbit mq: {}", message );
			appContainerPerformanceStreamMessageSender.processMessage(message);
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
		 * @param message the message
		 */
		private void postToLogFile(String message) {
			try {
				Logger businessMetricLogger = LoggerFactory.getLogger("BusinessMetricLogger");
				businessMetricLogger.info(message);
			}catch(Exception e) {
				LOGGER.error("exception in container performance activity post to log file method: {}", ExceptionUtils.getFullStackTrace(e));			
			}		
		}
		
		/**
		 * Post to cassandra repository.
		 *
		 * @param appContPerfEntity the app cont perf entity
		 */
		private void postToCassandraRepository(AppContPerfActivity appContPerfEntity) {	
			try {
			//TODO	
			repositoryService.saveRepositoryObject(appContPerfEntity);
			}catch(Exception e) {
				LOGGER.error("exception in container performance activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
			}
		}
}
