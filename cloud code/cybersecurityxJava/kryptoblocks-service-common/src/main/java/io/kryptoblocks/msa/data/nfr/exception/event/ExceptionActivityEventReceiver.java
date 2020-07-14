package io.kryptoblocks.msa.data.nfr.exception.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.common.model.ExceptionActivityEntity;
import io.kryptoblocks.msa.data.nfr.exception.model.ExceptionActivity;
import io.kryptoblocks.msa.data.nfr.exception.service.ExceptionActivityEventService;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.T;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivityEventReceiver.
 */
public class ExceptionActivityEventReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionActivityEventReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The exception service. */
	@Autowired
	ExceptionActivityEventService exceptionService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(ExceptionActivity.class), receiveExceptionActivityEvent());
	}

	/**
	 * Receive exception activity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receiveExceptionActivityEvent() {
		
		Consumer<Event<?>> returnValue = null;
		
		try {
			returnValue =  exceptionEvent -> exceptionService.processServiceException((ExceptionActivity) exceptionEvent.getData());
		} catch (Exception e) {			
			LOGGER.error("exception in received exception activity event method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}
}
