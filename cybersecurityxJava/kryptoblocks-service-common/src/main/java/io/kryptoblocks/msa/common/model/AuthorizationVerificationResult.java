package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizationVerificationResult.
 */
public class AuthorizationVerificationResult implements Serializable{

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
	 * Gets the authorization list.
	 *
	 * @return the authorization list
	 */
	@Getter /**
  * Sets the authorization list.
  *
  * @param authorizationList the new authorization list
  */
 @Setter(AccessLevel.PUBLIC)
	private String authorizationList;
	
	 

}
