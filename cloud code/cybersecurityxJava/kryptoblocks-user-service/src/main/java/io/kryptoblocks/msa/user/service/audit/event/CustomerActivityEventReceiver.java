package io.kryptoblocks.msa.user.service.audit.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.UserActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

public class CustomerActivityEventReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerActivityEventReceiver.class);

	@Autowired
	private EventBus eventBus;

	@Autowired
	CustomerServiceActivityEventService customerActivityEventService;

	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(CustomerActivity.class), receiveCustomerActivityyEvent());
	}

	private Consumer<Event<?>> receiveCustomerActivityyEvent() {
		
		Consumer<Event<?>> returnValue = null;
		
		try {
			returnValue =  customerActivityEvent -> customerActivityEventService.processCustomerActivity((CustomerActivity) customerActivityEvent.getData());
		} catch (Exception e) {			
			LOGGER.error("exception in receive customer activityEvent method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}
}
