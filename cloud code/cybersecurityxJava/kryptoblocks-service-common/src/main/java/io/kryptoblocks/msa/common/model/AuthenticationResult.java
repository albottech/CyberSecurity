package io.kryptoblocks.msa.common.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationResult.
 */
public class AuthenticationResult implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	@Getter /**
  * Sets the msg.
  *
  * @param msg the new msg
  */
 @Setter(AccessLevel.PUBLIC)
	private String msg;
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	@Getter /**
  * Sets the user name.
  *
  * @param userName the new user name
  */
 @Setter(AccessLevel.PUBLIC)
	private String userName;
	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	@Getter /**
  * Sets the token.
  *
  * @param token the new token
  */
 @Setter(AccessLevel.PUBLIC)
	private String token;

}
