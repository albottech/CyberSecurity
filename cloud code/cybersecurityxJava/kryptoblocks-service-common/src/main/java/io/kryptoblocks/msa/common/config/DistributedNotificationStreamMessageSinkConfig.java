package io.kryptoblocks.msa.common.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.receiver.DistributedNotificationStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.sink.DistributedNotificationStreamMessageSink;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributedNotificationStreamMessageSinkConfig.
 */
@Configuration

@EnableBinding({DistributedNotificationStreamMessageSink.class})
public class DistributedNotificationStreamMessageSinkConfig {

	/**
	 * Distributed notification stream message.
	 *
	 * @return the distributed notification stream message receiver
	 */
	@Bean
	DistributedNotificationStreamMessageReceiver distributedNotificationStreamMessage() {
		return new DistributedNotificationStreamMessageReceiver();
	}

}
