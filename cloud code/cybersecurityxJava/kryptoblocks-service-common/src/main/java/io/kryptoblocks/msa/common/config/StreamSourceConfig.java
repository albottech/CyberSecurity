package io.kryptoblocks.msa.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.stream.sender.AppContainerPerformanceStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.BusinessActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.DirectActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.DistributedNotificationStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.ExceptionActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.InfraContainerPerformanceStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.NotificationActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.SecurityActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.TraceActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.source.AppContainerPerformanceStreamSource;
import io.kryptoblocks.msa.common.stream.source.BusinessActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.DirectActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.DistributedNotificationStreamMessageSource;
import io.kryptoblocks.msa.common.stream.source.ExceptionActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.InfraContainerPerformanceStreamSource;
import io.kryptoblocks.msa.common.stream.source.NotificationActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.SecurityActivityStreamSource;
import io.kryptoblocks.msa.common.stream.source.TraceActivityStreamSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class StreamSourceConfig.
 */
@Configuration
@EnableBinding({ AppContainerPerformanceStreamSource.class, BusinessActivityStreamSource.class,
		DirectActivityStreamSource.class, DistributedNotificationStreamMessageSource.class,
		ExceptionActivityStreamSource.class, InfraContainerPerformanceStreamSource.class,
		NotificationActivityStreamSource.class, SecurityActivityStreamSource.class, TraceActivityStreamSource.class })
public class StreamSourceConfig {

	/** The span adjusters. */
	@Autowired(required = false)
	List<SpanAdjuster> spanAdjusters = new ArrayList<>();

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
	 * Default sampler.
	 *
	 * @return the sampler
	 */
	@Bean
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	}

	/**
	 * App container performance stream message sender.
	 *
	 * @return the app container performance stream message sender
	 */
	@Bean
	AppContainerPerformanceStreamMessageSender appContainerPerformanceStreamMessageSender() {
		return new AppContainerPerformanceStreamMessageSender();
	}

	/**
	 * Business activity stream message sender.
	 *
	 * @return the business activity stream message sender
	 */
	@Bean
	BusinessActivityStreamMessageSender businessActivityStreamMessageSender() {
		return new BusinessActivityStreamMessageSender();
	}

	/**
	 * Direct activity stream message sender.
	 *
	 * @return the direct activity stream message sender
	 */
	@Bean
	DirectActivityStreamMessageSender directActivityStreamMessageSender() {
		return new DirectActivityStreamMessageSender();

	}

	/**
	 * Distributed notification stream message sender.
	 *
	 * @return the distributed notification stream message sender
	 */
	@Bean
	DistributedNotificationStreamMessageSender distributedNotificationStreamMessageSender() {
		return new DistributedNotificationStreamMessageSender();

	}

	/**
	 * Exception activity stream message sender.
	 *
	 * @return the exception activity stream message sender
	 */
	@Bean
	ExceptionActivityStreamMessageSender exceptionActivityStreamMessageSender() {
		return new ExceptionActivityStreamMessageSender();
	}

	/**
	 * Infra container performance stream message sender.
	 *
	 * @return the infra container performance stream message sender
	 */
	@Bean
	InfraContainerPerformanceStreamMessageSender infraContainerPerformanceStreamMessageSender() {
		return new InfraContainerPerformanceStreamMessageSender();
	}

	/**
	 * Notification activity stream message sender.
	 *
	 * @return the notification activity stream message sender
	 */
	@Bean
	NotificationActivityStreamMessageSender notificationActivityStreamMessageSender() {
		return new NotificationActivityStreamMessageSender();
	}

	/**
	 * Security activity stream message sender.
	 *
	 * @return the security activity stream message sender
	 */
	@Bean
	SecurityActivityStreamMessageSender securityActivityStreamMessageSender() {
		return new SecurityActivityStreamMessageSender();
	}

	/**
	 * Trace activity stream message sender.
	 *
	 * @return the trace activity stream message sender
	 */
	@Bean
	TraceActivityStreamMessageSender traceActivityStreamMessageSender() {
		return new TraceActivityStreamMessageSender();
	}
}
