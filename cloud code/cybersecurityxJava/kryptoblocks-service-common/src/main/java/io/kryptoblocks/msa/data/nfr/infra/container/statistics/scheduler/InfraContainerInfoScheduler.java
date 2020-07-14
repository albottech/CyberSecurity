package io.kryptoblocks.msa.data.nfr.infra.container.statistics.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

import io.kryptoblocks.msa.data.nfr.infra.container.statistics.event.InfraContPerfAcivityCollEvent;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfCollEnabler;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.service.InfraContainerClientService;
import lombok.Getter;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraContainerInfoScheduler.
 */
public class InfraContainerInfoScheduler implements ApplicationContextAware {
	
/** The Constant LOGGER. */
private static final Logger LOGGER = LoggerFactory.getLogger(InfraContainerInfoScheduler.class);
	
		 
	
	/** The application event publisher. */
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	/** The infra container client service. */
	@Autowired
	InfraContainerClientService infraContainerClientService;
	
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
	@Scheduled(fixedDelayString = "${infra.container.info.scheduler.fixed.delay}")
	public void onCall() {
		LOGGER.debug("infra container info scheduler called");
		//fire the scheduled data collection event
		InfraContPerfCollEnabler infraNfrDataCollectionRequest = new InfraContPerfCollEnabler();
		infraNfrDataCollectionRequest.setCollectChangeInfo(true);
		InfraContPerfAcivityCollEvent infraNfrDataCollectionEvent = new InfraContPerfAcivityCollEvent(infraNfrDataCollectionRequest);
		//add additional setting
		applicationEventPublisher.publishEvent(infraNfrDataCollectionEvent);
		
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
	 * Handle app nfr data collection event.
	 *
	 * @param infraNfrDataCollectionEvent the infra nfr data collection event
	 */
	@EventListener
    public void handleAppNfrDataCollectionEvent(InfraContPerfAcivityCollEvent infraNfrDataCollectionEvent) {		
		 
        //collect data relevant to the ifra nfr data collection event
    }

}
