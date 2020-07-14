package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.List;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new infra cont perf activity.
 */
@Data
public class InfraContPerfActivity {
	
	/** The collected time. */
	String collectedTime;
	
	/** The reported time. */
	String reportedTime;
	
	/** The stream type. */
	String streamType;
	
	/** The service name. */
	String serviceName;
	
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
	
	/** The collect process info. */
	boolean collectProcessInfo;
	
	/** The collect resource usage info. */
	boolean collectResourceUsageInfo;
	
	/** The change info list. */
	List<ChangeInfo> changeInfoList;
	
	/** The config info list. */
	List<ConfigInfo> configInfoList;
	
	/** The hos T config info list. */
	List<HostConfigInfo> hosTConfigInfoList;
	
	/** The inspection info list. */
	List<InspectionInfo> inspectionInfoList;
	
	/** The log info list. */
	List<LogInfo> logInfoList;
	
	/** The networking config list. */
	List<NetworkingConfigInfo> networkingConfigList;
	
	/** The network settings list. */
	List<NetworkSettingsInfo> networkSettingsList;
	
	/** The process info list. */
	List<ProcessesInfo> processInfoList;
	
	/** The resource usage info list. */
	List<ResourceUsageInfo> resourceUsageInfoList;
	
	/** The state info list. */
	List<StateInfo> stateInfoList;
	
	
	
 	

}
