package io.kryptoblocks.msa.security.service.exception;

import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityConfigurationException.
 */
public class SecurityConfigurationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new security configuration exception.
	 */
	public SecurityConfigurationException() {
		
	}
	
	/**
	 * Instantiates a new security configuration exception.
	 *
	 * @param e the e
	 */
	public SecurityConfigurationException(Exception e) {
		super(ExceptionUtils.getFullStackTrace(e));		
	}

}
