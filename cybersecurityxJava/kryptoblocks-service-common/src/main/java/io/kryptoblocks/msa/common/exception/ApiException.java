package io.kryptoblocks.msa.common.exception;

 
import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.ExceptionUtil;
import lombok.AccessLevel;
import lombok.Getter;


// TODO: Auto-generated Javadoc
/**
 * The Class ApiException.
 */
public class ApiException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
		
	/**
	 * Gets the operation name.
	 *
	 * @return the operation name
	 */
	@Getter (AccessLevel.PUBLIC)
    private final String operationName;
	
	/**
	 * Checks if is parent.
	 *
	 * @return true, if is parent
	 */
	@Getter (AccessLevel.PUBLIC)
    private final boolean parent;
	
	/**
	 * Gets the user msg.
	 *
	 * @return the user msg
	 */
	@Getter (AccessLevel.PUBLIC)
    private final String userMsg;
	
	/**
	 * Gets the handler type.
	 *
	 * @return the handler type
	 */
	@Getter (AccessLevel.PUBLIC)
    private final String handlerType;
	
	/**
	 * Gets the parent exception.
	 *
	 * @return the parent exception
	 */
	@Getter (AccessLevel.PUBLIC)
    private final Exception parentException;
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	@Getter (AccessLevel.PUBLIC)
    private final String time;
	
	/**
	 * Instantiates a new api exception.
	 *
	 * @param inuserMsg the inuser msg
	 * @param inHandlerType the in handler type
	 * @param inOperationName the in operation name
	 */
	public ApiException(String inuserMsg, String inHandlerType, String inOperationName) {
		userMsg = inuserMsg;
		handlerType = inHandlerType;
		operationName = inOperationName;
		time = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
		parent = true;		
		parentException = null;		 
	}
	
	/**
	 * Instantiates a new api exception.
	 *
	 * @param message the message
	 * @param inuserMsg the inuser msg
	 * @param inHandlerType the in handler type
	 * @param inOperationName the in operation name
	 */
	public ApiException(String message, String inuserMsg, String inHandlerType, String inOperationName) {
		super(message);
		userMsg = inuserMsg;
		handlerType = inHandlerType;
		operationName = inOperationName;
		time = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
		parent = true;		
		parentException = null;		 
	}

	/**
	 * Instantiates a new api exception.
	 *
	 * @param message the message
	 * @param exception the exception
	 * @param inuserMsg the inuser msg
	 * @param inOperationName the in operation name
	 */
	public ApiException(String message, Exception exception, String inuserMsg, String inOperationName) {				
		super(message, exception);
		userMsg = inuserMsg;		
		operationName = inOperationName;
		time = ExceptionUtil.getFormattedTime(exception);
		handlerType = deriveHandlerFromException(exception);		
		parent = false;
		parentException = exception;
		
	}
	
	
	/**
	 * Derive handler from exception.
	 *
	 * @param e the e
	 * @return the string
	 */
	private static String deriveHandlerFromException(Exception e) {
		String derivedHandlerType = ExceptionUtil.getFormattedHandlerName(e);
		if (derivedHandlerType == null) {
			return ExceptionHandlerType.BUSINESS_EXCEPTION_HANDLER.getValue();
		} else {
			return derivedHandlerType;
		}
	}
	

}
