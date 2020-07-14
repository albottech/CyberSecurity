package io.kryptoblocks.msa.data.nfr.security.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.SecurityActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.TraceActivityStreamMessageSender;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import io.kryptoblocks.msa.data.nfr.security.model.SecurityActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityActivityEventService.
 */
public class SecurityActivityEventService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityActivityEventService.class);

	/** The cloud client host name. */
	@Value("${spring.cloud.client.hostname}")
	private String cloudClientHostName;

	/** The app name. */
	@Value("${spring.application.name}")
	private String appName;

	/** The app host port. */
	@Value("${server.port}")
	int appHostPort;
	
	/** The logged. */
	boolean logged = false;

	/** The repo enabled. */
	@Value("${repository.enabled}")
	boolean repoEnabled;

	/** The security activity stream message sender. */
	@Autowired
	SecurityActivityStreamMessageSender securityActivityStreamMessageSender;

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
	 * Process security event.
	 *
	 * @param securityActivity the security activity
	 */
	public void processSecurityEvent(SecurityActivity securityActivity) {
		try {

			String streamString = securityActivity.getStreamType();
			StreamType streamType = StreamType.findByValue(streamString);
			String securityActivityAsJson = jsonUtil.getObjectAsJson(securityActivity);
			switch (streamType) {
			
			case LOG:
				postToLogFile(securityActivityAsJson);
				logged = true;
				break;
			case RABBITMQ:
				postToRabbitMQ(securityActivityAsJson);
				break;

			case KAFKA:
				postToKafka(securityActivityAsJson);
				break;
				
			default:
				if(!logged) {
					postToLogFile(securityActivityAsJson);
				}
				if(repoEnabled) {
					postTOCassandra(securityActivity);
				}
				break;
			}
			
		} catch (Exception e) {
			LOGGER.error("exception in security event processing: {}", ExceptionUtils.getFullStackTrace(e));
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
	 * @param traceActivity the trace activity
	 */
	private void postToRabbitMQ(String traceActivity) {
		try {
			LOGGER.debug("sending security activity message to the rabbit mq: {}", traceActivity);
			securityActivityStreamMessageSender.processMessage(traceActivity);
		} catch (Exception e) {
			LOGGER.error("exception in security activity post to the rabbit MQ method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to log file.
	 *
	 * @param traceString the trace string
	 */
	private void postToLogFile(String traceString) {
		try {
			Logger traceLogger = LoggerFactory.getLogger("TraceLogger");
			traceLogger.info(traceString);
		} catch (Exception e) {
			LOGGER.error("exception in security activity post to log file method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Post TO cassandra.
	 *
	 * @param securityActivity the security activity
	 */
	private void postTOCassandra(SecurityActivity securityActivity) {
		try {

			repositoryService.saveRepositoryObject(securityActivity);

		} catch (Exception e) {
			LOGGER.error("exception in post to cassandra methos for security activity service: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

}
