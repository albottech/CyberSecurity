package io.kryptoblocks.msa.user.service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.user.service.business.UserService;
import io.kryptoblocks.msa.user.service.business.impl.UserServiceImpl;

@Configuration
public class UserConfig {
     
    @Bean
	UserService userService() {
		return new UserServiceImpl();
		
	}


}