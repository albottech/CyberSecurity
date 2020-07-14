package io.kryptoblocks.msa.data.nfr.infra.container.statistics.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.service.InfraContainerClientService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraContPerfAcivityEvntReceiver.
 */
public class InfraContPerfAcivityEvntReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InfraContPerfAcivityEvntReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The infra container client service. */
	@Autowired
	InfraContainerClientService infraContainerClientService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(InfraContPerfActivity.class), receiveInfraContPerfEntityEvent());
	}

	/**
	 * Receive infra cont perf entity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receiveInfraContPerfEntityEvent() {
		Consumer<Event<?>> returnValue = null;
		try {
			returnValue =  infraContPerfEntityEvent -> infraContainerClientService
					.processInfraPerfActivityEvent((InfraContPerfActivity) infraContPerfEntityEvent.getData());
		} catch (Exception e) {
			LOGGER.error("exception in receive infra container perf activity event method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

}
