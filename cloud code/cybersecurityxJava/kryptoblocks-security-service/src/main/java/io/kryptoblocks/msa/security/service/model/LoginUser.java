/**
 * The login user class. This class is used to represent a user name password based login object
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
 * The Class LoginUser.
 */
public class LoginUser  implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@Getter /**
  * Sets the username.
  *
  * @param username the new username
  */
 @Setter(AccessLevel.PUBLIC)
	private String username;

	 
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@Getter /**
  * Sets the password.
  *
  * @param password the new password
  */
 @Setter(AccessLevel.PUBLIC)
	private String password;
}
