package io.kryptoblocks.msa.user.service.audit.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.user.service.model.UserActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

public class UserActivityEventReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserActivityEventReceiver.class);

	@Autowired
	private EventBus eventBus;

	@Autowired
	UserServiceActivityEventService UserActivityEventService;

	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(UserActivity.class), receivePaymentActivityEvent());
	}

	private Consumer<Event<?>> receivePaymentActivityEvent() {
		
		Consumer<Event<?>> returnValue = null;
		
		try {
			returnValue =  paymentEvent -> UserActivityEventService.processPaymentActivity((UserActivity) paymentEvent.getData());
		} catch (Exception e) {			
			LOGGER.error("exception in receive payment activityEvent method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}
}
