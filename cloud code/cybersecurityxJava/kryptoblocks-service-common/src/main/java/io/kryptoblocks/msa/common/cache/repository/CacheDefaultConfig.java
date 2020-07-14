package io.kryptoblocks.msa.common.cache.repository;


import org.springframework.beans.factory.annotation.Value;
import lombok.Data;
 
// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache default config.
 */
@Data
public class CacheDefaultConfig {
	
	/** The severe alert acknowledgement threshold. */
	@Value("${ack-severe-alert-threshold}")
	String severeAlertAcknowledgementThreshold;
	
	/** The acknowledgement threshold. */
	@Value("${ack-wait-threshold}")
	String acknowledgementThreshold;
	
	/** The archive disk space limit. */
	@Value("${archive-disk-space-limit}")
	String archiveDiskSpaceLimit;
	
	/** The archive file size limit. */
	@Value("${archive-file-size-limit}")
	String archiveFileSizeLimit;
	
	/** The async distribution timeout. */
	@Value("${async-distribution-timeout}")
	String asyncDistributionTimeout;
	
	/** The async max queue size. */
	@Value("${async-max-queue-size}")
	String asyncMaxQueueSize;
	
	/** The async queue timeout. */
	@Value("${async-queue-timeout}")
	String asyncQueueTimeout;
	
	/** The cluster SSL ciphers. */
	@Value("${cluster-ssl-ciphers}")
	String clusterSSLCiphers;
	
	/** The cluster SSL enabled. */
	@Value("${cluster-ssl-enabled}")
	String clusterSSLEnabled;
	
	/** The cluster SSL protocol. */
	@Value("${cluster-ssl-protocols}")
	String clusterSSLProtocol;
	
	/** The cluster SSL require authentication. */
	@Value("${cluster-ssl-require-authentication}")
	String clusterSSLRequireAuthentication;
	
	/** The conflate events. */
	@Value("${conflate-events}")
	String conflateEvents;
	
	/** The conserve sockets. */
	@Value("${conserve-sockets}")
	String conserveSockets;
	
	/** The delta propagation. */
	@Value("${delta-propagation}")
	String deltaPropagation;
	
	/** The disable auto reconnect. */
	@Value("${disable-auto-reconnect}")
	String disableAutoReconnect;
	
	/** The disable TCP. */
	@Value("${disable-tcp}")
	String disableTCP;
	
	/** The distributed system ID. */
	@Value("${distributed-system-id}")
	String distributedSystemID;
	
	/** The durable client timeout. */
	@Value("${durable-client-timeout}")
	String durableClientTimeout;
	
	/** The enable cluster configuration. */
	@Value("${enable-cluster-configuration}")
	String enableClusterConfiguration;
	
	/** The enable network partition detection. */
	@Value("${enable-network-partition-detection}")
	String enableNetworkPartitionDetection;
	
	/** The enable time statistics. */
	@Value("${enable-time-statistics}")
	String enableTimeStatistics;
	
	/** The enforce unique host. */
	@Value("${enforce-unique-host}")
	String enforceUniqueHost;
	
	/** The gateway SSL ciphers. */
	@Value("${gateway-ssl-ciphers}")
	String gatewaySSLCiphers;
	
	/** The gateway SSL enabled. */
	@Value("${gateway-ssl-enabled}")
	String gatewaySSLEnabled;
	
	/** The gateway SSL protocols. */
	@Value("${gateway-ssl-protocols}")
	String gatewaySSLProtocols;
	
	/** The gateway SSL require authentication. */
	@Value("${gateway-ssl-require-authentication}")
	String gatewaySSLRequireAuthentication;
	
	/** The http service port. */
	@Value("${http-service-port}")
	String httpServicePort;
	
	/** The jmx manager. */
	@Value("${jmx-manager}")
	String jmxManager;
	
	/** The jmx manager http port. */
	@Value("${jmx-manager-http-port}")
	String jmxManagerHttpPort;
	
	/** The jmx manager port. */
	@Value("${jmx-manager-port}")
	String jmxManagerPort;
	
	/** The jmx manager SSL. */
	@Value("${jmx-manager-ssl}")
	String jmxManagerSSL;
	
	/** The jmx manager SSL ciphers. */
	@Value("${jmx-manager-ssl-ciphers}")
	String jmxManagerSSLCiphers;
	
	/** The jmx manager SSL enabled. */
	@Value("${jmx-manager-ssl-enabled}")
	String jmxManagerSSLEnabled;
	
