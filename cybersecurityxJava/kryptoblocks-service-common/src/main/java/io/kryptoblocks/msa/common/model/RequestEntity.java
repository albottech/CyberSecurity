package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestEntity.
 */
public class RequestEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the operation.
	 *
	 * @return the operation
	 */
	@Getter /**
  * Sets the operation.
  *
  * @param operation the new operation
  */
 @Setter(AccessLevel.PUBLIC)
	private String operation;
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	@Getter /**
  * Sets the parameters.
  *
  * @param parameters the parameters
  */
 @Setter(AccessLevel.PUBLIC)
	private Map<String, String> parameters;
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	@Getter /**
  * Sets the body.
  *
  * @param body the new body
  */
 @Setter(AccessLevel.PUBLIC)
	private String body;
	
	/**
	 * Gets the remote address.
	 *
	 * @return the remote address
	 */
	@Getter /**
  * Sets the remote address.
  *
  * @param remoteAddress the new remote address
  */
 @Setter(AccessLevel.PUBLIC)
	private String remoteAddress;
	
	 
	
	
	

}
