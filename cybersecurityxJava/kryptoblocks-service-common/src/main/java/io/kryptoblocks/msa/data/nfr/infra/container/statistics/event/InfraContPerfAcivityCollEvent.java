package io.kryptoblocks.msa.data.nfr.infra.container.statistics.event;

import org.springframework.context.ApplicationEvent;

import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfCollEnabler;

 

// TODO: Auto-generated Javadoc
/**
 * The Class InfraContPerfAcivityCollEvent.
 */
public class InfraContPerfAcivityCollEvent extends ApplicationEvent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new infra cont perf acivity coll event.
	 *
	 * @param infraNfrDataCollectionRequest the infra nfr data collection request
	 */
	public InfraContPerfAcivityCollEvent(InfraContPerfCollEnabler infraNfrDataCollectionRequest) {
		super(infraNfrDataCollectionRequest);
		// TODO Auto-generated constructor stub
	}

}
