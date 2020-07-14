/**
 * The user authorized resource operation model class. This class represent the active and allowed operation for an authenticated user for a given resource
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class Operation.
 */
public class Operation implements Serializable  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Sets the read.
	 *
	 * @param read the new read
	 */
	@Setter /**
  * Checks if is read.
  *
  * @return true, if is read
  */
 @Getter (AccessLevel.PUBLIC)
	private boolean read;
	
	/**
	 * Sets the write.
	 *
	 * @param write the new write
	 */
	@Setter 
 /**
  * Checks if is write.
  *
  * @return true, if is write
  */
 @Getter (AccessLevel.PUBLIC)
	private boolean write;
	
	/**
	 * Sets the update.
	 *
	 * @param update the new update
	 */
	@Setter 
 /**
  * Checks if is update.
  *
  * @return true, if is update
  */
 @Getter (AccessLevel.PUBLIC)
	private boolean update;
	
	/**
	 * Sets the delete.
	 *
	 * @param delete the new delete
	 */
	@Setter 
 /**
  * Checks if is delete.
  *
  * @return true, if is delete
  */
 @Getter (AccessLevel.PUBLIC)
	private boolean delete;

}
