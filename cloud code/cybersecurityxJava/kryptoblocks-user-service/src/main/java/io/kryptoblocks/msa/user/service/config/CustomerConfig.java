package io.kryptoblocks.msa.user.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.user.service.business.CustomerService;
import io.kryptoblocks.msa.user.service.business.impl.CustomerServiceImpl;

@Configuration
public class CustomerConfig {
     
    @Bean
	CustomerService customerService() {
		return new CustomerServiceImpl();
		
	}


}