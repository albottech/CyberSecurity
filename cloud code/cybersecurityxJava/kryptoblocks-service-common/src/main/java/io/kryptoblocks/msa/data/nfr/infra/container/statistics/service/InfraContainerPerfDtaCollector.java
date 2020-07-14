package io.kryptoblocks.msa.data.nfr.infra.container.statistics.service;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DefaultDockerClient.Builder;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.AttachedNetwork;
import com.spotify.docker.client.messages.BlockIoStats;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerChange;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerMount;
import com.spotify.docker.client.messages.ContainerState;
import com.spotify.docker.client.messages.ContainerStats;
import com.spotify.docker.client.messages.CpuStats;
import com.spotify.docker.client.messages.Device;
import com.spotify.docker.client.messages.EndpointConfig;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.Info;
import com.spotify.docker.client.messages.LogConfig;
import com.spotify.docker.client.messages.MemoryStats;
import com.spotify.docker.client.messages.NetworkSettings;
import com.spotify.docker.client.messages.NetworkStats;
import com.spotify.docker.client.messages.PortBinding;
import com.spotify.docker.client.messages.TopResults;
import com.spotify.docker.client.messages.ContainerConfig.Healthcheck;
import com.spotify.docker.client.messages.ContainerConfig.NetworkingConfig;
import com.spotify.docker.client.messages.ContainerInfo.Node;
import com.spotify.docker.client.messages.ContainerState.HealthLog;
import com.spotify.docker.client.messages.HostConfig.BlkioDeviceRate;
import com.spotify.docker.client.messages.HostConfig.LxcConfParameter;
import com.spotify.docker.client.messages.HostConfig.Ulimit;
import com.spotify.docker.client.messages.mount.Mount;
import com.spotify.docker.client.messages.mount.VolumeOptions;

import io.kryptoblocks.msa.common.exception.ConfigException;
import io.kryptoblocks.msa.common.names.NameCollection;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ChangeInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.HealthCheckInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.HealthInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.HostConfigInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.InspectionInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.LogInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.NetworkSettingsInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.NetworkingConfigInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ResourceUsageInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.StateInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.ChangeInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.ConfigInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.HealthInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.HealthcheckInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.HostConfigInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InspectionInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.LogInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.NetworkSettingsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.NetworkingConfigInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.ProcessesInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.ResourceUsageInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.StateInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.AttachedNetworkInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.BlkIoDeviceRateInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.BlockIoStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.ContainerMountInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.CpuStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.DeviceInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.HealthLogInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.LogConfigInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.LxcConfParameterInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.MemoryStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.MountInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.NetworkEndpointConfigInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.NetworkStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.NodeInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.RestartPolicyInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.UlimitInfo;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraContainerPerfDtaCollector.
 */
