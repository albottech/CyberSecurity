package io.kryptoblocks.msa.user.service.config;


import org.apache.commons.validator.routines.CreditCardValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.user.service.ui.util.UserCreditCardValidator;
import io.kryptoblocks.msa.user.service.ui.util.UserDLValidator;
import io.kryptoblocks.msa.user.service.ui.util.UserVehicleValidator;

@Configuration
public class UtilConfig {
    
	@Bean
	CreditCardValidator creditCardValidator() {
		return new CreditCardValidator();
	}
    @Bean
    UserCreditCardValidator userCreditCardValidator() {
    	return new UserCreditCardValidator();
    }
    
    @Bean
    UserVehicleValidator userVehicleValidator() {
    	return new UserVehicleValidator();
    }
    
    @Bean
    UserDLValidator userDLValidator() {
    	return new UserDLValidator();
    }

}