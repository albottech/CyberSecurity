package io.kryptoblocks.msa.common.cache.repository;



import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache async event queue config.
 */
@Data
public class CacheAsyncEventQueueConfig {
	
	/** The batch size. */
	@Value("${ASYNC.EVENT.QUEUE.BATCH.SIZE}")
	String batchSize;
	
	/** The persistent. */
	@Value("${ASYNC.EVENT.QUEUE.PERSISTENT}")
	String persistent;
	
	/** The parallel. */
	@Value("${ASYNC.EVENT.QUEUE.PARALLEL}")
	String parallel;
	
	/** The max queue memory. */
	@Value("${ASYNC.EVENT.QUEUE.MAX.QUEUE.MEMORY}")
	String maxQueueMemory;
	
	/** The dispatcher threads. */
	@Value("${ASYNC.EVENT.QUEUE.DISPATCHER.THREADS}")
	String dispatcherThreads;
	
	
	
	
	
	
}
