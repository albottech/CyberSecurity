package io.kryptoblocks.msa.common.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionTransformer.
 */
public class ExceptionTransformer {
	
	
	/**
	 * Derive global exception handler input.
	 *
	 * @param e the e
	 * @return the api exception
	 */
	ApiException deriveGlobalExceptionHandlerInput(Exception e) {
		return  new ApiException(null, e, null,null);	
		
		
	}

}
