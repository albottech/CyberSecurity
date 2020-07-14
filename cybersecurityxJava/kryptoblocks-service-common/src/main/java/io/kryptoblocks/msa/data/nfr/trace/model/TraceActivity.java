package io.kryptoblocks.msa.data.nfr.trace.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new trace activity.
 */
@Data
public class TraceActivity {
	
	/** The cloud client host name. */
	@Value("${spring.cloud.client.hostname}")
	String cloudClientHostName;
	
	/** The app name. */
	@Value("${spring.application.name}")
	String appName;
	
	/** The app host port. */
	@Value("${server.port}")
	int appHostPort;
	
	/** The collected time. */
	String collectedTime;
	
	/** The reported time. */
	String reportedTime;
	
	/** The stream type. */
	String streamType;
	
	/** The collection type. */
	String collectionType;
	
	/** The service name. */
	String serviceName;
	
	/** The span. */
	Span span;	
}
