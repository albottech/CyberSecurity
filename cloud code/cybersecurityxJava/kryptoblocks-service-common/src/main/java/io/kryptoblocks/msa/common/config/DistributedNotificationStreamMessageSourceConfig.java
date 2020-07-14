package io.kryptoblocks.msa.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.sender.DistributedNotificationStreamMessageSender;
import io.kryptoblocks.msa.common.stream.source.DistributedNotificationStreamMessageSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributedNotificationStreamMessageSourceConfig.
 */
@Configuration
@EnableBinding({DistributedNotificationStreamMessageSource.class})
public class DistributedNotificationStreamMessageSourceConfig {
	
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
	 * Distributed notification stream message sender.
	 *
	 * @return the distributed notification stream message sender
	 */
	@Bean
	DistributedNotificationStreamMessageSender distributedNotificationStreamMessageSender() {		
		return new DistributedNotificationStreamMessageSender();		
	}
	
}
