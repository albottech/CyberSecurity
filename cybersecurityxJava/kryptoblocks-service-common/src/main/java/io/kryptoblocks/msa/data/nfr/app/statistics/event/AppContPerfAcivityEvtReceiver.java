package io.kryptoblocks.msa.data.nfr.app.statistics.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfActivity;
import io.kryptoblocks.msa.data.nfr.app.statistics.service.AppContainerClientService;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.service.InfraContainerClientService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class AppContPerfAcivityEvtReceiver.
 */
public class AppContPerfAcivityEvtReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContPerfAcivityEvtReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The app container client service. */
	@Autowired
	AppContainerClientService appContainerClientService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(AppContPerfActivity.class), receiveAppContPerfEntityEvent());
	}

	/**
	 * Receive app cont perf entity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receiveAppContPerfEntityEvent() {
		Consumer<Event<?>> returnValue = null;
		try {
			returnValue =  appContPerfEntityEvent -> appContainerClientService
					.processContPerfActivity((AppContPerfActivity) appContPerfEntityEvent.getData());
		} catch (Exception e) {
			LOGGER.error("exception in receive app container perf activity event method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

}
