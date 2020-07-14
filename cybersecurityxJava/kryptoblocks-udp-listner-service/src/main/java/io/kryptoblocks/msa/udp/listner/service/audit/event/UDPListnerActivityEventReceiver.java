package io.kryptoblocks.msa.udp.listner.service.audit.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerServiceActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

public class UDPListnerActivityEventReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPListnerActivityEventReceiver.class);

	@Autowired
	private EventBus eventBus;

	@Autowired
	UDPListnerActivityEventService networkProcessActivityEventService;

	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(UDPListnerServiceActivity.class), receivenetworkProcessActivityEvent());
	}

	private Consumer<Event<?>> receivenetworkProcessActivityEvent() {
		
		Consumer<Event<?>> returnValue = null;
		
		try {
			returnValue =  networkProcessActivityEvent -> networkProcessActivityEventService.processNetworkProcessActivity((UDPListnerServiceActivity) networkProcessActivityEvent.getData());
		} catch (Exception e) {			
			LOGGER.error("exception in receive and dispatch network process activity activityEvent method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}
}
