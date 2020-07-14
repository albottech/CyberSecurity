package io.kryptoblocks.msa.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.ExceptionUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class MessagingException.
 */
public class MessagingException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exception util. */
	@Autowired
	ExceptionUtil exceptionUtil;

	/** The service name. */
	@Value("${spring.application.name}")
	private String serviceName;

	/**
	 * Gets the operation name.
	 *
	 * @return the operation name
	 */
	@Getter
	
	/**
	 * Sets the operation name.
	 *
	 * @param operationName the new operation name
	 */
	@Setter(AccessLevel.PUBLIC)
	private String operationName;

	/**
	 * Checks if is parent.
	 *
	 * @return true, if is parent
	 */
	@Getter(AccessLevel.PUBLIC)
	private final boolean parent;

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	@Getter(AccessLevel.PUBLIC)
	private final String time;

	/**
	 * Gets the handler type.
	 *
	 * @return the handler type
	 */
	@Getter(AccessLevel.PUBLIC)
	private final String handlerType;

	/**
	 * Gets the parent exception.
	 *
	 * @return the parent exception
	 */
	@Getter(AccessLevel.PUBLIC)
	private final Exception parentException;

	/**
	 * Instantiates a new messaging exception.
	 */
	public MessagingException() {
		time = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
		parent = true;
		handlerType = ExceptionHandlerType.MESSAGING_EXCEPTION_HANDLER.getValue();
		parentException = null;
	}

	/**
	 * Instantiates a new messaging exception.
	 *
	 * @param message the message
	 * @param exception the exception
	 */
	public MessagingException(String message, Exception exception) {
		super(message, exception);
		time = exceptionUtil.getFormattedTime(exception);
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
	private String deriveHandlerFromException(Exception e) {
		String derivedHandlerType = exceptionUtil.getFormattedHandlerName(e);
		if (derivedHandlerType == null) {
			return ExceptionHandlerType.BUSINESS_EXCEPTION_HANDLER.getValue();
		} else {
			return derivedHandlerType;
		}
	}

}
