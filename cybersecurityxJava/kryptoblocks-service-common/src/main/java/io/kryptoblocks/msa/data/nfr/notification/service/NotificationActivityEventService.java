package io.kryptoblocks.msa.data.nfr.notification.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.NotificationActivityStreamMessageSender;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.notification.model.NotificationActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationActivityEventService.
 */
public class NotificationActivityEventService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationActivityEventService.class);

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

	/** The notification activity stream message sender. */
	@Autowired
	NotificationActivityStreamMessageSender notificationActivityStreamMessageSender;

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
	 * Process notification activity event.
	 *
	 * @param notificationActivity the notification activity
	 */
	public void processNotificationActivityEvent(NotificationActivity notificationActivity) {
		try {

			String streamString = notificationActivity.getStreamType();
			StreamType streamType = StreamType.findByValue(streamString);
			String notificationActivityAsJson = jsonUtil.getObjectAsJson(notificationActivity);
			switch (streamType) {

			case LOG:
				logged = true;
				postToLogFile(notificationActivityAsJson);
				break;
			case RABBITMQ:
				postToRabbitMQ(notificationActivityAsJson);
				break;

			case KAFKA:
				postToKafka(notificationActivityAsJson);
				break;

			default:
				if (!logged) {
					postToLogFile(notificationActivityAsJson);
				}
				if (repoEnabled) {
					postTOCassandra(notificationActivity);
				}
				break;
			}

		} catch (Exception e) {
			LOGGER.error("exception in notification event processing: {}", ExceptionUtils.getFullStackTrace(e));
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
	 * @param notificationActivity the notification activity
	 */
	private void postToRabbitMQ(String notificationActivity) {
		try {
			LOGGER.debug("sending notification activity message to the rabbit mq: {}", notificationActivity);
			notificationActivityStreamMessageSender.processMessage(notificationActivity);
		} catch (Exception e) {
			LOGGER.error("exception in business activity post to the rabbit MQ method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to log file.
	 *
	 * @param notificationString the notification string
	 */
	private void postToLogFile(String notificationString) {
		try {
			Logger notificationLogger = LoggerFactory.getLogger("TraceLogger");
			notificationLogger.info(notificationString);
		} catch (Exception e) {
			LOGGER.error("exception in notification activity post to log file method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Post TO cassandra.
	 *
	 * @param notificationActivity the notification activity
	 */
	private void postTOCassandra(NotificationActivity notificationActivity) {
		try {

			repositoryService.saveRepositoryObject(notificationActivity);

		} catch (Exception e) {
			LOGGER.error("exception in post to cassandra methos for notification activity service: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

}
