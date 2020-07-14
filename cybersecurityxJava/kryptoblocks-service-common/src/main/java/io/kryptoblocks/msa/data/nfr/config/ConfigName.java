package io.kryptoblocks.msa.data.nfr.config;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigName.
 */
public class ConfigName {
	
	/** The Constant NFR_PERSISTENCE_UNIT_NAME. */
	public static final String NFR_PERSISTENCE_UNIT_NAME = "cassandra_nfr";
	
	/** The Constant NFR_PERSISTENCE_XML_LOCATION. */
	public static final String NFR_PERSISTENCE_XML_LOCATION = "classpath*:META-INF/persistence.xml";	
	
	/** The Constant NFR_PERSISTENCE_SCHEMA_NAME. */
	public static final String NFR_PERSISTENCE_SCHEMA_NAME = "kryptoblocks_nfr@cassandra_nfr";
	
	 
	/** The Constant CHANNEL_TABLE_NAME. */
	/*
	public static final String CASSANDRA_NAMESPACE = "smartRide";
	public static final String CASSANDRA_HOST = "localhost";
	public static final String CASSANDRA_CLUSTER = "smartRideCassandraCluster";
	public static final int CASSANDRA_PORT = 9042;
	*/
	public static final String CHANNEL_TABLE_NAME = "channel";
	
	/** The Constant COORDINATOR_TABLE_NAME. */
	public static final String COORDINATOR_TABLE_NAME = "coordinator";
	
	/** The Constant CONTEXT_TABLE_NAME. */
	public static final String CONTEXT_TABLE_NAME = "context";
	
	/** The Constant SECURITY_ACTIVITY_TABLE_NAME. */
	public static final String SECURITY_ACTIVITY_TABLE_NAME = "security_activity";
	
	/** The Constant BUSINESS_ACTIVITY_TABLE_NAME. */
	public static final String BUSINESS_ACTIVITY_TABLE_NAME = "business_activity";
	
	/** The Constant EXCEPTION_ACTIVITY_TABLE_NAME. */
	public static final String EXCEPTION_ACTIVITY_TABLE_NAME = "business_activity";
	
	/** The Constant TRACE_ACTIVITY_TABLE_NAME. */
	public static final String TRACE_ACTIVITY_TABLE_NAME = "trace_activity";
	
	/** The Constant TRACE_SPAN_ACTIVITY_TABLE_NAME. */
	public static final String TRACE_SPAN_ACTIVITY_TABLE_NAME = "trace_span_activity";
	
	/** The Constant BUSINESS_ACTIVITY_ENTITY_UDT_NAME. */
	public static final String BUSINESS_ACTIVITY_ENTITY_UDT_NAME = "business_activity_entity";	
	
	/** The Constant TRACE_ACTIVITY_ENTITY_UDT_NAME. */
	public static final String TRACE_ACTIVITY_ENTITY_UDT_NAME = "trace_entity";	
	
	/** The Constant EXCEPTION_ACTIVITY_ENTITY_UDT_NAME. */
	public static final String EXCEPTION_ACTIVITY_ENTITY_UDT_NAME = "exception_activity_entity";	
	
	/** The Constant NOTIFICATION_ACTIVITY_TABLE_NAME. */
	public static final String NOTIFICATION_ACTIVITY_TABLE_NAME = "notification_activity";
	
	/** The Constant NOTIFICATION_ACTIVITY_ENTITY_NAME. */
	public static final String NOTIFICATION_ACTIVITY_ENTITY_NAME = "notification_activity_entity";
	
	/** The Constant APP_CONTAINER_PERFORMANCE_ACTIVITY_TABLE_NAME. */
	public static final String APP_CONTAINER_PERFORMANCE_ACTIVITY_TABLE_NAME = "app_container_performance_activity";
	
	/** The Constant APP_CONTAINER_PERFORMANCE_ACTIVITY_ENTITY_NAME. */
	public static final String APP_CONTAINER_PERFORMANCE_ACTIVITY_ENTITY_NAME = "app_container_performance_activity_entity";
	
	/** The Constant INFRA_CONTAINER_PERFORMANCE_ACTIVITY_TABLE_NAME. */
	public static final String INFRA_CONTAINER_PERFORMANCE_ACTIVITY_TABLE_NAME = "infra_container_performance_activity";
	
	/** The Constant INFRA_CONTAINER_PERFORMANCE_ACTIVITY_ENTITY_NAME. */
	public static final String INFRA_CONTAINER_PERFORMANCE_ACTIVITY_ENTITY_NAME = "infra_container_performance_activity_entity";
	
