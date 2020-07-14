package io.kryptoblocks.msa.network.stream.service.audit.event;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessServiceActivity;
 
import reactor.bus.Event;
import reactor.bus.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkDataProcessActivityEventSender.
 */
public class NetworkDataProcessActivityEventSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataProcessActivityEventSender.class);
	 	
	/** The service name. */
	@Value("${spring.application.name}")
	private String serviceName;
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/**
	 * Send network process activity.
	 *
	 * @param networkProcessActivity the network process activity
	 */
	public void sendNetworkProcessActivity(NetworkProcessServiceActivity networkProcessActivity) {

		try {			 
			eventBus.send(networkProcessActivity, Event.wrap(networkProcessActivity, networkProcessActivity));
		} catch (Exception e) {
			LOGGER.error("Exception in network process  activity sender : {}", ExceptionUtils.getStackTrace(e));
		}
	}
}
