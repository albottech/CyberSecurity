package io.kryptoblocks.msa.siem.stream.service.audit.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.siem.stream.service.model.SiemProcessServiceActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataProcessActivityEventReceiver.
 */
public class SiemDataProcessActivityEventReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SiemDataProcessActivityEventReceiver.class);

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/** The siem process activity event service. */
	@Autowired
	SiemDataProcessActivityEventService siemProcessActivityEventService;

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {
		eventBus.on(T(SiemProcessServiceActivity.class), receivesiemProcessActivityEvent());
	}

	/**
	 * Receivesiem process activity event.
	 *
	 * @return the consumer
	 */
	private Consumer<Event<?>> receivesiemProcessActivityEvent() {
		
		Consumer<Event<?>> returnValue = null;
		
		try {
			returnValue =  siemProcessActivityEvent -> siemProcessActivityEventService.processSiemProcessActivity((SiemProcessServiceActivity) siemProcessActivityEvent.getData());
		} catch (Exception e) {			
			LOGGER.error("exception in receive and dispatch siem process activity activityEvent method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}
}
