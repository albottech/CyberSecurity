/**
 * The protected resource model class. This class represent a protected resource being accessed by an authenticated user <p>
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
 * The Class Resource.
 */
public class Resource implements Serializable  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	@Setter /**
  * Gets the name.
  *
  * @return the name
  */
 @Getter (AccessLevel.PUBLIC)
	String name;

}
