/*
 * The security service applications class. This class is the spring boot application context initializer for security service
 * <p>
 * 
 * Copyright 2017-2017 MetLife Investment.
 */
package io.kryptoblocks.msa.security.service;


import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;

import io.kryptoblocks.msa.security.service.common.AuthorityName;
import reactor.spring.context.config.EnableReactor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityServiceApplication.
 *
 * @author      MetLife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class SecurityServiceApplication {
	
	static {
	    SLF4JBridgeHandler.install();
	}

	/**
	 * Static main method to start the application as a JAVA system service.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}	
	 
}
