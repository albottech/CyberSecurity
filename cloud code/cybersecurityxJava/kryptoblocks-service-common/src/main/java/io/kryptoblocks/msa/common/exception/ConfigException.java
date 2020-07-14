package io.kryptoblocks.msa.common.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigException.
 */
public class ConfigException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new config exception.
	 *
	 * @param message the message
	 */
	public ConfigException(String message) {
		super(message);		
	} 
	
	/**
	 * Instantiates a new config exception.
	 *
	 * @param exception the exception
	 */
	public ConfigException(Exception exception) {
		super(ExceptionUtils.getFullStackTrace(exception));		
	} 

}
