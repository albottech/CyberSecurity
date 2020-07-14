package io.kryptoblocks.msa.common.exception;

import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class PubnubConfigurationException.
 */
public class PubnubConfigurationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new pubnub configuration exception.
	 */
	public PubnubConfigurationException() {
		
	}
	
	/**
	 * Instantiates a new pubnub configuration exception.
	 *
	 * @param e the e
	 */
	public PubnubConfigurationException(Exception e) {
		super(ExceptionUtils.getFullStackTrace(e));		
	}

}
