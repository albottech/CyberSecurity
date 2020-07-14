package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new infra cont perf coll enabler.
 */
@Data
public class InfraContPerfCollEnabler {
	
	/** The collect change info. */
	boolean collectChangeInfo;
	
	/** The collect config info. */
	boolean collectConfigInfo;
	
	/** The collect health check info. */
	boolean collectHealthCheckInfo;
	
	/** The collect host config info. */
	boolean collectHostConfigInfo;
	
	/** The collect inspection info. */
	boolean collectInspectionInfo;
	
	/** The collect log info. */
	boolean collectLogInfo;
	
	/** The collect network config info. */
	boolean collectNetworkConfigInfo;
	
	/** The collect nwtwork settings. */
	boolean collectNwtworkSettings;
	
	/** The collect performance info. */
	boolean collectPerformanceInfo;
	
	/** The collect process info. */
	boolean collectProcessInfo;
	
	/** The collect resource usage info. */
	boolean collectResourceUsageInfo;

}
