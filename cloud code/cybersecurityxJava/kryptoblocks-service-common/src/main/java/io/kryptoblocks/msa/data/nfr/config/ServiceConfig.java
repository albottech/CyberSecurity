package io.kryptoblocks.msa.data.nfr.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.jta.JtaTransactionManager;

import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;


// TODO: Auto-generated Javadoc
/**
 * The Class ServiceConfig.
 */
@Configuration 
public class ServiceConfig {
	 
	
	/**
	 * Activity service.
	 *
	 * @return the nfr repository service
	 */
	@Bean
	NfrRepositoryService activityService() {
		return new NfrRepositoryService();
	}
	
	
}
