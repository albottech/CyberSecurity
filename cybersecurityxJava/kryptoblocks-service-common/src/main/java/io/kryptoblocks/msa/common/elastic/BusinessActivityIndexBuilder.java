package io.kryptoblocks.msa.common.elastic;

import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.names.NameCollection;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityIndexBuilder.
 */
public class BusinessActivityIndexBuilder  {
	
	/** The Constant BUSINESS_ACTIVITY. */
	private static final String BUSINESS_ACTIVITY = "BUSINESS_ACTIVITY";

	/** The service name. */
	@Value("${spring.application.name}")	
	private String serviceName;
	
	/** The host name. */
	@Value("${spring.cloud.client.hostname}")	
	private String hostName;
	
	/** The port. */
	@Value("${server.port}")	
	private String port;
	
	
	/** The elastic transport client. */
	@Autowired
	TransportClient  elasticTransportClient;
	
	/**
	 * Gets the performance metric index builder.
	 *
	 * @param performanceType the performance type
	 * @return the performance metric index builder
	 * @throws UnknownHostException the unknown host exception
	 */
	public IndexRequestBuilder getPerformanceMetricIndexBuilder(String performanceType) throws UnknownHostException{		
		return  elasticTransportClient.prepareIndex(name(performanceType), type(performanceType));			
	}
	
	/**
	 * Name.
	 *
	 * @param type the type
	 * @return the string
	 */
	private String name(String type) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(serviceName);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(hostName);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(port);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(BUSINESS_ACTIVITY);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(type);
		return stringBuilder.toString().toUpperCase();
	}
	
	/**
	 * Type.
	 *
	 * @param type the type
	 * @return the string
	 */
	private String type(String type) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(serviceName);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(hostName);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(port);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(BUSINESS_ACTIVITY);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(type);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append("TYPE");
		return stringBuilder.toString().toUpperCase();
	}
		
}
