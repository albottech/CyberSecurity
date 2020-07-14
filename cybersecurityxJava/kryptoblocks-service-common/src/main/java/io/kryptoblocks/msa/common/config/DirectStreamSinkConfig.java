package io.kryptoblocks.msa.common.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.receiver.DirectActivityStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.sink.DirectActivitityStreamSink;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectStreamSinkConfig.
 */
@Configuration
@EnableBinding({DirectActivitityStreamSink.class})
public class DirectStreamSinkConfig {

	/**
	 * Direct activity stream message receiver.
	 *
	 * @return the direct activity stream message receiver
	 */
	@Bean
	DirectActivityStreamMessageReceiver directActivityStreamMessageReceiver() {
		return new DirectActivityStreamMessageReceiver();
	}

}
