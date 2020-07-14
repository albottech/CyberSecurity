package io.kryptoblocks.msa.common.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.receiver.BusinessActivityStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.ExceptionStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.sink.BusinessActivitityStreamSink;
import io.kryptoblocks.msa.common.stream.sink.ExceptionActivityStreamSink;
 


// TODO: Auto-generated Javadoc
/**
 * The Class StreamSinkConfig.
 */
@Configuration
@EnableBinding({ExceptionActivityStreamSink.class, BusinessActivitityStreamSink.class})
public class StreamSinkConfig {
	
	
	
	/**
	 * Business activity stream message receiver.
	 *
	 * @return the business activity stream message receiver
	 */
	@Bean
	BusinessActivityStreamMessageReceiver businessActivityStreamMessageReceiver() {
		return new BusinessActivityStreamMessageReceiver();		
	}
	
	/**
	 * Exception stream message receiver.
	 *
	 * @return the exception stream message receiver
	 */
	@Bean
	ExceptionStreamMessageReceiver exceptionStreamMessageReceiver() {
		return new ExceptionStreamMessageReceiver();		
	}
	
	 
	

}
