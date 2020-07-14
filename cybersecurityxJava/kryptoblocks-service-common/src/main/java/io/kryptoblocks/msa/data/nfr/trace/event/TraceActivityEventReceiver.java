package io.kryptoblocks.msa.data.nfr.trace.event;

import static reactor.bus.selector.Selectors.T;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import io.kryptoblocks.msa.data.nfr.trace.service.TraceActivityEventService;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;


// TODO: Auto-generated Javadoc
/**
 * The Class TraceActivityEventReceiver.
 */
public class TraceActivityEventReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TraceActivityEventReceiver.class);
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
	
	/** The trace service. */
	@Autowired
	TraceActivityEventService traceService;
	
	/**
	 * On start up.
	 */
	@PostConstruct
    public void onStartUp() {        
		eventBus.on(T(TraceActivity.class), receiveTraceActivityEvent());
    }
	
	
	 /**
 	 * Receive trace activity event.
 	 *
 	 * @return the consumer
 	 */
 	private Consumer<Event<?>> receiveTraceActivityEvent() {
		 
		 Consumer<Event<?>> returnValue = null;
		 try {
			 returnValue =  traceEvent -> traceService.processTrace((TraceActivity) ( traceEvent.getData()));
		 }catch(Exception e) {
			 LOGGER.error("exception in receive trace activity event: {}", ExceptionUtils.getFullStackTrace(e));
			 
		 } 
		 return returnValue;
	 }


	    

}
