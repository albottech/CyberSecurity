package io.kryptoblocks.msa.common.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class LocationException.
 */
public class LocationException extends Exception {
	
	/**
	 * Instantiates a new location exception.
	 *
	 * @param message the message
	 */
	public LocationException(String message) {
		super(message);		
	}
	
	/**
	 * Instantiates a new location exception.
	 *
	 * @param exception the exception
	 */
	public LocationException(Exception exception) {
		super(ExceptionUtils.getFullStackTrace(exception));		
	}

}