	/** The Constant AUTHENTICATION_RESULT_UDT_NAME. */
	public static final String AUTHENTICATION_RESULT_UDT_NAME = "authentication_result";
	
	/** The Constant AUTHORIZATION_VERIFICATION_RESULT_UDT_NAME. */
	public static final String AUTHORIZATION_VERIFICATION_RESULT_UDT_NAME = "authorization_verification_result";
	
	/** The Constant HISTOGRAM_UDT_NAME. */
	public static final String HISTOGRAM_UDT_NAME = "historam";
	
	/** The Constant METERED_ENTITY_UDT_NAME. */
	public static final String METERED_ENTITY_UDT_NAME = "metered_entity";
	
	/** The Constant REQUEST_ENTITY_UDT_NAME. */
	public static final String REQUEST_ENTITY_UDT_NAME = "request_entity";
	
	/** The Constant RESPONSE_ENTITY_UDT_NAME. */
	public static final String RESPONSE_ENTITY_UDT_NAME = "response_entity";
	
	/** The Constant TIMED_ENTITY_UDT_NAME. */
	public static final String TIMED_ENTITY_UDT_NAME = "timed_entity";
	
	/** The Constant TRACE_SPAN_ENTITY_UDT_NAME. */
	public static final String TRACE_SPAN_ENTITY_UDT_NAME = "trace_span_entity";
	
	/** The Constant CITY_ENTITY_UDT_NAME. */
	public static final String CITY_ENTITY_UDT_NAME = "city_entity";
	
	/** The Constant GEOCODE_ENTITY_UDT_NAME. */
	public static final String GEOCODE_ENTITY_UDT_NAME = "geocode_entity";
	
	/** The Constant HOST_ENTITY_UDT_NAME. */
	public static final String HOST_ENTITY_UDT_NAME = "host_entity";
	
	/** The Constant SPAN_ENTITY_UDT_NAME. */
	public static final String SPAN_ENTITY_UDT_NAME = "span_entity";
	
	/** The Constant SPAN_LOG_ENTITY_UDT_NAME. */
	public static final String SPAN_LOG_ENTITY_UDT_NAME = "span_log_entity";	
	
	/** The Constant INFRA_CONTAINER_ATTACHED_NETWORK_UDT_NAME. */
	public static final String INFRA_CONTAINER_ATTACHED_NETWORK_UDT_NAME = "infra_container_attached_network";
	
	/** The Constant INFRA_CONTAINER_BLOCK_IO_STATUS_UDT_NAME. */
	public static final String INFRA_CONTAINER_BLOCK_IO_STATUS_UDT_NAME = "infra_container_block_io_status";
	
	/** The Constant INFRA_CONTAINER_CHANGE_UDT_NAME. */
	public static final String INFRA_CONTAINER_CHANGE_UDT_NAME = "infra_container_change";
	
	/** The Constant INFRA_CONTAINER_CONFIG_UDT_NAME. */
	public static final String INFRA_CONTAINER_CONFIG_UDT_NAME = "infra_container_config";
	
	/** The Constant INFRA_CONTAINER_HEALTH_LOG_UDT_NAME. */
	public static final String INFRA_CONTAINER_HEALTH_LOG_UDT_NAME = "infra_container_health_log";
	
	/** The Constant INFRA_CONTAINER_HEALTH_UDT_NAME. */
	public static final String INFRA_CONTAINER_HEALTH_UDT_NAME = "infra_container_health";
	
	/** The Constant INFRA_CONTAINER_MOUNT_UDT_NAME. */
	public static final String INFRA_CONTAINER_MOUNT_UDT_NAME = "infra_container_mount";
	
	/** The Constant INFRA_CONTAINER_STATE_UDT_NAME. */
	public static final String INFRA_CONTAINER_STATE_UDT_NAME = "infra_container_state";
	
	/** The Constant INFRA_CONTAINER_STATS_UDT_NAME. */
	public static final String INFRA_CONTAINER_STATS_UDT_NAME = "infra_container_stats";
	
	/** The Constant INFRA_CONTAINER_CPU_STATS_UDT_NAME. */
	public static final String INFRA_CONTAINER_CPU_STATS_UDT_NAME = "infra_container_cpu_stats";
	
	/** The Constant INFRA_CONTAINER_CPU_USAGE_UDT_NAME. */
	public static final String INFRA_CONTAINER_CPU_USAGE_UDT_NAME = "infra_container_cpu_usage";
	
	/** The Constant INFRA_CONTAINER_ENDPOINT_CONFIG_UDT_NAME. */
	public static final String INFRA_CONTAINER_ENDPOINT_CONFIG_UDT_NAME = "infra_container_endpoint_config";	
	
