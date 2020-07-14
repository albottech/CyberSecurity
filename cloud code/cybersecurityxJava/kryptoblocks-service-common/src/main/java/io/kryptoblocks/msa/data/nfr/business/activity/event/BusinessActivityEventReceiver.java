package io.kryptoblocks.msa.data.nfr.business.activity.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.business.activity.service.BusinessActivityEventService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityEventReceiver.
 */
public class BusinessActivityEventReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivityEventReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The business activity service. */
	@Autowired
	BusinessActivityEventService businessActivityService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(BusinessActivity.class), receiveBusinessActivityEvent());
	}

	/**
	 * Receive business activity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receiveBusinessActivityEvent() {
		Consumer<Event<?>> returnValue = null;
		try {
			returnValue =  businessActivityEvent -> businessActivityService
					.processBusinessActivity((BusinessActivity) businessActivityEvent.getData());
		} catch (Exception e) {
			LOGGER.error("exception in receive business activity event method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

}
