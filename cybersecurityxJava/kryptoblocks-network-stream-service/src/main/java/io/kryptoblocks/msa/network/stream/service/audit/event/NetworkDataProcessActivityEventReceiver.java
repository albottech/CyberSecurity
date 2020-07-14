package io.kryptoblocks.msa.network.stream.service.audit.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessServiceActivity;
 
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkDataProcessActivityEventReceiver.
 */
public class NetworkDataProcessActivityEventReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataProcessActivityEventReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The network process activity event service. */
	@Autowired
	NetworkDataProcessActivityEventService networkProcessActivityEventService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(NetworkProcessServiceActivity.class), receivenetworkProcessActivityEvent());
	}

	/**
	 * Receivenetwork process activity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receivenetworkProcessActivityEvent() {
		
		Consumer<Event<?>> returnValue = null;
		
		try {
			returnValue =  networkProcessActivityEvent -> networkProcessActivityEventService.processNetworkProcessActivity((NetworkProcessServiceActivity) networkProcessActivityEvent.getData());
		} catch (Exception e) {			
			LOGGER.error("exception in receive and dispatch network process activity activityEvent method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}
}
