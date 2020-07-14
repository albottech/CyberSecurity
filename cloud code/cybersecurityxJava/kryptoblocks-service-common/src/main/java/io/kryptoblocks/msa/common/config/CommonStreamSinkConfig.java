package io.kryptoblocks.msa.common.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.receiver.AppContainerPerformanceStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.BusinessActivityStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.DirectActivityStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.DistributedNotificationStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.ExceptionStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.InfraContainerPerformanceStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.NotificationStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.SecurityStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.receiver.TraceStreamMessageReceiver;
import io.kryptoblocks.msa.common.stream.sink.AppContainerPerformanceStreamSink;
import io.kryptoblocks.msa.common.stream.sink.BusinessActivitityStreamSink;
import io.kryptoblocks.msa.common.stream.sink.DirectActivitityStreamSink;
import io.kryptoblocks.msa.common.stream.sink.DistributedNotificationStreamMessageSink;
import io.kryptoblocks.msa.common.stream.sink.ExceptionActivityStreamSink;
import io.kryptoblocks.msa.common.stream.sink.InfraContainerPerformanceStreamSink;
import io.kryptoblocks.msa.common.stream.sink.NotificationActivityStreamSink;
import io.kryptoblocks.msa.common.stream.sink.TraceActivityStreamSink;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonStreamSinkConfig.
 */
@Configuration
@EnableBinding({ AppContainerPerformanceStreamSink.class, BusinessActivitityStreamSink.class,
		DirectActivitityStreamSink.class, DistributedNotificationStreamMessageSink.class,
		ExceptionActivityStreamSink.class, InfraContainerPerformanceStreamSink.class,
		NotificationActivityStreamSink.class, TraceActivityStreamSink.class, ExceptionActivityStreamSink.class })
public class CommonStreamSinkConfig {

	/**
	 * App container performance stream message receiver.
	 *
	 * @return the app container performance stream message receiver
	 */
	@Bean
	AppContainerPerformanceStreamMessageReceiver appContainerPerformanceStreamMessageReceiver() {
		return new AppContainerPerformanceStreamMessageReceiver();
	}

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
	/*@Bean
	DirectActivityStreamMessageReceiver directActivityStreamMessageReceiver() {
		return new DirectActivityStreamMessageReceiver();

	}

	@Bean
	DistributedNotificationStreamMessageReceiver distributedNotificationStreamMessageReceiver() {
		return new DistributedNotificationStreamMessageReceiver();

	}
*/
	@Bean
	ExceptionStreamMessageReceiver exceptionStreamMessageReceiver() {
		return new ExceptionStreamMessageReceiver();
	}

	/**
	 * Infra container performance stream message receiver.
	 *
	 * @return the infra container performance stream message receiver
	 */
	@Bean
	InfraContainerPerformanceStreamMessageReceiver infraContainerPerformanceStreamMessageReceiver() {
		return new InfraContainerPerformanceStreamMessageReceiver();
	}

	/**
	 * Notification stream message receiver.
	 *
	 * @return the notification stream message receiver
	 */
	@Bean
	NotificationStreamMessageReceiver notificationStreamMessageReceiver() {
		return new NotificationStreamMessageReceiver();
	}

	/**
	 * Security stream message receiver.
	 *
	 * @return the security stream message receiver
	 */
	@Bean
	SecurityStreamMessageReceiver securityStreamMessageReceiver() {
		return new SecurityStreamMessageReceiver();
	}

	/**
	 * Trace stream message receiver.
	 *
	 * @return the trace stream message receiver
	 */
	@Bean
	TraceStreamMessageReceiver traceStreamMessageReceiver() {
		return new TraceStreamMessageReceiver();
	}

}
