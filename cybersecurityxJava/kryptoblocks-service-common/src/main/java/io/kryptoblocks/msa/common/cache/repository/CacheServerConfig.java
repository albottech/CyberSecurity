package io.kryptoblocks.msa.common.cache.repository;

 

import org.springframework.beans.factory.annotation.Value;

 
import lombok.Data;
 

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache server config.
 */
@Data
public class CacheServerConfig {
	
	/** The auto startup. */
	@Value("${SERVER.AUTO.STARTUP}")
	String autoStartup;
	
	/** The bind addddress. */
	@Value("${SERVER.BIND.ADDRESS}")
	String bindAddddress;
	
	/** The host for clients. */
	@Value("${SERVER.HOST.FOR.CLIENTS}")
	String hostForClients;
	
	/** The poll interval. */
	@Value("${SERVER.LOCK.POLL.INTERVAL}")
	String pollInterval;
	
	/** The max connections. */
	@Value("${SERVER.MAX.CONNECTIONS}")
	String maxConnections;
	
	/** The max threads. */
	@Value("${SERVER.MAX.THREADS}")
	String maxThreads;
	
	/** The max message count. */
	@Value("${SERVER.MAX.MESSAGE.COUNT}")
	String maxMessageCount;
	
	/** The max time between pings. */
	@Value("${SERVER.MAX.TIME.BETWEEN.PINGS}")
	String maxTimeBetweenPings;
	
	/** The groups. */
	@Value("${SERVER.GROUPS}")
	String groups;
	
	/** The port range min value. */
	@Value("${SERVER.PORT.MIN.VALUE}")
	String portRangeMinValue;
	
	/** The port range max value. */
	@Value("${SERVER.PORT.MAX.VALUE}")
	String portRangeMaxValue;
	
	
	
	
	
		
	
}