	/** The Constant INFRA_CONTAINER_ENDPOINT_PAM_CONFIG_UDT_NAME. */
	public static final String INFRA_CONTAINER_ENDPOINT_PAM_CONFIG_UDT_NAME = "infra_container_endpoint_pam_config";
	
	/** The Constant INFRA_CONTAINER_HEALTH_CHECK_UDT_NAME. */
	public static final String INFRA_CONTAINER_HEALTH_CHECK_UDT_NAME = "infra_container_health_check";
	
	/** The Constant INFRA_CONTAINER_HOST_CONFIG_UDT_NAME. */
	public static final String INFRA_CONTAINER_HOST_CONFIG_UDT_NAME = "infra_container_host_config";
	
	/** The Constant INFRA_CONTAINER_DEVICE_UDT_NAME. */
	public static final String INFRA_CONTAINER_DEVICE_UDT_NAME = "infra_container_device_config";
	
	/** The Constant INFRA_CONTAINER_LOG_CONFIG_UDT_NAME. */
	public static final String INFRA_CONTAINER_LOG_CONFIG_UDT_NAME = "infra_container_log_config_config";	
	
	/** The Constant INFRA_CONTAINER_BLK_IO_DEVICE_RATE_UDT_NAME. */
	public static final String INFRA_CONTAINER_BLK_IO_DEVICE_RATE_UDT_NAME = "infra_container_blk_io_device_rate";
	
	/** The Constant INFRA_CONTAINER_BLK_IO_WEHT_DEVICE_UDT_NAME. */
	public static final String INFRA_CONTAINER_BLK_IO_WEHT_DEVICE_UDT_NAME = "infra_container_blk_io_weight_device";
	
	/** The Constant INFRA_CONTAINER_LX_CONFIG_PARAM_UDT_NAME. */
	public static final String INFRA_CONTAINER_LX_CONFIG_PARAM_UDT_NAME = "infra_container_lx_config_param";
	
	/** The Constant INFRA_CONTAINER_RESTART_POLICY_UDT_NAME. */
	public static final String INFRA_CONTAINER_RESTART_POLICY_UDT_NAME = "infra_container_restart_policy";
	
	/** The Constant INFRA_CONTAINER_ULIMIT_UDT_NAME. */
	public static final String INFRA_CONTAINER_ULIMIT_UDT_NAME = "infra_container_ulimit";	
	
	/** The Constant INFRA_CONTAINER_MEMORY_STATS_DETAILS_UDT_NAME. */
	public static final String INFRA_CONTAINER_MEMORY_STATS_DETAILS_UDT_NAME = "infra_container_memory_stats_details";
	
	/** The Constant INFRA_CONTAINER_MEMORY_STATS_UDT_NAME. */
	public static final String INFRA_CONTAINER_MEMORY_STATS_UDT_NAME = "infra_container_memory_stats";	
	
	/** The Constant INFRA_CONTAINER_NETWORKING_CONFIG_UDT_NAME. */
	public static final String INFRA_CONTAINER_NETWORKING_CONFIG_UDT_NAME = "infra_container_networking_config";
	
	/** The Constant INFRA_CONTAINER_NETWORKING_SETTINGS_UDT_NAME. */
	public static final String INFRA_CONTAINER_NETWORKING_SETTINGS_UDT_NAME = "infra_container_networking_settings";
	
	/** The Constant INFRA_CONTAINER_NETWORK_STATUS_UDT_NAME. */
	public static final String INFRA_CONTAINER_NETWORK_STATUS_UDT_NAME = "infra_container_network_status";
	
	/** The Constant INFRA_CONTAINER_NODE_UDT_NAME. */
	public static final String INFRA_CONTAINER_NODE_UDT_NAME = "infra_container_node";
	
	/** The Constant INFRA_CONTAINER_PORT_BINDING_UDT_NAME. */
	public static final String INFRA_CONTAINER_PORT_BINDING_UDT_NAME = "infra_container_port_binding";
	
	/** The Constant INFRA_CONTAINER_THROTTLING_DATA_UDT_NAME. */
	public static final String INFRA_CONTAINER_THROTTLING_DATA_UDT_NAME = "infra_container_throttling_data";
	
	/** The Constant INFRA_CONTAINER_TOP_RESULTS_UDT_NAME. */
	public static final String INFRA_CONTAINER_TOP_RESULTS_UDT_NAME = "infra_container_top_results";
	
	/** The Constant INFRA_CONTAINER_HOST_CONFIG_TABLE_NAME. */
	public static final String INFRA_CONTAINER_HOST_CONFIG_TABLE_NAME = "infra_container_host_config";
	
