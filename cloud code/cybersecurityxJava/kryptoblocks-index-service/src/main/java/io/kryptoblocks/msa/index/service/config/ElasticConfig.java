package io.kryptoblocks.msa.index.service.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateAction;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

import com.google.common.io.ByteStreams;

import io.kryptoblocks.msa.common.config.CommonConfig;
import io.kryptoblocks.msa.common.config.NfrRepositoryConfig;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.index.service.util.IndexResourceUtil;
 
// TODO: Auto-generated Javadoc
/**
 * The Class ElasticConfig.
 */
@Configuration
@Import({CommonConfig.class,  NfrRepositoryConfig.class})
public class ElasticConfig {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticConfig.class);
	
	/** The service name. */
	@Value("${spring.application.name}")	
	private String serviceName;
	
	/** The elastic cluster enabled. */
	@Value("${elastic.cluster.enabled}")	
	private boolean elasticClusterEnabled;
	
	/** The elastic cluster name. */
	@Value("${elastic.cluster.name}")	
	private String elasticClusterName;
	
	/** The elastic local host. */
	@Value("${elastic.local.host}")	
	private String elasticLocalHost;
	
	/** The elastic local port. */
	@Value("${elastic.local.port}")	
	private int elasticLocalPort;
	
	/** The performance metric index name. */
	@Value("${performance.metric.index.name}")	
	private String performanceMetricIndexName;
	
	/** The performance metric index type. */
	@Value("${performance.metric.index.type}")	
	private String performanceMetricIndexType;
	
	/** The trace metric index name. */
	@Value("${trace.metric.index.name}")	
	private String traceMetricIndexName;
	
	/** The trace metric index type. */
	@Value("${trace.metric.index.type}")	
	private String traceMetricIndexType;
	
	
	/** The exception metric index name. */
	@Value("${exception.metric.index.name}")	
	private String exceptionMetricIndexName;
	
	/** The exception metric index type. */
	@Value("${exception.metric.index.type}")	
	private String exceptionMetricIndexType;
	
	/** The business activity metric index name. */
	@Value("${business.activity.metric.index.name}")	
	private String businessActivityMetricIndexName;
	
	/** The business activity metric index type. */
	@Value("${business.activity.metric.index.type}")	
	private String businessActivityMetricIndexType;
	
	 
	
	/** The index location. */
	private final String INDEX_LOCATION = "classpath*:index/*.json";
	
	/**
	 * Elastic transport client.
	 *
	 * @return the transport client
	 * @throws UnknownHostException the unknown host exception
	 */
	@Bean
	TransportClient  elasticTransportClient() throws UnknownHostException {
		TransportClient elasticClusterClient = null;
	
		if(elasticClusterEnabled) {
		Builder elasticClusterBuilder = Settings.builder();
		elasticClusterBuilder.put("client.transport.sniff", true);
		elasticClusterBuilder.put("cluster.name", elasticClusterName);
		Settings elasticClusterSettings = elasticClusterBuilder.build();				
		elasticClusterClient = new PreBuiltTransportClient(elasticClusterSettings);}
		 
			else {
				elasticClusterClient = new PreBuiltTransportClient(Settings.EMPTY)
				        .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticLocalHost), elasticLocalPort));
			}	
		 	
		return elasticClusterClient;		
	}
	
	/**
	 * Json uti.
	 *
	 * @return the json util
	 */
	@Bean
	JsonUtil jsonUti(){
		return new JsonUtil();
	}
	
	/*
	 * @Bean AppContainerPerformanceIndexBuilder
	 * appContainerPerformanceIndexBuilder() { return new
	 * AppContainerPerformanceIndexBuilder(); }
	 * 
	 * @Bean InfraContainerPerformanceIndexBuilder
	 * infraContainerPerformanceIndexBuilder() { return new
	 * InfraContainerPerformanceIndexBuilder(); }
	 * 
	 * @Bean BusinessActivityIndexBuilder businessActivityIndexBuilder() { return
	 * new BusinessActivityIndexBuilder(); }
	 * 
	 * @Bean ExceptionActivityIndexBuilder exceptionActivityIndexBuilder() { return
	 * new ExceptionActivityIndexBuilder(); }
	 * 
	 * @Bean NotificationActivityIndexBuilder notificationActivityIndexBuilder() {
	 * return new NotificationActivityIndexBuilder(); }
	 * 
	 * @Bean SecurityActivityIndexBuilder securityActivityIndexBuilder() { return
	 * new SecurityActivityIndexBuilder(); }
	 * 
	 * @Bean TraceActivityIndexBuilder traceActivityIndexBuilder() { return new
	 * TraceActivityIndexBuilder(); }
	 * 
	 * 
	 * @Bean IndexRequestBuilder performanceMetricIndexBuilder() throws
	 * UnknownHostException{
	 * 
	 * return elasticTransportClient().prepareIndex(performanceMetricIndexName,
	 * performanceMetricIndexType); }
	 * 
	 * @Bean IndexRequestBuilder traceMetricIndexBuilder() throws
	 * UnknownHostException { return
	 * elasticTransportClient().prepareIndex(traceMetricIndexName,
	 * traceMetricIndexType); }
	 * 
	 * @Bean IndexRequestBuilder exceptionMetricIndexBuilder() throws
	 * UnknownHostException { return
	 * elasticTransportClient().prepareIndex(exceptionMetricIndexName,
	 * exceptionMetricIndexType); }
	 * 
	 * @Bean IndexRequestBuilder businessActivityMetricIndexBuilder() throws
	 * UnknownHostException { return
	 * elasticTransportClient().prepareIndex(businessActivityMetricIndexName,
	 * businessActivityMetricIndexType); }
	 */
	
	/**
	 * Index resource util.
	 *
	 * @return the index resource util
	 */
	@Bean
	IndexResourceUtil indexResourceUtil() {
		return new IndexResourceUtil();
	}
	
	/**
	 * Index map.
	 *
	 * @return the map
	 * @throws Exception the exception
	 */
	@Bean
	Map<String, Boolean> indexMap( ) throws Exception{
		
		Map<String, Boolean> returnValue = new HashMap<String, Boolean>();
		Resource[] indexResources = null;
		indexResources = indexResourceUtil().getIndexResource(INDEX_LOCATION);
		
		if(indexResources != null) {
	 
		for(Resource resource: indexResources) {
			String indexName = resource.getFilename();
			LOGGER.debug("Loading resource from class path : current index templace name: {}", indexName);
			byte[] indexByteArray = ByteStreams.toByteArray(resource.getInputStream());
			PutIndexTemplateRequest indexRequest = new PutIndexTemplateRequest(indexName);
			indexRequest.source(new String(indexByteArray, "UTF-8"), XContentType.JSON);
			 
			AcknowledgedResponse response = elasticTransportClient().admin().indices().execute(PutIndexTemplateAction.INSTANCE, indexRequest).get();
			      
			  if (!response.isAcknowledged()) {
			    throw new Exception("Error While Updating Template");
			  } else {
				  returnValue.put(indexName, new Boolean(response.isAcknowledged()));
				  LOGGER.debug("Index template updated successfully on the elasticsearch for template: {}", indexName);
			  }
		}
		}
		else {
			throw new Exception("Unable to get the index resource template names from class path with  location mame:" + INDEX_LOCATION) ;
			}
		
		return returnValue;
		
	}

}
