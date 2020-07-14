package io.kryptoblocks.msa.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.metric.TraceMetricsAutoConfiguration;
import org.springframework.cloud.sleuth.sampler.SamplerProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.config.ChannelBindingAutoConfiguration;
import org.springframework.cloud.stream.config.ChannelsEndpointAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.support.PeriodicTrigger;

import io.kryptoblocks.msa.data.nfr.trace.service.HostLocator;
import io.kryptoblocks.msa.data.nfr.trace.service.ServerPropertiesHostLocator;
import io.kryptoblocks.msa.data.nfr.trace.service.TraceSource;
import io.kryptoblocks.msa.data.nfr.trace.service.TraceSpanStreamReporter;
import io.kryptoblocks.msa.data.nfr.trace.service.TraceStreamProperties;
import io.kryptoblocks.msa.data.nfr.trace.service.TracerIgnoringChannelInterceptor;
 

// TODO: Auto-generated Javadoc
/**
 * The Class TraceConfig.
 */
@Configuration
@EnableConfigurationProperties({ TraceStreamProperties.class, SamplerProperties.class })
@AutoConfigureAfter(TraceMetricsAutoConfiguration.class)
@AutoConfigureBefore({ ChannelBindingAutoConfiguration.class, TraceAutoConfiguration.class, ChannelsEndpointAutoConfiguration.class })
@EnableBinding(TraceSource.class)
public class TraceConfig {
	
	/** The span adjusters. */
	@Autowired(required = false) List<SpanAdjuster> spanAdjusters = new ArrayList<>();	
	
	/** The server properties. */
	@Autowired(required = false)
	private ServerProperties serverProperties;

	/** The inet utils. */
	@Autowired
	private InetUtils inetUtils;

	/** The environment. */
	@Autowired
	private Environment environment;
	
	/**
	 * Host locator.
	 *
	 * @return the host locator
	 */
	@Bean
	public HostLocator hostLocator() {
		return new ServerPropertiesHostLocator(this.serverProperties, this.environment, 
				this.inetUtils);
	}
	
	/**
	 * Zipkin channel interceptor.
	 *
	 * @param spanMetricReporter the span metric reporter
	 * @return the channel interceptor
	 */
	@Bean
	@GlobalChannelInterceptor(patterns = TraceSource.OUTPUT, order = Ordered.HIGHEST_PRECEDENCE)
	public ChannelInterceptor zipkinChannelInterceptor(SpanMetricReporter spanMetricReporter) {
		return new TracerIgnoringChannelInterceptor(spanMetricReporter);
	}
	
	/**
	 * Trace span stream reporter.
	 *
	 * @param environment the environment
	 * @return the trace span stream reporter
	 */
	@Bean
	public TraceSpanStreamReporter traceSpanStreamReporter(Environment environment) {
		return new TraceSpanStreamReporter(environment,
				this.spanAdjusters);
	}
	
	/**
	 * Default stream span reporter poller.
	 *
	 * @param sleuth the sleuth
	 * @return the poller metadata
	 */
	@Bean(name = TraceSpanStreamReporter.POLLER)	
	public PollerMetadata defaultStreamSpanReporterPoller(TraceStreamProperties sleuth) {
		PollerMetadata poller = new PollerMetadata();
		poller.setTrigger(new PeriodicTrigger(sleuth.getPoller().getFixedDelay()));
		poller.setMaxMessagesPerPoll(sleuth.getPoller().getMaxMessagesPerPoll());
		return poller;
	}
	
}