	/** The Constant INFRA_CONTAINER_HEALTH_CHECK_TABLE_NAME. */
	public static final String INFRA_CONTAINER_HEALTH_CHECK_TABLE_NAME = "infra_container_health_check_config";
	
	/** The Constant INFRA_CONTAINER_NETWORKING_CONFIG_TABLE_NAME. */
	public static final String INFRA_CONTAINER_NETWORKING_CONFIG_TABLE_NAME = "infra_container_networking_config";
	
	/** The Constant INFRA_CONTAINER_CONFIG_TABLE_NAME. */
	public static final String INFRA_CONTAINER_CONFIG_TABLE_NAME = "infra_container_config";
	
	/** The Constant INFRA_CONTAINER_HEALTH_TABLE_NAME. */
	public static final String INFRA_CONTAINER_HEALTH_TABLE_NAME = "infra_container_health";
	
	/** The Constant INFRA_CONTAINER_INFO_TABLE_NAME. */
	public static final String INFRA_CONTAINER_INFO_TABLE_NAME = "infra_container_info";
	
	/** The Constant INFRA_CONTAINER_STATE_TABLE_NAME. */
	public static final String INFRA_CONTAINER_STATE_TABLE_NAME = "infra_container_state";
	
	/** The Constant INFRA_CONTAINER_CHANGE_TABLE_NAME. */
	public static final String INFRA_CONTAINER_CHANGE_TABLE_NAME = "infra_container_change";
	
	/** The Constant INFRA_CONTAINER_STATS_TABLE_NAME. */
	public static final String INFRA_CONTAINER_STATS_TABLE_NAME = "infra_container_stats";
	
	/** The Constant INFRA_ENGINE_INFO_TABLE_NAME. */
	public static final String INFRA_ENGINE_INFO_TABLE_NAME = "infra_engine_info";
	
	/** The Constant INFRA_ENGINE_SWAM_INFO_TABLE_NAME. */
	public static final String INFRA_ENGINE_SWAM_INFO_TABLE_NAME = "infra_engine_swam_info";
	
	/** The Constant INFRA_ENGINE_SWAM_CLUSTER_INFO_TABLE_NAME. */
	public static final String INFRA_ENGINE_SWAM_CLUSTER_INFO_TABLE_NAME = "infra_engine_swam_cluster_info";
	
	
	
	
	
	/** The Constant INFRA_CONTAINER_NETWORK_SETTINGS_TABLE_NAME. */
	public static final String INFRA_CONTAINER_NETWORK_SETTINGS_TABLE_NAME = "infra_container_network_settings";
	
	/** The Constant INFRA_CONTAINER_TOP_RESULTS_TABLE_NAME. */
	public static final String INFRA_CONTAINER_TOP_RESULTS_TABLE_NAME = "infra_container_top_results";
	
	/** The Constant APP_CONTAINER_ENVIRONMENT_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_ENVIRONMENT_INFO_TABLE_NAME = "app_container_environment_info";
	
	/** The Constant APP_CONTAINER_HEALTH_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_HEALTH_INFO_TABLE_NAME = "app_container_health_info";
	
	/** The Constant APP_CONTAINER_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_INFO_TABLE_NAME = "app_container_info";
	
	/** The Constant APP_CONTAINER_COMPONENT_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_COMPONENT_INFO_TABLE_NAME = "app_container_component_info";
	
	/** The Constant APP_CONTAINER_PROPERTIES_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_PROPERTIES_INFO_TABLE_NAME = "app_container_properties_info";
	
	/** The Constant APP_CONTAINER_THREAD_DUMP_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_THREAD_DUMP_INFO_TABLE_NAME = "app_container_thread_dump_info";
	
	/** The Constant APP_CONTAINER_DATA_SOURCE_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_DATA_SOURCE_INFO_TABLE_NAME = "app_container_data_source_info";
	
	/** The Constant APP_CONTAINER_LOG_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_LOG_INFO_TABLE_NAME = "app_container_log_info";
	
	/** The Constant APP_CONTAINER_SYSTEM_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_SYSTEM_INFO_TABLE_NAME = "app_container_system_info";
	
	/** The Constant APP_CONTAINER_SERVLET_INFO_TABLE_NAME. */
	public static final String APP_CONTAINER_SERVLET_INFO_TABLE_NAME = "app_container_servlet_info";
	
	/** The Constant APP_OBJECT_PERF_INFO_TABLE_NAME. */
	public static final String APP_OBJECT_PERF_INFO_TABLE_NAME = "app_object_performance_info";
	
	 
	
	
	
	
 
}
