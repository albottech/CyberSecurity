package io.kryptoblocks.msa.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.sender.DirectActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.source.DirectActivityStreamSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectStreamSourceConfig.
 */
@Configuration
@EnableBinding({DirectActivityStreamSource.class})
public class DirectStreamSourceConfig {
	
	/** The service name. */
	@Value("${spring.application.name}")
	
	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	@Getter 
 /**
  * Sets the service name.
  *
  * @param serviceName the new service name
  */
 @Setter(AccessLevel.PUBLIC)
	private String serviceName;	
	
	 
	
	/**
	 * Direct activity stream message sender.
	 *
	 * @return the direct activity stream message sender
	 */
	@Bean
	DirectActivityStreamMessageSender directActivityStreamMessageSender() {		
		return new DirectActivityStreamMessageSender();		
	}
	
}
