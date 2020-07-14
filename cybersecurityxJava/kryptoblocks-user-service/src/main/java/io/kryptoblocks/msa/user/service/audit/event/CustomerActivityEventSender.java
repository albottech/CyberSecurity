package io.kryptoblocks.msa.user.service.audit.event;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.UserActivity;
import reactor.bus.Event;
import reactor.bus.EventBus;

public class CustomerActivityEventSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerActivityEventSender.class);
	 	
	@Value("${spring.application.name}")
	private String serviceName;
	
	@Autowired
	private EventBus eventBus;

	public void sendCustomerActivity(CustomerActivity customerActivity) {

		try {			 
			eventBus.send(customerActivity, Event.wrap(customerActivity, customerActivity));
		} catch (Exception e) {
			LOGGER.error("Exception in customer  activity sender : {}", ExceptionUtils.getStackTrace(e));
		}
	}
}