	/** The jmx manager SSL protocols. */
	@Value("${jmx-manager-ssl-protocols}")
	String jmxManagerSSLProtocols;
	
	/** The jmx manager SSL require authentication. */
	@Value("${jmx-manager-ssl-require-authentication}")
	String jmxManagerSSLRequireAuthentication;
	
	/** The jmx manager start. */
	@Value("${jmx-manager-start}")
	String jmxManagerStart;
	
	/** The jmx manager update rate. */
	@Value("${jmx-manager-update-rate}")
	String jmxManagerUpdateRate;
	
	/** The load cluster configuration from dir. */
	@Value("${load-cluster-configuration-from-dir}")
	String loadClusterConfigurationFromDir;
	
	/** The locators. */
	@Value("${locators}")
	String locators;
	
	/** The log disk space limit. */
	@Value("${log-disk-space-limit}")
	String logDiskSpaceLimit;
	
	/** The log file size limit. */
	@Value("${log-file-size-limit}")
	String logFileSizeLimit;
	
	/** The log level. */
	@Value("${log-level}")
	String logLevel;
	
	/** The max num reconnect tries. */
	@Value("${max-num-reconnect-tries}")
	String maxNumReconnectTries;
	
	/** The mcast flow control. */
	@Value("${mcast-flow-control}")
	String mcastFlowControl;
	
	/** The mcast recv buffer size. */
	@Value("${mcast-recv-buffer-size}")
	String mcastRecvBufferSize;
	
	/** The mcast send buffer size. */
	@Value("${mcast-send-buffer-size}")
	String mcastSendBufferSize;
	
	/** The max wait time reconnect. */
	@Value("${max-wait-time-reconnect}")
	String maxWaitTimeReconnect;
	
	
	/** The member timeout. */
	@Value("${member-timeout}")
	String memberTimeout;
	
	/** The membership port range. */
	@Value("${membership-port-range}")
	String membershipPortRange;
	
	/** The memcached port. */
	@Value("${memcached-port}")
	String memcachedPort;
	
	/** The memcached protocol. */
	@Value("${memcached-protocol}")
	String memcachedProtocol;
	
	/** The remove unresponsive client. */
	@Value("${remove-unresponsive-client}")
	String removeUnresponsiveClient;
	
	
	/** The resecurity log level. */
	@Value("${security-log-level}")
	String resecurityLogLevel;
	
	/** The security peer verify member timeout. */
	@Value("${security-peer-verifymember-timeout}")
	String securityPeerVerifyMemberTimeout;
	
	/** The server SSL ciphers. */
	@Value("${server-ssl-ciphers}")
	String serverSSLCiphers;
	
	/** The server SSL enabled. */
	@Value("${server-ssl-enabled}")
	String serverSSLEnabled;
	
	/** The server SSL protocols. */
	@Value("${server-ssl-protocols}")
	String serverSSLProtocols;
	
	/** The server SSL require authentication. */
	@Value("${server-ssl-require-authentication}")
	String serverSSLRequireAuthentication;
	
	/** The socket buffer size. */
	@Value("${socket-buffer-size}")
	String socketBufferSize;
	
	/** The socket lease time. */
	@Value("${socket-lease-time}")
	String socketLeaseTime;
	
	/** The start dev rest API. */
	@Value("${start-dev-rest-api}")
	String startDevRestAPI;
	
	/** The statistic sample rate. */
	@Value("${statistic-sample-rate}")
	String statisticSampleRate;
	
	/** The statistic sampling enabled. */
	@Value("${statistic-sampling-enabled}")
	String statisticSamplingEnabled;
	
	/** The tcp port. */
	@Value("${tcp-port}")
	String tcpPort;
	
	/** The udp fragment size. */
	@Value("${udp-fragment-size}")
	String udpFragmentSize;
	
	/** The udp recv buffer size. */
	@Value("${udp-recv-buffer-size}")
	String udpRecvBufferSize;
	
	/** The udp send buffer size. */
	@Value("${udp-send-buffer-size}")
	String udpSendBufferSize;
	
	/** The use cluster configuration. */
	@Value("${use-cluster-configuration}")
	String useClusterConfiguration;
	
	
	/** The allow persistent transactions. */
	@Value("${ALLOW_PERSISTENT_TRANSACTIONS}")
	String ALLOW_PERSISTENT_TRANSACTIONS;
	
	
	
	
	
	
	
	
	
	
}
