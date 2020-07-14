package io.kryptoblocks.msa.common.controller;

 
import org.springframework.beans.factory.annotation.Value;

 
// TODO: Auto-generated Javadoc
/**
 * The Class ControllerBase.
 */
abstract public class ControllerBase {
	
	 
	
	/** The service name. */
	@Value("${spring.application.name}")
	private String serviceName;

	/** The input. */
	protected Object input = null;

	/** The user. */
	protected String user = null;
	
	/** The method name. */
	protected String methodName = null;

}
