package io.kryptoblocks.msa.common.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.exception.EventingException;
import io.kryptoblocks.msa.common.exception.MessagingException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionUtil.
 */
public class ExceptionUtil {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);
	
	/**
	 * Instantiates a new exception util.
	 */
	private ExceptionUtil() {}

	/**
	 * Gets the formatted time.
	 *
	 * @param e the e
	 * @return the formatted time
	 */
	public static String getFormattedTime(Exception e) {
		String returnTime = null;
		String inputExceptionClassName = e.getClass().getName();
		String inputExceptionMsg = e.getMessage();
		String processedExceptionClassName = null;
		try {
		if (inputExceptionClassName.equalsIgnoreCase(MessagingException.class.getName())) {
			processedExceptionClassName = MessagingException.class.getName();
			MessagingException me = (MessagingException) e;
			returnTime = me.getTime();
		}
		if (inputExceptionClassName.equalsIgnoreCase(EventingException.class.getName())) {
			processedExceptionClassName = EventingException.class.getName();
			EventingException ee = (EventingException) e;
			returnTime = ee.getTime();
		}
		if (inputExceptionClassName.equalsIgnoreCase(BusinessException.class.getName())) {
			processedExceptionClassName = MessagingException.class.getName();
			BusinessException be = (BusinessException) e;
			returnTime = be.getTime();
		}

		if (inputExceptionClassName.equalsIgnoreCase(ApiException.class.getName())) {
			processedExceptionClassName = ApiException.class.getName();
			ApiException ae = (ApiException) e;
			returnTime = ae.getTime();
		}
		}catch(Exception ee) {
			LOGGER.debug("exception in getFormattedTime method: {}", ExceptionUtils.getFullStackTrace(ee));			 
		}

		if (returnTime == null) {
			returnTime = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
			LOGGER.debug("Unable to find a date from input exception, returning a default current date as string: {}",
					returnTime);
		}
		LOGGER.debug("original input exception class name: {}", e.getClass().getName());
		LOGGER.debug("original input exception message: {}", inputExceptionMsg);
		
		LOGGER.debug("processed exception class name: {}", processedExceptionClassName);

		return returnTime;
	}

	/**
	 * Gets the formatted handler name.
	 *
	 * @param e the e
	 * @return the formatted handler name
	 */
	public static String getFormattedHandlerName(Exception e) {
		String returnHandlerName = null;
		String inputExceptionClassName = e.getClass().getName();
		String processedExceptionClassName = null;
		if (inputExceptionClassName.equalsIgnoreCase(MessagingException.class.getName())) {
			processedExceptionClassName = MessagingException.class.getName();
			MessagingException me = (MessagingException) e;
			returnHandlerName = me.getHandlerType();
		}
		if (inputExceptionClassName.equalsIgnoreCase(EventingException.class.getName())) {
			processedExceptionClassName = EventingException.class.getName();
			EventingException ee = (EventingException) e;
			returnHandlerName = ee.getHandlerType();
		}
		if (inputExceptionClassName.equalsIgnoreCase(BusinessException.class.getName())) {
			processedExceptionClassName = MessagingException.class.getName();
			BusinessException be = (BusinessException) e;
			returnHandlerName = be.getHandlerType();
		}

		if (inputExceptionClassName.equalsIgnoreCase(ApiException.class.getName())) {
			processedExceptionClassName = ApiException.class.getName();
			ApiException ae = (ApiException) e;
			returnHandlerName = ae.getHandlerType();
		}

		if (returnHandlerName == null) {
			LOGGER.debug("Unable to find a handler name from input exception, returning NULL");
		}
		LOGGER.debug("original input exception class name: {}", e.getClass().getName());
		LOGGER.debug("processed exception class name: {}", processedExceptionClassName);

		return returnHandlerName;
	}
	
	/**
	 * Generate api exception.
	 *
	 * @param message the message
	 * @param handlerType the handler type
	 * @param operationName the operation name
	 * @return the api exception
	 */
	public static ApiException generateApiException(String message, String handlerType, String operationName) {
		return new ApiException(message, handlerType, operationName);	 
		 
	}
	
	/**
	 * Generate api exception from business exception.
	 *
	 * @param be the be
	 * @param operationName the operation name
	 * @return the api exception
	 */
	public static ApiException generateApiExceptionFromBusinessException(BusinessException be, String operationName) {
		ApiException apiException = new ApiException(ExceptionUtils.getStackTrace(be), be, null, operationName);		 
		Assert.notNull(apiException.getHandlerType(), "the API Exception handler must NOT be null");
		return apiException;
	}

}
