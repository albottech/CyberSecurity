package io.kryptoblocks.msa.user.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.user.service.audit.event.CustomerActivityEventReceiver;
import io.kryptoblocks.msa.user.service.audit.event.CustomerActivityEventSender;
import io.kryptoblocks.msa.user.service.audit.event.CustomerServiceActivityEventService;
import io.kryptoblocks.msa.user.service.audit.event.UserActivityEventReceiver;
import io.kryptoblocks.msa.user.service.audit.event.UserActivityEventSender;
import io.kryptoblocks.msa.user.service.audit.event.UserServiceActivityEventService;

@Configuration
public class EventConfig {
     
    @Bean
    CustomerActivityEventReceiver ccustomerActivityEventReceiver() {
		return new CustomerActivityEventReceiver();		
	}
    
    @Bean
    CustomerActivityEventSender ccustomerActivityEventSender() {
		return new CustomerActivityEventSender();		
	}
    
    @Bean
    UserActivityEventReceiver userActivityEventReceiver() {
		return new UserActivityEventReceiver();		
	}
    
    @Bean
    UserActivityEventSender userActivityEventSender() {
		return new UserActivityEventSender();		
	}
    
    @Bean
    CustomerServiceActivityEventService customerServiceActivityEventService(){
    	return new CustomerServiceActivityEventService();    	
    }
    
    @Bean
    UserServiceActivityEventService userServiceActivityEventService() {
    	return new UserServiceActivityEventService();
    }


}