 
package io.kryptoblocks.msa.common.cache.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

import io.kryptoblocks.msa.common.cache.pdx.PdxObject;

 

 
// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving cacheSyncEvent events.
 * The class that is interested in processing a cacheSyncEvent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCacheSyncEventListener<code> method. When
 * the cacheSyncEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CacheSyncEventEvent
 */
public class CacheSyncEventListener extends CacheListenerAdapter<String, PdxObject> {
	
	/** The log. */
	private static Log log = LogFactory.getLog(CacheSyncEventListener.class);

	 
	/**
	 * After create.
	 *
	 * @param event the event
	 */
	@Override
	  public void afterCreate(EntryEvent<String, PdxObject> event) {
	    final String regionName = event.getRegion().getName();
	    final Object key = event.getKey();
	    final Object newValue = event.getNewValue();
	    log.info("In region [" + regionName + "] created key [" + key
	        + "] value [" + newValue + "]");
	  }

	  /**
  	 * After destroy.
  	 *
  	 * @param event the event
  	 */
  	@Override
	  public void afterDestroy(EntryEvent<String, PdxObject> event) {
	    final String regionName = event.getRegion().getName();
	    final Object key = event.getKey();
	    log.info("In region [" + regionName + "] destroyed key [" + key
	        + "]");
	  }

	  /**
  	 * After update.
  	 *
  	 * @param event the event
  	 */
  	@Override
	  public void afterUpdate(EntryEvent<String, PdxObject> event) {
	    final String regionName = event.getRegion().getName();
	    final Object key = event.getKey();
	    final Object newValue = event.getNewValue();
	    final Object oldValue = event.getOldValue();
	    log.info("In region [" + regionName + "] updated key [" + key
	        + "] [oldValue [" + oldValue + "] new value [" + newValue +"]");
	  }
}
