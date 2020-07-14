package io.kryptoblocks.msa.network.stream.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventReceiver;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventSender;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
 

// TODO: Auto-generated Javadoc
/**
 * The Class EventConfig.
 */
@Configuration
public class EventConfig {
     
    /**
     * Ccustomer activity event receiver.
     *
     * @return the network data process activity event receiver
     */
    @Bean
    NetworkDataProcessActivityEventReceiver ccustomerActivityEventReceiver() {
		return new NetworkDataProcessActivityEventReceiver();		
	}
    
    /**
     * Network data process activity event sender.
     *
     * @return the network data process activity event sender
     */
    @Bean
    NetworkDataProcessActivityEventSender networkDataProcessActivityEventSender() {
		return new NetworkDataProcessActivityEventSender();		
	}
    
    
    
    /**
     * Network data process activity event service.
     *
     * @return the network data process activity event service
     */
    @Bean
    NetworkDataProcessActivityEventService networkDataProcessActivityEventService() {
		return new NetworkDataProcessActivityEventService();
		
	}
    
     


}