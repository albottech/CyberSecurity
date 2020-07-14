 
package io.kryptoblocks.msa.common.cache.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventQueue;

 

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving cacheAsyncEvent events.
 * The class that is interested in processing a cacheAsyncEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCacheAsyncEventListener<code> method. When
 * the cacheAsyncEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CacheAsyncEventEvent
 */
public class CacheAsyncEventListener implements AsyncEventListener {
	
	/*
	 * https://github.com/jxblum/spring-gemfire-tests/blob/master/src/main/java/org/spring/data/gemfire/cache/asyncqueue/QueueAsyncEventListener.java
	 * https://github.com/jxblum/spring-gemfire-tests/blob/master/src/test/resources/org/spring/data/gemfire/cache/PersistentAsyncEventQueueTest-context.xml
	 */
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(CacheAsyncEventListener.class);

	/** The queue. */
	private AsyncEventQueue queue;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new cache async event listener.
	 */
	public CacheAsyncEventListener() {
		this.queue = null;
	}

	/**
	 * Instantiates a new cache async event listener.
	 *
	 * @param queue the queue
	 */
	public CacheAsyncEventListener(final AsyncEventQueue queue) {
		this.queue = queue;
	}
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		getQueue();
		LOGGER.debug("async event queue listener initialized : {}", this);
	}

	/**
	 * Gets the queue.
	 *
	 * @return the queue
	 */
	public AsyncEventQueue getQueue() {

		return queue;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets the queue.
	 *
	 * @param queue the new queue
	 */
	public void setQueue(final AsyncEventQueue queue) {
		this.queue = queue;
	}

	/**
	 * Process events.
	 *
	 * @param events the events
	 * @return true, if successful
	 */
	@Override
	public boolean processEvents(final List<AsyncEvent> events) {
		for(AsyncEvent event: events) {
		LOGGER.debug("async event queue listener event received : event operation {}", event.getOperation());
		}
		return true;
	}

	/**
	 * Close.
	 */
	@Override
	public void close() {
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return (StringUtils.hasText(getName()) ? getName() : getClass()
				.getName());
	}

}
