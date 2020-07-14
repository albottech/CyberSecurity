package io.kryptoblocks.msa.user.service.audit.event;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.user.service.model.UserActivity;
import reactor.bus.Event;
import reactor.bus.EventBus;

public class UserActivityEventSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserActivityEventSender.class);
	 	
	@Value("${spring.application.name}")
	private String serviceName;
	
	@Autowired
	private EventBus eventBus;

	public void sendUserActivity(UserActivity userActivity) {

		try {			 
			eventBus.send(userActivity, Event.wrap(userActivity, userActivity));
		} catch (Exception e) {
			LOGGER.error("Exception in user activity sender : {}", ExceptionUtils.getStackTrace(e));
		}
	}
}
