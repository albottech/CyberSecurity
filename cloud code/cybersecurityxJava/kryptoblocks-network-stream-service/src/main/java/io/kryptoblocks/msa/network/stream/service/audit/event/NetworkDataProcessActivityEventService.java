package io.kryptoblocks.msa.network.stream.service.audit.event;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.names.NameCollection;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.common.util.LoggerUtil;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessServiceActivity;

import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
 
// TODO: Auto-generated Javadoc
/**
 * The Class NetworkDataProcessActivityEventService.
 */
public class NetworkDataProcessActivityEventService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataProcessActivityEventService.class);
	
	/** The Constant USER_EVENT_LOG_NAME. */
	private static final String USER_EVENT_LOG_NAME = "CUSTOMER_EVENT_LOG";
	
	/** The service name. */
	@Value("${spring.application.name}")
    private String serviceName;
	
	/** The host name. */
	@Value("${spring.cloud.client.hostname}")
	private String hostName;
	
	/** The port number. */
	@Value("${spring.cloud.client.hostname}")
	private String portNumber;
	
	/** The json util. */
	@Autowired
	JsonUtil jsonUtil;
		
	/** The network service repository. */
	@Autowired
	NetworkServiceRepository networkServiceRepository;
	
	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {

	}

	/**
	 * Process network process activity.
	 *
	 * @param networkProcessActivity the network process activity
	 */
	public void processNetworkProcessActivity(NetworkProcessServiceActivity networkProcessActivity) {
		 
		
		Object activity = null;
		try {
			String activityType = networkProcessActivity.getActivity();
			LOGGER.debug("current network process activity type: {}", activityType );
			NetworkProcessServiceActivity.Type networkProcessServiceActivityType = NetworkProcessServiceActivity.Type.findByValue(activityType);
			
			switch(networkProcessServiceActivityType) {
					case NETWORK_DATA_SOURCING:
						break;
					case NETWORK_DATA_ENRICHMENT:
						break;
					case NETWORK_DATA_SOR:
						break;
					case NETWORK_DATA_INDEX:
						break;
					case NETWORK_DATA_AI:
						break;
					 
					default:
						break;				
			}
			
		}catch (Exception e) {
			LOGGER.error("exception in network process activity event persistence log the activity to event log file : {}", ExceptionUtils.getFullStackTrace(e));
			getEventLogger().info(jsonUtil.getObjectAsJson(activity));			
		}
	}
	
	/**
	 * Gets the event logger.
	 *
	 * @return the event logger
	 */
	private Logger getEventLogger() {
		
		StringBuilder logFileBuilder = new StringBuilder();
		logFileBuilder.append(serviceName);
		logFileBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		logFileBuilder.append(hostName);
		logFileBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		logFileBuilder.append(portNumber);
		logFileBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		logFileBuilder.append(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(new Date()));
		return LoggerUtil.createLoggerFor(USER_EVENT_LOG_NAME, logFileBuilder.toString());
		
	}

}
