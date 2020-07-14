package io.kryptoblocks.msa.common.exception;

import java.util.Iterator;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
 
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.data.nfr.exception.event.ExceptionActivityEventSender;

 

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalExceptionHandler.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/** The exception event sender. */
	@Autowired
	ExceptionActivityEventSender exceptionEventSender;

	/**
	 * Handle exception.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<Object> handleException(ApiException ex, WebRequest request) {
		
		//return value
		ResponseEntity<Object> returnValue =  null;
		//locate handler type from the internal exception
		String handlerType = ex.getHandlerType();		
		LOGGER.debug("global exception hander type: {}", handlerType);
		//create a new return header and move value from input header to return header
		HttpHeaders updatedHeader = new HttpHeaders();			
		Iterator<String> headerNames = request.getHeaderNames();		
		while(headerNames.hasNext()) {
			String headerName = headerNames.next();				
			String  headerValue = request.getHeader(headerName);
			updatedHeader.add(headerName, headerValue);
		}
		//try to handle the exception using the not null handlers or create a default one
		try {
			
			if(handlerType != null) {
				ExceptionHandlerType exceptionHandlerType = ExceptionHandlerType.valueOf(handlerType);
				if(exceptionHandlerType != null) {		
					switch(exceptionHandlerType) {
					case DATA_ACCESS_EXCEPTION_HANDLER:
						returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.INTERNAL_SERVER_ERROR, request);
						break;
					case MESSAGING_EXCEPTION_HANDLER :
						returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.INTERNAL_SERVER_ERROR, request);
						break;
					case BUSINESS_EXCEPTION_HANDLER :
						returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.INTERNAL_SERVER_ERROR, request);
						break;
					case INPUT_EXCEPTION_HANDLER :
						returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.BAD_REQUEST, request);
						break;
					case SECURITY_EXCEPTION_HANDLER :
						returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.FORBIDDEN, request);						
						break;
					case EVENTING_EXCEPTION_HANDLER :
						returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.INTERNAL_SERVER_ERROR, request);
						break;
					default:
						break;
					}
				}
			}else {
				LOGGER.error("global exception handling error: {}", "unable to locate the service exception handler type");
				returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.INTERNAL_SERVER_ERROR, request);
				
			}
			LOGGER.debug("sending exception metric");
			exceptionEventSender.sendServiceException(ex);			
			
		} catch (Exception e) {
			String exceptionAsString = ExceptionUtils.getStackTrace(e);
			LOGGER.error("global exception handling error: {}", exceptionAsString);
			if(returnValue == null) {
				returnValue = handleExceptionInternal(ex, ex.getUserMsg(), updatedHeader, HttpStatus.INTERNAL_SERVER_ERROR, request);
			}
		}
		return returnValue;
	}
}