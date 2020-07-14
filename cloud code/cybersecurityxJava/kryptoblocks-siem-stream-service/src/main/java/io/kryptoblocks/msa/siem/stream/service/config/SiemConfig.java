package io.kryptoblocks.msa.siem.stream.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.siem.stream.service.business.SiemDataProcessService;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDataStreamService;
import io.kryptoblocks.msa.siem.stream.service.business.impl.SiemDataProcessServiceImpl;
import io.kryptoblocks.msa.siem.stream.service.business.impl.SiemDataStreamServiceImpl;
 

// TODO: Auto-generated Javadoc
/**
 * The Class SiemConfig.
 */
@Configuration
public class SiemConfig {
     
    /**
     * Network data process service.
     *
     * @return the siem data process service
     */
    @Bean
    SiemDataProcessService networkDataProcessService() {
		return new SiemDataProcessServiceImpl();
		
	}
    
    /**
     * Network data stream service.
     *
     * @return the siem data stream service
     */
    @Bean
    SiemDataStreamService networkDataStreamService() {
		return new SiemDataStreamServiceImpl();
		
	}
    
    
    
 

}