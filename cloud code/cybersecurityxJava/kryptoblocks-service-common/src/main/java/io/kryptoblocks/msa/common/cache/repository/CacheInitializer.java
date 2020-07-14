
package io.kryptoblocks.msa.common.cache.repository;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gemstone.gemfire.cache.Region;
 

import lombok.Data;


// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache initializer.
 */
@Data
public class CacheInitializer implements ApplicationContextAware {

	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CacheInitializer.class);
	
	/** The context. */
	ApplicationContext context;
	
	/** The replicated region. */
	Region<String, Object> replicatedRegion;
	
	/** The partitioned region. */
	Region<String, Object> partitionedRegion;	
	 
	

	/**
	 * Inits the.
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		
		setPartitionedRegion(context.getBean("partitionedRegion",	Region.class));
		setReplicatedRegion(context.getBean("replicatedRegion",	Region.class));		
		 
	}
	
	/**
	 * Shutdown.
	 */
	@PreDestroy
	public void shutdown() {

	}

	/**
	 * Sets the application context.
	 *
	 * @param ctx the new application context
	 * @throws BeansException the beans exception
	 */
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		context = ctx;

	}

}
