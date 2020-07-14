package io.kryptoblocks.msa.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.jwt.JWTTokenUtil;
import io.kryptoblocks.msa.common.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class UtilConfig.
 */
@Configuration
public class UtilConfig {
	
	/**
	 * String encryption util.
	 *
	 * @return the string encryption util
	 */
	@Bean
	StringEncryptionUtil stringEncryptionUtil() {
		return new StringEncryptionUtil(5);
	}
	
	/**
	 * Json util.
	 *
	 * @return the json util
	 */
	@Bean
	JsonUtil jsonUtil() {
		return new JsonUtil();
	}
	
	/**
	 * City loader.
	 *
	 * @return the city loader
	 */
	@Bean
	CityLoader cityLoader() {
		return new CityLoader();
	}
	
	/**
	 * Base log config.
	 *
	 * @return the base log config
	 */
	@Bean
	BaseLogConfig baseLogConfig() {
		return new BaseLogConfig();
	}
	
	/**
	 * Logger util.
	 *
	 * @return the logger util
	 */
	@Bean
	LoggerUtil LoggerUtil() {
		return new LoggerUtil();		
	}
	
	/**
	 * Jwt token util.
	 *
	 * @return the JWT token util
	 */
	@Bean
	JWTTokenUtil jwtTokenUtil() {
		return new JWTTokenUtil();
	}
	
	
	 

}
