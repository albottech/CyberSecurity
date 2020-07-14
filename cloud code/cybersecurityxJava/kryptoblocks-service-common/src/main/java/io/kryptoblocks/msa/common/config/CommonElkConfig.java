package io.kryptoblocks.msa.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.condition.ElkStatus;
import io.kryptoblocks.msa.common.elastic.AppContainerPerformanceIndexBuilder;
import io.kryptoblocks.msa.common.elastic.BusinessActivityIndexBuilder;
import io.kryptoblocks.msa.common.elastic.ExceptionActivityIndexBuilder;
import io.kryptoblocks.msa.common.elastic.InfraContainerPerformanceIndexBuilder;
import io.kryptoblocks.msa.common.elastic.NotificationActivityIndexBuilder;
import io.kryptoblocks.msa.common.elastic.SecurityActivityIndexBuilder;
import io.kryptoblocks.msa.common.elastic.TraceActivityIndexBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

 
 
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonElkConfig.
 */
@Configuration
public class CommonElkConfig {
	
	/** The service name. */
	@Value("${spring.application.name}")	
	private String serviceName;
	
	/** The elk cluster enabled. */
	@Value("${elk.cluster.enabled}")	
	private boolean elkClusterEnabled;
	
	/** The elk cluster name. */
	@Value("${elk.cluster.name}")	
	private String elkClusterName;
	
	/** The elk local host. */
	@Value("${elk.local.host}")	
	private String elkLocalHost;
	
	/** The elk local port. */
	@Value("${elk.local.port}")	
	private int elkLocalPort;
	
	
	/**
	 * Elastic transport client.
	 *
	 * @return the transport client
	 * @throws UnknownHostException the unknown host exception
	 */
	@Bean
	@Conditional(ElkStatus.class)
	TransportClient  elasticTransportClient() throws UnknownHostException {
		TransportClient elasticClusterClient = null;
	
		if(elkClusterEnabled) {
		Builder elasticClusterBuilder = Settings.builder();
		elasticClusterBuilder.put("client.transport.sniff", true);
		elasticClusterBuilder.put("cluster.name", elkClusterName);
		Settings elasticClusterSettings = elasticClusterBuilder.build();				
		elasticClusterClient = new PreBuiltTransportClient(elasticClusterSettings);}
		else {
			TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
			        .addTransportAddress(new TransportAddress(InetAddress.getByName(elkLocalHost), elkLocalPort));
		}		
		return elasticClusterClient;		
	}
	
	/**
	 * App container performance index builder.
	 *
	 * @return the app container performance index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	AppContainerPerformanceIndexBuilder appContainerPerformanceIndexBuilder() {		
		return new AppContainerPerformanceIndexBuilder();
	}
	
	/**
	 * Business activity index builder.
	 *
	 * @return the business activity index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	BusinessActivityIndexBuilder businessActivityIndexBuilder() {
		return new BusinessActivityIndexBuilder();
	}
	
	/**
	 * Exception activity index builder.
	 *
	 * @return the exception activity index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	ExceptionActivityIndexBuilder exceptionActivityIndexBuilder() {
		return new ExceptionActivityIndexBuilder();
	}
	
	/**
	 * Infra container performance index builder.
	 *
	 * @return the infra container performance index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	InfraContainerPerformanceIndexBuilder infraContainerPerformanceIndexBuilder() {		
		return new InfraContainerPerformanceIndexBuilder();
	}
		
	/**
	 * Notification activity index builder.
	 *
	 * @return the notification activity index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	NotificationActivityIndexBuilder notificationActivityIndexBuilder() {
		return new NotificationActivityIndexBuilder();
	}
	
	/**
	 * Security activity index builder.
	 *
	 * @return the security activity index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	SecurityActivityIndexBuilder securityActivityIndexBuilder() {
		return new SecurityActivityIndexBuilder();
	}
	
	/**
	 * Trace activity index builder.
	 *
	 * @return the trace activity index builder
	 */
	@Bean
	@Conditional(ElkStatus.class)
	TraceActivityIndexBuilder traceActivityIndexBuilder() {
		return new TraceActivityIndexBuilder();
	}
	
}
