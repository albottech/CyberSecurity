package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new app cont perf coll enabler.
 */
@Data
public class AppContPerfCollEnabler {
	
	/** The collect auto config. */
	boolean collectAutoConfig;
	
	/** The collect component info. */
	boolean collectComponentInfo;
	
	/** The collect data source info. */
	boolean collectDataSourceInfo;
	
	/** The collect environment info. */
	boolean collectEnvironmentInfo;
	
	/** The collect generic info. */
	boolean collectGenericInfo;
	
	/** The collect health details info. */
	boolean collectHealthDetailsInfo;
	
	/** The collect log details info. */
	boolean collectLogDetailsInfo;
	
	/** The collect properties info. */
	boolean collectPropertiesInfo;
	
	/** The collect servlet info. */
	boolean collectServletInfo;
	
	/** The collect system info. */
	boolean collectSystemInfo;
	
	/** The collect thread dump info. */
	boolean collectThreadDumpInfo;

}
