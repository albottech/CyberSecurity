package io.kryptoblocks.msa.common.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kryptoblocks.msa.common.exception.GlobalExceptionHandler;
import reactor.spring.context.config.EnableReactor;

/**
 * The Class CommonConfig.
 */
@Configuration
@EnableReactor
@EnableFeignClients
@Import({ MultiBeanFactoryConfig.class, TraceConfig.class, BusinessActivityConfig.class, 
		GlobalExceptionHandler.class, UtilConfig.class, ExceptionConfig.class, StreamSourceConfig.class, EventConfig.class, EventConfig.class,
		NfrRepositoryConfig.class })
public class CommonConfig {

}
