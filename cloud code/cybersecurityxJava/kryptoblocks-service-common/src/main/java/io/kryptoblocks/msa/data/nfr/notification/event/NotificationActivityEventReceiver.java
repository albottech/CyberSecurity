package io.kryptoblocks.msa.data.nfr.notification.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.service.InfraContainerClientService;
import io.kryptoblocks.msa.data.nfr.notification.model.NotificationActivity;
import io.kryptoblocks.msa.data.nfr.notification.service.NotificationActivityEventService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationActivityEventReceiver.
 */
public class NotificationActivityEventReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationActivityEventReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The notification activity event service. */
	@Autowired
	NotificationActivityEventService notificationActivityEventService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(NotificationActivity.class), receiveNoitificationEvent());
	}

	/**
	 * Receive noitification event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receiveNoitificationEvent() {
		Consumer<Event<?>> returnValue = null;
		try {
			returnValue =  notificationEvent -> notificationActivityEventService
					.processNotificationActivityEvent((NotificationActivity) notificationEvent.getData());
		} catch (Exception e) {
			LOGGER.error("exception in receive notification activity event method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

}
