package io.kryptoblocks.msa.data.nfr.exception.event;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.exception.key.ExceptionActivityKey;
import io.kryptoblocks.msa.data.nfr.exception.model.ExceptionActivity;
import reactor.bus.Event;
import reactor.bus.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivityEventSender.
 */
public class ExceptionActivityEventSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionActivityEventSender.class);
	
	/** The service exception activity stream. */
	@Value("${service.exception.activity.stream}")
	String serviceExceptionActivityStream;
		
	/** The service name. */
	@Value("${spring.application.name}")
	private String serviceName;
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/**
	 * Send service exception.
	 *
	 * @param exception the exception
	 */
	public void sendServiceException(Exception exception) {

		try {
			ApiException apiException = (ApiException) exception;
			if (apiException != null) {
				ExceptionActivity exceptionEntity = new ExceptionActivity();
				ExceptionActivityKey key = new ExceptionActivityKey();
				key.setServiceName(serviceName);
				key.setId(java.util.UUID.randomUUID().toString());
				exceptionEntity.setKey(key);
				exceptionEntity.setStreamType(serviceExceptionActivityStream);
				exceptionEntity.setAlertStatus(false);
				exceptionEntity.setDetails(ExceptionUtils.getStackTrace(apiException));
				exceptionEntity.setLocation(serviceName + ":" + apiException.getOperationName());
				exceptionEntity.setProssedTimeTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
				exceptionEntity.setReportedTime(apiException.getTime());
				exceptionEntity.setDetails(ExceptionUtils.getStackTrace(apiException));
				LOGGER.debug("providing exception metric");
				eventBus.send(exceptionEntity, Event.wrap(exceptionEntity, exceptionEntity));

			} else {
				LOGGER.error(
						"Null point error in exception message sender : unable to convert input exception to API exception, services supposed to send API Exception",
						ExceptionUtils.getStackTrace(exception));
			}

		} catch (Exception e) {
			LOGGER.error("Exception in exception message sender : {}", ExceptionUtils.getStackTrace(e));
		}

	}
}
