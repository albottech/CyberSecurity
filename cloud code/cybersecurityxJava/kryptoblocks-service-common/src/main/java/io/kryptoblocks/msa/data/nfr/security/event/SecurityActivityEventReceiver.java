package io.kryptoblocks.msa.data.nfr.security.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.service.InfraContainerClientService;
import io.kryptoblocks.msa.data.nfr.security.model.SecurityActivity;
import io.kryptoblocks.msa.data.nfr.security.service.SecurityActivityEventService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityActivityEventReceiver.
 */
public class SecurityActivityEventReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityActivityEventReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The security activity event service. */
	@Autowired
	SecurityActivityEventService securityActivityEventService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(SecurityActivity.class), receiveSecurityActivityEvent());
	}

	/**
	 * Receive security activity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receiveSecurityActivityEvent() {
		Consumer<Event<?>> returnValue = null;
		try {
			returnValue =  securityEntityEvent -> securityActivityEventService
					.processSecurityEvent((SecurityActivity) securityEntityEvent.getData());
		} catch (Exception e) {
			LOGGER.error("exception in receive security activity event method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

}
