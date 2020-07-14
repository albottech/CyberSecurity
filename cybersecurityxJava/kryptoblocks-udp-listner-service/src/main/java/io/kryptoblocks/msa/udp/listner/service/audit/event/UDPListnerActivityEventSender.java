package io.kryptoblocks.msa.udp.listner.service.audit.event;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerServiceActivity;
import reactor.bus.Event;
import reactor.bus.EventBus;

public class UDPListnerActivityEventSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPListnerActivityEventSender.class);
	 	
	@Value("${spring.application.name}")
	private String serviceName;
	
	@Autowired
	private EventBus eventBus;

	public void sendNetworkProcessActivity(UDPListnerServiceActivity networkProcessActivity) {

		try {			 
			eventBus.send(networkProcessActivity, Event.wrap(networkProcessActivity, networkProcessActivity));
		} catch (Exception e) {
			LOGGER.error("Exception in network process  activity sender : {}", ExceptionUtils.getStackTrace(e));
		}
	}
}
