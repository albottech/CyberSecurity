package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;


// TODO: Auto-generated Javadoc
/**
 * Instantiates a new app cont perf activity.
 */
@Data
public class AppContPerfActivity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The collected time. */
	String collectedTime;
	
	/** The collection type. */
	String collectionType;	
	
	/** The reported time. */
	String reportedTime;
	
	/** The processed time. */
	String processedTime;
	
	/** The stream type. */
	String streamType;
	
	/** The service name. */
	String serviceName;
	
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
	
	/** The auto config list list. */
	List<AutoConfigInfo> autoConfigListList;
	
	/** The component list list. */
	List<ComponentInfo> componentListList;
	
	/** The data source info list. */
	List<DataSourceInfo> dataSourceInfoList;
	
	/** The environment info list. */
	List<EnvironmentInfo> environmentInfoList;
	
	/** The generic info list. */
	List<GenericInfo> genericInfoList;
	
	/** The health details info list. */
	List<HealthDetailsInfo> healthDetailsInfoList;
	
	/** The log details info list. */
	List<LogDetailsInfo> logDetailsInfoList;
	
	/** The properfies info list. */
	List<PropertiesInfo> properfiesInfoList;
	
	/** The servlet info list. */
	List<ServletInfo> servletInfoList;
	
	/** The system info list. */
	List<SystemInfo> systemInfoList;
	
	/** The thread dump info list. */
	List<ThreadDumpInfo> threadDumpInfoList;

}
