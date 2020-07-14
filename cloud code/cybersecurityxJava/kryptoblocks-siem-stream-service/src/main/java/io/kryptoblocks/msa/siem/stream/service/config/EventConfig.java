package io.kryptoblocks.msa.siem.stream.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.siem.stream.service.audit.event.SiemDataProcessActivityEventReceiver;
import io.kryptoblocks.msa.siem.stream.service.audit.event.SiemDataProcessActivityEventSender;
import io.kryptoblocks.msa.siem.stream.service.audit.event.SiemDataProcessActivityEventService;
 

// TODO: Auto-generated Javadoc
/**
 * The Class EventConfig.
 */
@Configuration
public class EventConfig {
     
    /**
     * Ccustomer activity event receiver.
     *
     * @return the siem data process activity event receiver
     */
    @Bean
    SiemDataProcessActivityEventReceiver ccustomerActivityEventReceiver() {
		return new SiemDataProcessActivityEventReceiver();		
	}
    
    /**
     * Network data process activity event sender.
     *
     * @return the siem data process activity event sender
     */
    @Bean
    SiemDataProcessActivityEventSender networkDataProcessActivityEventSender() {
		return new SiemDataProcessActivityEventSender();		
	}
    
    
    
    /**
     * Network data process activity event service.
     *
     * @return the siem data process activity event service
     */
    @Bean
    SiemDataProcessActivityEventService networkDataProcessActivityEventService() {
		return new SiemDataProcessActivityEventService();
		
	}
    
     


}