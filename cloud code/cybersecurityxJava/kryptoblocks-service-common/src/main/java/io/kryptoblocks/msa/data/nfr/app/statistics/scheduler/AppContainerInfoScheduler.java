package io.kryptoblocks.msa.data.nfr.app.statistics.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

import io.kryptoblocks.msa.data.nfr.app.statistics.event.AppContPerfActivityCollectionEvent;
import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfCollEnabler;
import io.kryptoblocks.msa.data.nfr.app.statistics.service.AppContainerClientService;

import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;

import lombok.Getter;

 

// TODO: Auto-generated Javadoc
/**
 * The Class AppContainerInfoScheduler.
 */
public class AppContainerInfoScheduler implements ApplicationContextAware {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppContainerInfoScheduler.class);
	
		 
	
	/** The application event publisher. */
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	/** The app container client service. */
	@Autowired
	AppContainerClientService appContainerClientService;
	
	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	@Getter  
	ApplicationContext applicationContext;
	
	
	/**
	 * On call.
	 */
	@Scheduled(fixedDelayString = "${app.container.info.scheduler.fixed.delay}")
	public void onCall() {
		LOGGER.debug("app container info scheduler called");
		//fire the scheduled data collection event
		AppContPerfCollEnabler appNfrDataCollectionRequest = new AppContPerfCollEnabler();
		appNfrDataCollectionRequest.setCollectAutoConfig(true);
		AppContPerfActivityCollectionEvent appNfrDataCollectionEvent = new AppContPerfActivityCollectionEvent(appNfrDataCollectionRequest);
		//add additional setting
		applicationEventPublisher.publishEvent(appNfrDataCollectionEvent);
		
	}


	/**
	 * Sets the application context.
	 *
	 * @param appContext the new application context
	 * @throws BeansException the beans exception
	 */
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.applicationContext = appContext;		
	}
	
	/**
	 * Handle context refresh.
	 *
	 * @param contextRefreshedEvent the context refreshed event
	 */
	@EventListener
    public void handleContextRefresh(ContextRefreshedEvent contextRefreshedEvent) {
		 
        //collect data relevant to the context fefresh event
    }
	
	/**
	 * Handle context refresh.
	 *
	 * @param applicationContextEvent the application context event
	 */
	@EventListener
    public void handleContextRefresh(ApplicationContextEvent applicationContextEvent) {
		 
        //collect data relevant to the application context event
    }
	
	/**
	 * Handle context refresh.
	 *
	 * @param contextStartedEvent the context started event
	 */
	@EventListener
    public void handleContextRefresh(ContextStartedEvent contextStartedEvent) {
		 
        //collect data relevant to the context started event
    }
	
	/**
	 * Handle context refresh.
	 *
	 * @param contextClosedEvent the context closed event
	 */
	@EventListener
    public void handleContextRefresh(ContextClosedEvent contextClosedEvent) {
		 
        //collect data relevant to the context closed event
    }
	
	/**
	 * Handle context refresh.
	 *
	 * @param contextStoppedEvent the context stopped event
	 */
	@EventListener
    public void handleContextRefresh(ContextStoppedEvent contextStoppedEvent) {		
		 
        //collect data relevant to the context stopped event
    }
	
	/**
	 * Handle app nfr data collection event.
	 *
	 * @param appNfrDataCollectionEvent the app nfr data collection event
	 */
	@EventListener
    public void handleAppNfrDataCollectionEvent(AppContPerfActivityCollectionEvent appNfrDataCollectionEvent) {		
		 
        //collect data relevant to the app nfr data collection event
    }

}