public class InfraContainerPerfDtaCollector {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InfraContainerClientService.class);
	
	/** The Constant DOCKER_UNIX_DEMON. */
	private static final String DOCKER_UNIX_DEMON = "unix:///var/run/docker.sock";

	/**
	 * Gets the docker client.
	 *
	 * @return the docker client
	 */
	@Getter
	
	/**
	 * Sets the docker client.
	 *
	 * @param dockerClient the new docker client
	 */
	@Setter
	DockerClient dockerClient;

	/** The config enabled. */
	@Value("${docker.client.config.enabled}")
	private boolean configEnabled;

	/** The connection URI. */
	@Value("${docker.client.server.connection.uri}")
	private String connectionURI;

	/** The connection pool size. */
	@Value("${docker.client.connection.pool.size}")
	private int connectionPoolSize;

	/** The connection timeout in millis. */
	@Value("${docker.client.connection.time.out.in.millis}")
	private int connectionTimeoutInMillis;

	/** The read timeout in millis. */
	@Value("${docker.client.read.time.out.in.millis}")
	private int readTimeoutInMillis;

	/** The docker certificates name. */
	@Value("${docker.client.certificates.name}")
	private String dockerCertificatesName;

	/** The host name. */
	@Value("${spring.cloud.client.hostname}")
	private String hostName;

	/** The app name. */
	@Value("${spring.application.name}")
	private String appName;

	/** The port. */
	@Value("${server.port}")
	int port;

	/** The loader. */
	@Autowired
	ResourceLoader loader;

	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService;

	/** The json util. */
	@Autowired
	JsonUtil jsonUtil;

	/**
	 * Gets the container list base.
	 *
	 * @return the container list base
	 */
	@Getter
	
	/**
	 * Sets the container list base.
	 *
	 * @param containerListBase the new container list base
	 */
	@Setter
	List<Container> containerListBase;

	/**
	 * Gets the config info base.
	 *
	 * @return the config info base
	 */
	@Getter
	
	/**
	 * Sets the config info base.
	 *
	 * @param configInfoBase the new config info base
	 */
	@Setter
	ConfigInfo configInfoBase;

	/**
	 * Post construct.
	 *
	 * @throws ConfigException the config exception
	 */
	@PostConstruct
	public void postConstruct() throws ConfigException {
		if (configEnabled) {
			Builder dockerBuilder = DefaultDockerClient.builder();
			dockerBuilder.uri(URI.create(connectionURI));
			dockerBuilder.connectionPoolSize(connectionPoolSize);
			dockerBuilder.connectTimeoutMillis(connectionTimeoutInMillis);
			dockerBuilder.readTimeoutMillis(readTimeoutInMillis);
			dockerBuilder.dockerCertificates(loadCertificate());
			dockerBuilder.readTimeoutMillis(readTimeoutInMillis);
			setDockerClient(dockerBuilder.build());
		} else {
			DockerClient dClient = new DefaultDockerClient(DOCKER_UNIX_DEMON);
			setDockerClient(dClient);
		}
	}

	

	/**
	 * Gets the container list.
	 *
	 * @return the container list
	 */
	public List<Container> getContainerList() {
		List<Container> returnValue = null;
		try {
			returnValue = dockerClient.listContainers();
		} catch (Exception e) {
			LOGGER.error("exception in docker get container list operation at host: {}", DOCKER_UNIX_DEMON);
			LOGGER.error("exception details: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

	/**
	 * Gets the container info.
	 *
	 * @param containedID the contained ID
	 * @return the container info
	 */
	public ContainerInfo getContainerInfo(String containedID) {
		ContainerInfo returnValue = null;
		try {
			returnValue = dockerClient.inspectContainer(containedID);
		} catch (Exception e) {
			LOGGER.error("exception in docker get container info for container id: {}", containedID);
			LOGGER.error("exception details: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

	/**
	 * Gets the container process detaiils.
	 *
	 * @param containedID the contained ID
	 * @return the container process detaiils
	 */
	public TopResults getContainerProcessDetaiils(String containedID) {
		TopResults returnValue = null;
		try {
			returnValue = dockerClient.topContainer(containedID);
		} catch (Exception e) {
			LOGGER.error("exception in docker get container process details for container id: {}", containedID);
			LOGGER.error("exception details: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

	/**
	 * Gets the container stats.
	 *
	 * @param containedID the contained ID
	 * @return the container stats
	 */
	public ContainerStats getContainerStats(String containedID) {
		ContainerStats returnValue = null;
		try {
			returnValue = dockerClient.stats(containedID);
		} catch (Exception e) {
			LOGGER.error("exception in docker get container stats for container id: {}", containedID);
			LOGGER.error("exception details: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

	/**
	 * Gets the docker info.
	 *
	 * @return the docker info
	 */
	public Info getDockerInfo() {
		Info returnValue = null;
		try {
			returnValue = dockerClient.info();
		} catch (DockerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * Gets the change info list.
	 *
	 * @param containerId the container id
	 * @return the change info list
	 */
	public List<ChangeInfo> getChangeInfoList(String containerId) {

		List<ChangeInfo> returnValue = new ArrayList<ChangeInfo>();
		try {
			List<ContainerChange> containerChange = dockerClient.inspectContainerChanges(containerId);
			for (ContainerChange change : containerChange) {
				returnValue.add(getChangeInfo(change, containerId));
			}
		} catch (DockerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnValue;
	}

	/**
	 * Gets the config info.
	 *
	 * @param cConfig the c config
	 * @param healthCheckId the health check id
	 * @param hostConfigKey the host config key
	 * @param networkConfigId the network config id
	 * @return the config info
	 */
	@SuppressWarnings("unchecked")
	public ConfigInfo getConfigInfo(ContainerConfig cConfig, String healthCheckId, String hostConfigKey,
			String networkConfigId) {
		/*
		 * ContainerInfo - ContainerConfig
		 */
		ConfigInfo configInfo = new ConfigInfo();
		configInfo.setInstanceId(getInstanceId());
		configInfo.setAttachStderr(cConfig.attachStderr());
		configInfo.setAttachStdin(cConfig.attachStdin());
		configInfo.setAttachStdout(cConfig.attachStdout());
		configInfo.setCmd(cConfig.cmd());
		configInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		configInfo.setDomainname(cConfig.domainname());
		configInfo.setEntrypoint(cConfig.entrypoint());
		configInfo.setEnv(cConfig.env());
		configInfo.setExposedPorts((List<String>) cConfig.exposedPorts());
		configInfo.setHealthcheckId(healthCheckId);
		configInfo.setHostConfigKey(hostConfigKey);
		configInfo.setHostname(cConfig.hostname());
		configInfo.setImage(cConfig.image());
		configInfo.setLabels(cConfig.labels());
		configInfo.setMacAddress(cConfig.macAddress());
		configInfo.setNetworkDisabled(cConfig.networkDisabled());
		configInfo.setNetworkingConfigId(networkConfigId);
		configInfo.setOnBuild(cConfig.onBuild());
		configInfo.setOpenStdin(cConfig.openStdin());
		configInfo.setPortSpecs(cConfig.portSpecs());
		configInfo.setStdinOnce(cConfig.stdinOnce());
		configInfo.setStopSignal(cConfig.stopSignal());
		configInfo.setTty(cConfig.tty());
		configInfo.setUser(cConfig.user());
		configInfo.setVolumeNames((List<String>) cConfig.volumeNames());
		Map<String, Map> volumes = cConfig.volumes();
		Map<String, String> updatedVolumes = new HashMap<String, String>();
		for (Map.Entry<String, Map> volumesEntry : volumes.entrySet()) {
			String key = volumesEntry.getKey();
			Map value = volumesEntry.getValue();
			String valueasString = jsonUtil.getObjectAsJson(value);
			updatedVolumes.put(key, valueasString);
		}
		configInfo.setVolumes(updatedVolumes);
		configInfo.setWorkingDir(cConfig.workingDir());
		return configInfo;
	}

	/**
	 * Gets the healthcheck info.
	 *
	 * @param hCheck the h check
	 * @param containerID the container ID
	 * @return the healthcheck info
	 */
	HealthcheckInfo getHealthcheckInfo(Healthcheck hCheck, String containerID) {
		/*
		 * ContainerConfig - HealthCheck
		 */
		HealthcheckInfo healthcheckInfo = new HealthcheckInfo();
		healthcheckInfo.setInstanceId(getInstanceId());
		HealthCheckInfoKey healthCheckInfoKey = new HealthCheckInfoKey();
		healthCheckInfoKey.setId(containerID);
		healthCheckInfoKey.setName(getName());
		healthcheckInfo.setKey(healthCheckInfoKey);
		healthcheckInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		healthcheckInfo.setInterval(hCheck.interval());
		healthcheckInfo.setRetries(hCheck.retries());
		healthcheckInfo.setStartPerion(hCheck.startPeriod());
		healthcheckInfo.setTest(hCheck.test());
		healthcheckInfo.setTimeout(hCheck.timeout());
		return healthcheckInfo;
	}

	/**
	 * Gets the health info.
	 *
	 * @param cHealth the c health
	 * @param containerId the container id
	 * @return the health info
	 */
	public HealthInfo getHealthInfo(ContainerState.Health cHealth, String containerId) {
		HealthInfo healthInfo = new HealthInfo();
		healthInfo.setInstanceId(getInstanceId());
		HealthInfoKey healthInfoKey = new HealthInfoKey();
		healthInfoKey.setId(containerId);
		healthInfoKey.setName(getName());
		healthInfo.setKey(healthInfoKey);
		healthInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		healthInfo.setFailingStreak(cHealth.failingStreak());
		List<HealthLog> healthLogList = cHealth.log();
		List<HealthLogInfo> updatedHealthLogList = new ArrayList<HealthLogInfo>();
		for (HealthLog healthLog : healthLogList) {
			updatedHealthLogList.add(getHealthLogInfo(healthLog));
		}
		healthInfo.setLog(updatedHealthLogList);
		healthInfo.setStatus(cHealth.status());
		return healthInfo;
	}

	/**
	 * Gets the host config info.
	 *
	 * @param hostConfig the host config
	 * @param containerID the container ID
	 * @return the host config info
	 */
	public HostConfigInfo getHostConfigInfo(HostConfig hostConfig, String containerID) {

		/*
		 * ContainerInfo - HostConfig
		 */
		HostConfigInfo hostConfigInfo = new HostConfigInfo();
		hostConfigInfo.setInstanceId(getInstanceId());
		hostConfigInfo.setAutoRemove(hostConfig.autoRemove());
		hostConfigInfo.setBinds(hostConfig.binds());

		List<BlkioDeviceRate> bulkIoDeviceRateList = hostConfig.blkioDeviceReadBps();
		List<BlkIoDeviceRateInfo> updatedBulkIoDeviceRateList = new ArrayList<BlkIoDeviceRateInfo>();
		for (BlkioDeviceRate rate : bulkIoDeviceRateList) {
			BlkIoDeviceRateInfo bulkInDeviceRateInf = getBulkInDeviceRateInfo(rate);
			updatedBulkIoDeviceRateList.add(bulkInDeviceRateInf);
		}
		hostConfigInfo.setBlkIoDeviceReadBps(updatedBulkIoDeviceRateList);

		List<BlkioDeviceRate> blkioDeviceReadIOpsList = hostConfig.blkioDeviceReadIOps();
		List<BlkIoDeviceRateInfo> updatedblkioDeviceReadIOpsList = new ArrayList<BlkIoDeviceRateInfo>();
		for (BlkioDeviceRate rateIOp : blkioDeviceReadIOpsList) {
			BlkIoDeviceRateInfo bulkInDeviceRateInf = getBulkInDeviceRateInfo(rateIOp);
			updatedblkioDeviceReadIOpsList.add(bulkInDeviceRateInf);
		}
		hostConfigInfo.setBlkIoDeviceReadIOps(updatedblkioDeviceReadIOpsList);

		List<BlkioDeviceRate> blkIoDeviceWriteBpsList = hostConfig.blkioDeviceWriteBps();
		List<BlkIoDeviceRateInfo> updatedBlkIoDeviceWriteBpsList = new ArrayList<BlkIoDeviceRateInfo>();
		for (BlkioDeviceRate rateWriteBps : blkIoDeviceWriteBpsList) {
			BlkIoDeviceRateInfo bulkInDeviceRateInf = getBulkInDeviceRateInfo(rateWriteBps);
			updatedBlkIoDeviceWriteBpsList.add(bulkInDeviceRateInf);
		}
		hostConfigInfo.setBlkIoDeviceWriteBps(updatedBlkIoDeviceWriteBpsList);

		List<BlkioDeviceRate> blkioDeviceWriteIOpsList = hostConfig.blkioDeviceWriteIOps();
		List<BlkIoDeviceRateInfo> updatedBlkioDeviceWriteIOpsList = new ArrayList<BlkIoDeviceRateInfo>();
		for (BlkioDeviceRate writeIOps : blkioDeviceWriteIOpsList) {
			BlkIoDeviceRateInfo bulkInDeviceRateInf = getBulkInDeviceRateInfo(writeIOps);
			updatedBlkioDeviceWriteIOpsList.add(bulkInDeviceRateInf);
		}
		hostConfigInfo.setBlkioDeviceWriteIOps(updatedBlkioDeviceWriteIOpsList);

		hostConfigInfo.setBlkioWeight(hostConfig.blkioWeight());
		hostConfigInfo.setCapAdd(hostConfig.capAdd());
		hostConfigInfo.setCapDrop(hostConfig.capDrop());
		hostConfigInfo.setCgroupParent(hostConfig.cgroupParent());
		hostConfigInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		hostConfigInfo.setContainerIdFile(hostConfig.containerIdFile());
		hostConfigInfo.setCpuPeriod(hostConfig.cpuPeriod());
		hostConfigInfo.setCpuQuota(hostConfig.cpuQuota());
		hostConfigInfo.setCpusetCpus(hostConfig.cpusetCpus());
		hostConfigInfo.setCpusetMems(hostConfig.cpusetMems());
		hostConfigInfo.setCpuShares(hostConfig.cpuShares());
		List<Device> devices = hostConfig.devices();
		List<DeviceInfo> updatedDevices = new ArrayList<DeviceInfo>();
		for (Device device : devices) {
			updatedDevices.add(getDeviceInfo(device));
		}
		hostConfigInfo.setDevices(updatedDevices);

		hostConfigInfo.setDns(hostConfig.dns());
		hostConfigInfo.setDnsOptions(hostConfig.dnsOptions());
		hostConfigInfo.setDnsSearch(hostConfig.dnsSearch());
		hostConfigInfo.setExtraHosts(hostConfig.extraHosts());
		hostConfigInfo.setIpcMode(hostConfig.ipcMode());
		HostConfigInfoKey hostConfigInfoKey = new HostConfigInfoKey();
		hostConfigInfoKey.setId(containerID);
		hostConfigInfoKey.setName(getName());
		hostConfigInfo.setKey(hostConfigInfoKey);
		hostConfigInfo.setLinks(hostConfig.links());
		hostConfigInfo.setLogConfig(getLogConfigInfo(hostConfig.logConfig()));

		List<LxcConfParameter> lxcConfParameterList = hostConfig.lxcConf();
		List<LxcConfParameterInfo> updatedLxcConfParameterList = new ArrayList<LxcConfParameterInfo>();
		for (LxcConfParameter lxcConfParameter : lxcConfParameterList) {
			updatedLxcConfParameterList.add(getLxcConfParameterInfo(lxcConfParameter));
		}
		hostConfigInfo.setLxcConf(updatedLxcConfParameterList);

		hostConfigInfo.setMemory(hostConfig.memory());
		hostConfigInfo.setMemoryReservation(hostConfig.memoryReservation());
		hostConfigInfo.setMemorySwap(hostConfig.memorySwap());
		hostConfigInfo.setMemorySwappiness(hostConfig.memorySwappiness());
		hostConfigInfo.setNanoCpus(hostConfig.nanoCpus());
		hostConfigInfo.setNetworkMode(hostConfig.networkMode());
		hostConfigInfo.setOomKillDisable(hostConfig.oomKillDisable());
		hostConfigInfo.setOomScoreAdj(hostConfig.oomScoreAdj());
		hostConfigInfo.setPidMode(hostConfig.pidMode());
		hostConfigInfo.setPidsLimit(hostConfig.pidsLimit());
		Map<String, List<PortBinding>> portBindings = hostConfig.portBindings();
		Map<String, String> updatedPortBindings = new HashMap<String, String>();

		for (Map.Entry<String, List<PortBinding>> portBindingEntry : portBindings.entrySet()) {
			String key = portBindingEntry.getKey();
			List<PortBinding> value = portBindingEntry.getValue();
			String valueAsString = jsonUtil.getObjectAsJson(value);
			updatedPortBindings.put(key, valueAsString);
		}
		hostConfigInfo.setPortBindings(updatedPortBindings);
		hostConfigInfo.setPrivileged(hostConfig.privileged());
		hostConfigInfo.setPublishAllPorts(hostConfig.publishAllPorts());
		hostConfigInfo.setReadonlyRootfs(hostConfig.readonlyRootfs());
		HostConfig.RestartPolicy restartPolicy = hostConfig.restartPolicy();
		hostConfigInfo.setRestartPolicy(getRestartPolicyInfo(restartPolicy));
		hostConfigInfo.setSecurityOpt(hostConfig.securityOpt());
		hostConfigInfo.setShmSize(hostConfig.shmSize());
		hostConfigInfo.setStorageOpt(hostConfig.storageOpt());
		hostConfigInfo.setTmpfs(hostConfig.tmpfs());
		List<Ulimit> uLimitList = hostConfig.ulimits();
		List<UlimitInfo> updatedULimitList = new ArrayList<UlimitInfo>();
		for (Ulimit ulimit : uLimitList) {
			updatedULimitList.add(getUlimitInfo(ulimit));
		}
		hostConfigInfo.setUlimits(updatedULimitList);
		hostConfigInfo.setVolumesFrom(hostConfig.volumesFrom());

		return hostConfigInfo;
	}

	/**
	 * Gets the inspection info.
	 *
	 * @param cInfo the c info
	 * @param containerId the container id
	 * @param containerConfigInstanceId the container config instance id
	 * @param containerHostConfigId the container host config id
	 * @param containerStateId the container state id
	 * @param networkSettingsId the network settings id
	 * @return the inspection info
	 */
	public InspectionInfo getInspectionInfo(ContainerInfo cInfo, String containerId, String containerConfigInstanceId,
			String containerHostConfigId, String containerStateId, String networkSettingsId) {
		/*
		 * ContainerInfo
		 */
		// TODO
		InspectionInfo iInfo = new InspectionInfo();
		InspectionInfoKey iKey = new InspectionInfoKey();
		iKey.setId(containerId);
		iKey.setName(getName());
		iInfo.setKey(iKey);
		iInfo.setInstanceId(getInstanceId());
		iInfo.setAppArmorProfile(cInfo.appArmorProfile());
		iInfo.setArgs(cInfo.args());
		iInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		iInfo.setContainerConfigId(containerConfigInstanceId);
		iInfo.setContainerHostConfigId(containerHostConfigId);
		iInfo.setContainerStateId(containerStateId);
		iInfo.setDriver(cInfo.driver());
		iInfo.setExecDriver(cInfo.execDriver());
		iInfo.setExecIds(cInfo.execIds());
		iInfo.setHostnamePath(cInfo.hostnamePath());
		iInfo.setHostsPath(cInfo.hostsPath());
		iInfo.setImage(cInfo.image());
		iInfo.setInstanceId(cInfo.id());
		iInfo.setLogPath(cInfo.logPath());
		iInfo.setMountLabel(cInfo.mountLabel());
		List<ContainerMount> mounts = cInfo.mounts();
		List<ContainerMountInfo> updatedMounds = new ArrayList<ContainerMountInfo>();
		for (ContainerMount curMount : mounts) {
			updatedMounds.add(getContainerMountInfo(curMount));
		}
		iInfo.setMounts(updatedMounds);
		iInfo.setMountLabel(cInfo.mountLabel());
		iInfo.setNetworkSettingsId(networkSettingsId);
		Node node = cInfo.node();
		iInfo.setNode(getNodeInfo(node));
		iInfo.setPath(cInfo.path());
		iInfo.setProcessLabel(cInfo.processLabel());
		iInfo.setResolvConfPath(cInfo.resolvConfPath());
		iInfo.setRestartCount(cInfo.restartCount());
		return iInfo;
	}

	/**
	 * Gets the log info.
	 *
	 * @param containerId the container id
	 * @return the log info
	 */
	public LogInfo getLogInfo(String containerId) {
		LogInfo returnValue = new LogInfo();
		returnValue.setInstanceId(getInstanceId());
		LogInfoKey key = new LogInfoKey();
		key.setId(containerId);
		key.setName(getName());
		returnValue.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		returnValue.setKey(key);
		try {
			LogStream stream = dockerClient.logs(containerId, LogsParam.stdout(), LogsParam.stderr());
			returnValue.setLog(stream.readFully());
		} catch (Exception e) {
			LOGGER.error("exception in docker get container logs for container id: {}", containerId);
			LOGGER.error("exception details: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

	/**
	 * Gets the network config info.
	 *
	 * @param nConfig the n config
	 * @param containerId the container id
	 * @return the network config info
	 */
	public NetworkingConfigInfo getNetworkConfigInfo(NetworkingConfig nConfig, String containerId) {

		/*
		 * ContainerInfo --> ContainerConfig - NetworkingConfig
		 */

		NetworkingConfigInfo returnValue = new NetworkingConfigInfo();
		returnValue.setInstanceId(getInstanceId());
		NetworkingConfigInfoKey key = new NetworkingConfigInfoKey();
		key.setId(containerId);
		key.setName(getName());
		returnValue.setKey(key);
		Map<String, EndpointConfig> endpointConfigMap = nConfig.endpointsConfig();
		Map<String, NetworkEndpointConfigInfo> updatedEndpointConfigMap = new HashMap<String, NetworkEndpointConfigInfo>();
		for (Map.Entry<String, EndpointConfig> entPointEntry : endpointConfigMap.entrySet()) {
			String curKey = entPointEntry.getKey();
			EndpointConfig curEndpointConfig = entPointEntry.getValue();
			updatedEndpointConfigMap.put(curKey, getNetworkEndpointConfigInfo(curEndpointConfig));
		}

		returnValue.setEndpointsConfig(updatedEndpointConfigMap);
		returnValue.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		return returnValue;
	}

	/**
	 * Gets the network settings info.
	 *
	 * @param nSettings the n settings
	 * @param containerId the container id
	 * @return the network settings info
	 */
	@SuppressWarnings("unchecked")
	public NetworkSettingsInfo getNetworkSettingsInfo(NetworkSettings nSettings, String containerId) {
		/*
		 * ContainerInfo - NetworkSettings
		 */
		NetworkSettingsInfo networkSettingsInfo = new NetworkSettingsInfo();
		networkSettingsInfo.setInstanceId(getInstanceId());
		NetworkSettingsInfoKey networkSettingsInfoKey = new NetworkSettingsInfoKey();
		networkSettingsInfoKey.setId(containerId);
		networkSettingsInfoKey.setName(getName());
		networkSettingsInfo.setBridge(nSettings.bridge());
		networkSettingsInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		networkSettingsInfo.setEndpointId(nSettings.endpointId());
		networkSettingsInfo.setGateway(nSettings.gateway());
		networkSettingsInfo.setGlobalIPv6Address(nSettings.ipv6Gateway());
		networkSettingsInfo.setGlobalIPv6PrefixLen(nSettings.globalIPv6PrefixLen());
		networkSettingsInfo.setHairpinMode(nSettings.hairpinMode());
		networkSettingsInfo.setIpAddress(nSettings.ipAddress());
		networkSettingsInfo.setLinkLocalIPv6Address(nSettings.linkLocalIPv6Address());
		networkSettingsInfo.setLinkLocalIPv6PrefixLen(nSettings.linkLocalIPv6PrefixLen());
		networkSettingsInfo.setMacAddress(nSettings.macAddress());
		Map<String, AttachedNetwork> networks = nSettings.networks();
		Map<String, AttachedNetworkInfo> updatedAttachedNetworkMap = new HashMap<String, AttachedNetworkInfo>();
		for (Map.Entry<String, AttachedNetwork> entry : networks.entrySet()) {
			String key = entry.getKey();
			AttachedNetwork network = entry.getValue();
			AttachedNetworkInfo AttachedNetworkInfo = getAttachedNetworkInfo(network);
			updatedAttachedNetworkMap.put(key, AttachedNetworkInfo);
		}
		networkSettingsInfo.setNetworks(updatedAttachedNetworkMap);
		Map<String, String> updatedPortMapping = new HashMap<String, String>();
		Map<String, Map<String, String>> portMapping = nSettings.portMapping();
		for (Map.Entry<String, Map<String, String>> portEntry : portMapping.entrySet()) {
			String key = portEntry.getKey();
			Map<String, String> value = portEntry.getValue();
			String valueasString = jsonUtil.getObjectAsJson(value);
			updatedPortMapping.put(key, valueasString);
		}
		networkSettingsInfo.setPortMapping(updatedPortMapping);

		Map<String, String> updatedPorts = new HashMap<String, String>();
		Map<String, List<PortBinding>> ports = nSettings.ports();
		for (Map.Entry<String, List<PortBinding>> port : ports.entrySet()) {
			String key = port.getKey();
			List<PortBinding> value = port.getValue();
			String valueasString = jsonUtil.getObjectAsJson(value);
			updatedPorts.put(key, valueasString);
		}
		networkSettingsInfo.setPorts(updatedPorts);
		networkSettingsInfo.setSandboxId(nSettings.sandboxId());
		networkSettingsInfo.setSandboxKey(nSettings.sandboxKey());

		return networkSettingsInfo;
	}

	/**
	 * Gets the processes info.
	 *
	 * @param containedID the contained ID
	 * @return the processes info
	 */
	public ProcessesInfo getProcessesInfo(String containedID) {
		ProcessesInfo returnValue = null;
		try {
			TopResults topResults = dockerClient.topContainer(containedID);
			returnValue = new ProcessesInfo();
			returnValue.setInstanceId(getInstanceId());
			returnValue.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			ImmutableList<ImmutableList<String>> processes = topResults.processes();
			List<String> updatedProcesses = new ArrayList<String>();
			for (ImmutableList<String> process : processes) {
				String vlaueasJson = jsonUtil.getObjectAsJson(process);
				updatedProcesses.add(vlaueasJson);
			}
			returnValue.setProcesses(updatedProcesses);
		} catch (Exception e) {
			LOGGER.error("exception in docker get container process details for container id: {}", containedID);
			LOGGER.error("exception details: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return returnValue;
	}

	/**
	 * Gets the resource usage info.
	 *
	 * @param containerStats the container stats
	 * @param containerId the container id
	 * @return the resource usage info
	 */
	ResourceUsageInfo getResourceUsageInfo(ContainerStats containerStats, String containerId) {

		/*
		 * ContainerStats
		 */
		ResourceUsageInfo resourceUsageInfo = new ResourceUsageInfo();
		resourceUsageInfo.setInstanceId(getInstanceId());
		ResourceUsageInfoKey resourceUsageInfoKey = new ResourceUsageInfoKey();
		resourceUsageInfoKey.setId(containerId);
		resourceUsageInfoKey.setName(getName());
		resourceUsageInfo.setKey(resourceUsageInfoKey);

		BlockIoStats blockIoStats = containerStats.blockIoStats();
		resourceUsageInfo.setBlockIoStats(getBlockIoStatsInfo(blockIoStats));
		resourceUsageInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		CpuStats cpuStats = containerStats.cpuStats();
		CpuStats perCpustats = containerStats.precpuStats();
		resourceUsageInfo.setCpuStats(getCpuStatsInfo(cpuStats, perCpustats));
		MemoryStats memoryStats = containerStats.memoryStats();
		resourceUsageInfo.setMemoryStats(getMemoryStatsInfo(memoryStats));
		NetworkStats networkStats = containerStats.network();
		resourceUsageInfo.setNetwork(getNetworkStatsInfo(networkStats));
		Map<String, NetworkStats> networks = containerStats.networks();
		Map<String, NetworkStatsInfo> updatedNetworks = new HashMap<String, NetworkStatsInfo>();

		for (Map.Entry<String, NetworkStats> networkEntry : networks.entrySet()) {
			updatedNetworks.put(networkEntry.getKey(), getNetworkStatsInfo(networkEntry.getValue()));
		}
		resourceUsageInfo.setNetworks(updatedNetworks);
		resourceUsageInfo.setReadTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(containerStats.read()));
		return resourceUsageInfo;
	}

	/**
	 * Gets the state info.
	 *
	 * @param cState the c state
	 * @param containerId the container id
	 * @param healthID the health ID
	 * @return the state info
	 */
	public StateInfo getStateInfo(ContainerState cState, String containerId, String healthID) {

		/*
		 * ContainerInfo - ContainerState
		 */
		StateInfo stateInfo = new StateInfo();
		stateInfo.setInstanceId(getInstanceId());
		StateInfoKey stateInfoKey = new StateInfoKey();
		stateInfoKey.setId(containerId);
		stateInfoKey.setName(getName());
		stateInfo.setKey(stateInfoKey);
		stateInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		stateInfo.setContainerHealthId(healthID);
		stateInfo.setError(cState.error());
		stateInfo.setExitCode(cState.exitCode());
		stateInfo.setFinishedAt(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString((cState.finishedAt())));
		stateInfo.setOomKilled(cState.oomKilled());
		stateInfo.setPaused(cState.paused());
		stateInfo.setPid(cState.pid());
		stateInfo.setRestarting(cState.restarting());
		stateInfo.setRunning(cState.running());
		stateInfo.setStartedAt(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString((cState.startedAt())));
		stateInfo.setStatus(cState.status());
		return stateInfo;
	}
	
	/**
	 * Load certificate.
	 *
	 * @return the docker certificates
	 * @throws ConfigException the config exception
	 */
	private DockerCertificates loadCertificate() throws ConfigException {
		DockerCertificates returnValue = null;
		try {
			Resource resource = new DefaultResourceLoader().getResource(dockerCertificatesName);
			returnValue = new DockerCertificates(Paths.get(resource.getURI()));
		} catch (Exception e) {
			throw new ConfigException("Could not read the docker certificate " + dockerCertificatesName);
		}
		return returnValue;
	}

	/**
	 * Gets the change info.
	 *
	 * @param cChange the c change
	 * @param containerId the container id
	 * @return the change info
	 */
	private ChangeInfo getChangeInfo(ContainerChange cChange, String containerId) {
		ChangeInfo returnValue = new ChangeInfo();
		ChangeInfo changeInfo = new ChangeInfo();
		ChangeInfoKey key = new ChangeInfoKey();
		key.setId(containerId);
		key.setName(getName());
		changeInfo.setKey(key);
		changeInfo.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		changeInfo.setKind(cChange.kind());
		changeInfo.setPath(cChange.path());
		return returnValue;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	private String getName() {
		StringBuilder builder = new StringBuilder();
		builder.append(appName);
		builder.append(NameCollection.UNDERSCORE_CHARACTER);
		builder.append(hostName);
		builder.append(NameCollection.UNDERSCORE_CHARACTER);
		builder.append(port);
		return builder.toString();
	}

	/**
	 * Gets the health log info.
	 *
	 * @param hLog the h log
	 * @return the health log info
	 */
	private HealthLogInfo getHealthLogInfo(HealthLog hLog) {
		HealthLogInfo returnValue = new HealthLogInfo();
		returnValue.setEnd(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(hLog.end()));
		returnValue.setStart(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(hLog.start()));
		returnValue.setExitCode(hLog.exitCode());
		returnValue.setOutput(hLog.output());
		return returnValue;
	}

	/**
	 * Gets the attached network info.
	 *
	 * @param aNetwork the a network
	 * @return the attached network info
	 */
	private AttachedNetworkInfo getAttachedNetworkInfo(AttachedNetwork aNetwork) {
		AttachedNetworkInfo returnInfo = new AttachedNetworkInfo();
		returnInfo.setAliases(aNetwork.aliases());
		returnInfo.setEndpointId(aNetwork.endpointId());
		returnInfo.setGateway(aNetwork.gateway());
		returnInfo.setGlobalIPv6Address(aNetwork.globalIPv6Address());
		returnInfo.setGlobalIPv6PrefixLen(aNetwork.globalIPv6PrefixLen());
		returnInfo.setIpAddress(aNetwork.ipAddress());
		returnInfo.setIpPrefixLen(aNetwork.ipPrefixLen());
		returnInfo.setIpv6Gateway(aNetwork.ipv6Gateway());
		returnInfo.setMacAddress(aNetwork.macAddress());
		returnInfo.setNetworkId(aNetwork.networkId());
		return returnInfo;
	}

	/**
	 * Gets the bulk in device rate info.
	 *
	 * @param rate the rate
	 * @return the bulk in device rate info
	 */
	public BlkIoDeviceRateInfo getBulkInDeviceRateInfo(BlkioDeviceRate rate) {
		BlkIoDeviceRateInfo returnValue = new BlkIoDeviceRateInfo();
		returnValue.setPath(rate.path());
		returnValue.setRate(rate.rate());
		return returnValue;
	}

	/**
	 * Gets the device info.
	 *
	 * @param device the device
	 * @return the device info
	 */
	private DeviceInfo getDeviceInfo(Device device) {
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setCgroupPermissions(device.cgroupPermissions());
		deviceInfo.setPathInContainer(device.pathInContainer());
		deviceInfo.setPathOnHost(device.pathOnHost());
		return deviceInfo;
	}

	/**
	 * Gets the log config info.
	 *
	 * @param lConfig the l config
	 * @return the log config info
	 */
	private LogConfigInfo getLogConfigInfo(LogConfig lConfig) {
		LogConfigInfo returnValue = new LogConfigInfo();
		returnValue.setLogOptions(lConfig.logOptions());
		returnValue.setLogType(lConfig.logType());
		return returnValue;

	}

	/**
	 * Gets the lxc conf parameter info.
	 *
	 * @param param the param
	 * @return the lxc conf parameter info
	 */
	private LxcConfParameterInfo getLxcConfParameterInfo(LxcConfParameter param) {
		LxcConfParameterInfo returnValue = new LxcConfParameterInfo();
		returnValue.setKey(param.key());
		returnValue.setValue(param.value());
		return returnValue;
	}

	/**
	 * Gets the restart policy info.
	 *
	 * @param restartP the restart P
	 * @return the restart policy info
	 */
	private RestartPolicyInfo getRestartPolicyInfo(HostConfig.RestartPolicy restartP) {
		RestartPolicyInfo restartPolicyInfo = new RestartPolicyInfo();
		restartPolicyInfo.setMaxRetryCount(restartP.maxRetryCount());
		restartPolicyInfo.setName(restartP.name());
		return restartPolicyInfo;
	}

	/**
	 * Gets the ulimit info.
	 *
	 * @param ul the ul
	 * @return the ulimit info
	 */
	private UlimitInfo getUlimitInfo(Ulimit ul) {
		UlimitInfo returnValue = new UlimitInfo();
		returnValue.setHard(ul.hard());
		returnValue.setName(ul.name());
		returnValue.setSoft(ul.soft());
		return returnValue;
	}

	/**
	 * Gets the block io stats info.
	 *
	 * @param stat the stat
	 * @return the block io stats info
	 */
	BlockIoStatsInfo getBlockIoStatsInfo(BlockIoStats stat) {
		BlockIoStatsInfo returnValue = new BlockIoStatsInfo();
		List<Object> iomr = stat.ioMergedRecursive();
		List<String> iomrUpdated = new ArrayList<String>();
		for (Object obj : iomr) {
			iomrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setIoMergedRecursive(iomrUpdated);
		List<Object> ioqr = stat.ioQueueRecursive();
		List<String> ioqrUpdated = new ArrayList<String>();
		for (Object objioqr : ioqr) {
			iomrUpdated.add(jsonUtil.getObjectAsJson(objioqr));
		}
		returnValue.setIoQueueRecursive(ioqrUpdated);

		List<Object> iosbr = stat.ioServiceBytesRecursive();
		List<String> iosbrUpdated = new ArrayList<String>();
		for (Object obj : iosbr) {
			iosbrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setIoServiceBytesRecursive(iosbrUpdated);

		List<Object> iosr = stat.ioServicedRecursive();
		List<String> iosrUpdated = new ArrayList<String>();
		for (Object obj : iosr) {
			iosrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setIoServicedRecursive(iosrUpdated);

		List<Object> iostr = stat.ioServiceTimeRecursive();
		List<String> iostrUpdated = new ArrayList<String>();
		for (Object obj : iostr) {
			iostrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setIoServiceTimeRecursive(iostrUpdated);

		List<Object> iotr = stat.ioTimeRecursive();
		List<String> iotrUpdated = new ArrayList<String>();
		for (Object obj : iotr) {
			iotrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setIoTimeRecursive(iotrUpdated);

		List<Object> iowtr = stat.ioWaitTimeRecursive();
		List<String> iowtrUpdated = new ArrayList<String>();
		for (Object obj : iowtr) {
			iowtrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setIoWaitTimeRecursive(iowtrUpdated);

		List<Object> iosetr = stat.sectorsRecursive();
		List<String> iosetrUpdated = new ArrayList<String>();
		for (Object obj : iosetr) {
			iosetrUpdated.add(jsonUtil.getObjectAsJson(obj));
		}
		returnValue.setSectorsRecursive(iosetrUpdated);
		return returnValue;
	}

	/**
	 * Gets the cpu stats info.
	 *
	 * @param stats the stats
	 * @param per the per
	 * @return the cpu stats info
	 */
	private CpuStatsInfo getCpuStatsInfo(CpuStats stats, CpuStats per) {
		CpuStatsInfo returnValue = new CpuStatsInfo();
		returnValue.setSystemCpuUsage(stats.systemCpuUsage());
		returnValue.setThrottledPeriod(stats.throttlingData().periods());
		returnValue.setThrottledTime(stats.throttlingData().throttledTime());
		returnValue.setThrottlingDataPeriods(stats.throttlingData().throttledPeriods());
		returnValue.setTotalUsage(stats.cpuUsage().totalUsage());
		returnValue.setUsageInKernelmode(stats.cpuUsage().usageInKernelmode());
		returnValue.setUsageInUsermode(stats.cpuUsage().usageInUsermode());
		CpuStatsInfo perCpuStats = new CpuStatsInfo();
		perCpuStats.setSystemCpuUsage(per.systemCpuUsage());
		perCpuStats.setThrottledPeriod(per.throttlingData().periods());
		perCpuStats.setThrottledTime(per.throttlingData().throttledTime());
		perCpuStats.setThrottlingDataPeriods(per.throttlingData().throttledPeriods());
		perCpuStats.setTotalUsage(per.cpuUsage().totalUsage());
		perCpuStats.setUsageInKernelmode(per.cpuUsage().usageInKernelmode());
		perCpuStats.setUsageInUsermode(per.cpuUsage().usageInUsermode());
		String perCpuStatsAsString = jsonUtil.getObjectAsJson(perCpuStats);
		returnValue.setPercpuUsage(perCpuStatsAsString);
		return returnValue;
	}

	/**
	 * Gets the memory stats info.
	 *
	 * @param m the m
	 * @return the memory stats info
	 */
	private MemoryStatsInfo getMemoryStatsInfo(MemoryStats m) {
		MemoryStatsInfo returnValue = new MemoryStatsInfo();
		returnValue.setMaxUsage(m.maxUsage());
		returnValue.setUsage(m.usage());
		returnValue.setFailcnt(m.failcnt());
		returnValue.setLimit(m.limit());
		returnValue.setActiveFile(m.stats().activeFile());
		returnValue.setTotalActiveFile(m.stats().totalActiveFile());
		returnValue.setInactiveFile(m.stats().inactiveFile());
		returnValue.setTotalInactiveFile(m.stats().totalInactiveFile());
		returnValue.setCache(m.stats().cache());
		returnValue.setTotalCache(m.stats().totalCache());
		returnValue.setActiveAnon(m.stats().activeAnon());
		returnValue.setTotalActiveAnon(m.stats().totalActiveAnon());
		returnValue.setInactiveAnon(m.stats().inactiveAnon());
		returnValue.setTotalInactiveAnon(m.stats().totalInactiveAnon());
		returnValue.setHierarchicalMemoryLimit(m.stats().hierarchicalMemoryLimit());
		returnValue.setMappedFile(m.stats().mappedFile());
		returnValue.setTotalMappedFile(m.stats().totalMappedFile());
		returnValue.setPgfault(m.stats().pgmajfault());
		returnValue.setTotalPgfault(m.stats().totalPgfault());
		returnValue.setPgpgout(m.stats().pgpgout());
		returnValue.setTotalPgpgout(m.stats().totalPgpgout());
		returnValue.setPgfault(m.stats().pgfault());
		returnValue.setTotalPgfault(m.stats().totalPgfault());
		returnValue.setRss(m.stats().rss());
		returnValue.setTotalRss(m.stats().totalRss());
		returnValue.setRssHuge(m.stats().rssHuge());
		returnValue.setTotalRssHuge(m.stats().totalRssHuge());
		returnValue.setUnevictable(m.stats().unevictable());
		returnValue.setTotalUnevictable(m.stats().totalUnevictable());
		returnValue.setWriteback(m.stats().writeback());
		returnValue.setTotalWriteback(m.stats().totalWriteback());
		return returnValue;
	}

	/**
	 * Gets the network stats info.
	 *
	 * @param network the network
	 * @return the network stats info
	 */
	private NetworkStatsInfo getNetworkStatsInfo(NetworkStats network) {
		NetworkStatsInfo returnValue = new NetworkStatsInfo();
		returnValue.setRxBytes(network.rxBytes());
		returnValue.setRxDropped(network.rxDropped());
		returnValue.setRxErrors(network.rxErrors());
		returnValue.setRxPackets(network.rxPackets());
		returnValue.setTxBytes(network.txBytes());
		returnValue.setTxDropped(network.txDropped());
		returnValue.setTxErrors(network.txErrors());
		returnValue.setTxPackets(network.txPackets());
		return returnValue;
	}

	/**
	 * Gets the network endpoint config info.
	 *
	 * @param end the end
	 * @return the network endpoint config info
	 */
	private NetworkEndpointConfigInfo getNetworkEndpointConfigInfo(EndpointConfig end) {
		NetworkEndpointConfigInfo returnValue = new NetworkEndpointConfigInfo();
		returnValue.setAliases(end.aliases());
		returnValue.setGateway(end.gateway());
		returnValue.setGlobalIPv6Address(end.globalIPv6Address());
		returnValue.setGlobalIPv6PrefixLen(end.globalIPv6PrefixLen());
		returnValue.setIpAddress(end.ipAddress());
		returnValue.setIpPrefixLen(end.ipPrefixLen());
		returnValue.setIpv4Address(end.ipamConfig().ipv4Address());
		returnValue.setIpv6Address(end.ipamConfig().ipv6Address());
		returnValue.setIpv6Gateway(end.ipv6Gateway());
		returnValue.setLinkLocalIPs(end.ipamConfig().linkLocalIPs());
		returnValue.setLinks(end.links());
		returnValue.setMacAddress(end.macAddress());
		return returnValue;
	}

	/**
	 * Gets the mount info.
	 *
	 * @param mt the mt
	 * @return the mount info
	 */
	private MountInfo getMountInfo(Mount mt) {
		MountInfo mInfo = new MountInfo();
		VolumeOptions vo = mt.volumeOptions();
		mInfo.setVolumeOptionsDriver(jsonUtil.getObjectAsJson(vo.driverConfig()));
		mInfo.setVolumeOptionsLabels(jsonUtil.getObjectAsJson(vo.labels()));
		mInfo.setVolumeOptionsNoCopy(vo.noCopy());
		mInfo.setTmfOptionsMode(mt.tmpfsOptions().mode());
		mInfo.setTmfOptionsSizeBytes(mt.tmpfsOptions().sizeBytes());
		mInfo.setType(mt.type());
		mInfo.setSource(mt.source());
		mInfo.setReadOnly(mt.readOnly());
		mInfo.setBindOptionsPropagation(mt.bindOptions().propagation());
		return mInfo;
	}

	/**
	 * Gets the container mount info.
	 *
	 * @param cm the cm
	 * @return the container mount info
	 */
	private ContainerMountInfo getContainerMountInfo(ContainerMount cm) {
		ContainerMountInfo containerMountInfo = new ContainerMountInfo();
		containerMountInfo.setDestination(cm.destination());
		containerMountInfo.setDriver(cm.driver());
		containerMountInfo.setMode(cm.mode());
		containerMountInfo.setName(cm.name());
		containerMountInfo.setPropagation(cm.propagation());
		containerMountInfo.setRw(cm.rw());
		containerMountInfo.setSource(cm.source());
		containerMountInfo.setType(cm.type());
		return containerMountInfo;
	}

	/**
	 * Gets the node info.
	 *
	 * @param nd the nd
	 * @return the node info
	 */
	private NodeInfo getNodeInfo(Node nd) {
		NodeInfo nodeInfo = new NodeInfo();
		nodeInfo.setAddr(nd.addr());
		nodeInfo.setId(nd.id());
		nodeInfo.setIp(nd.ip());
		nodeInfo.setName(nd.name());
		return nodeInfo;
	}

	/**
	 * Gets the instance id.
	 *
	 * @return the instance id
	 */
	private String getInstanceId() {
		return UUID.randomUUID().toString();
	}
}
