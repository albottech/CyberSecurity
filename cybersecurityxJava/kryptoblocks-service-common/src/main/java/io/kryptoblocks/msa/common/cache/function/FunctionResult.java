package io.kryptoblocks.msa.common.cache.function;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class FunctionResult.
 */
public class FunctionResult implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	@Getter /**
  * Sets the status.
  *
  * @param status the new status
  */
 @Setter (AccessLevel.PUBLIC)
	private String status;
	
	/**
	 * Checks if is boolean result.
	 *
	 * @return true, if is boolean result
	 */
	@Getter /**
  * Sets the boolean result.
  *
  * @param booleanResult the new boolean result
  */
 @Setter (AccessLevel.PUBLIC)
	private boolean booleanResult;
	
	/**
	 * Gets the list result.
	 *
	 * @return the list result
	 */
	@Getter /**
  * Sets the list result.
  *
  * @param listResult the new list result
  */
 @Setter (AccessLevel.PUBLIC)
	private List<Object> listResult;
	
	/**
	 * Gets the result type.
	 *
	 * @return the result type
	 */
	@Getter /**
  * Sets the result type.
  *
  * @param resultType the new result type
  */
 @Setter (AccessLevel.PUBLIC)
	private String resultType;
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	@Getter /**
  * Sets the time.
  *
  * @param time the new time
  */
 @Setter (AccessLevel.PUBLIC)
	private String time;
	
	/**
	 * Checks if is gets the for update.
	 *
	 * @return true, if is gets the for update
	 */
	@Getter /**
  * Sets the gets the for update.
  *
  * @param getForUpdate the new gets the for update
  */
 @Setter (AccessLevel.PUBLIC)
	private boolean getForUpdate;
	
}
