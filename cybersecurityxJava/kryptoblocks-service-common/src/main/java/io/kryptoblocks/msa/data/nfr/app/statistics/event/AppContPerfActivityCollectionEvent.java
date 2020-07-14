package io.kryptoblocks.msa.data.nfr.app.statistics.event;

import org.springframework.context.ApplicationEvent;

import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfCollEnabler;

 

// TODO: Auto-generated Javadoc
/**
 * The Class AppContPerfActivityCollectionEvent.
 */
public class AppContPerfActivityCollectionEvent extends ApplicationEvent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new app cont perf activity collection event.
	 *
	 * @param appNfrDataCollectionRequest the app nfr data collection request
	 */
	public AppContPerfActivityCollectionEvent(AppContPerfCollEnabler appNfrDataCollectionRequest) {
		super(appNfrDataCollectionRequest);
		// TODO Auto-generated constructor stub
	}

}
