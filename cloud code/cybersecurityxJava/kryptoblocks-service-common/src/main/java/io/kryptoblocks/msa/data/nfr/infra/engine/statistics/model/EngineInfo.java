package io.kryptoblocks.msa.data.nfr.infra.engine.statistics.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.collect.ImmutableList;
import com.spotify.docker.client.messages.Info.Plugins;
import com.spotify.docker.client.messages.Info.RegistryConfig;
import com.spotify.docker.client.messages.swarm.SwarmInfo;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.ChangeInfo;
import io.kryptoblocks.msa.data.nfr.infra.engine.statistics.key.EngineInfoKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class EngineInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_ENGINE_INFO_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new engine info.
 */
@NoArgsConstructor

/**
 * Instantiates a new engine info.
 *
 * @param key the key
 * @param architecture the architecture
 * @param clusterStore the cluster store
 * @param cgroupDriver the cgroup driver
 * @param containers the containers
 * @param containersRunning the containers running
 * @param containersStopped the containers stopped
 * @param containersPaused the containers paused
 * @param cpuCfsPeriod the cpu cfs period
 * @param cpuCfsQuota the cpu cfs quota
 * @param debug the debug
 * @param dockerRootDir the docker root dir
 * @param storageDriver the storage driver
 * @param driverStatus the driver status
 * @param experimentalBuild the experimental build
 * @param httpProxy the http proxy
 * @param httpsProxy the https proxy
 * @param ipv4Forwarding the ipv 4 forwarding
 * @param images the images
 * @param indexServerAddress the index server address
 * @param initPath the init path
 * @param initSha1 the init sha 1
 * @param kernelMemory the kernel memory
 * @param kernelVersion the kernel version
 * @param labels the labels
 * @param memTotal the mem total
 * @param memoryLimit the memory limit
 * @param cpus the cpus
 * @param eventsListener the events listener
 * @param fileDescriptors the file descriptors
 * @param goroutines the goroutines
 * @param noProxy the no proxy
 * @param oomKillDisable the oom kill disable
 * @param operatingSystem the operating system
 * @param osType the os type
 * @param plugins the plugins
 * @param registryConfig the registry config
 * @param serverVersion the server version
 * @param swapLimit the swap limit
 * @param swarmInfoId the swarm info id
 * @param systemStatus the system status
 * @param systemTime the system time
 */
@AllArgsConstructor
public class EngineInfo implements Serializable {
	  
  	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
 
	/** The key. */
	@EmbeddedId
	EngineInfoKey key;
	
	/** The architecture. */
	@Column
	  String architecture;
	  
	  /** The cluster store. */
  	@Column
	  String clusterStore;
	  
	  /** The cgroup driver. */
  	@Column
	  String cgroupDriver;

	  /** The containers. */
  	@Column
	  Integer containers;

	  /** The containers running. */
  	@Column
	  Integer containersRunning;

	  /** The containers stopped. */
  	@Column
	  Integer containersStopped;

	  /** The containers paused. */
  	@Column
	  Integer containersPaused;

	  /** The cpu cfs period. */
  	@Column
	  Boolean cpuCfsPeriod;

	  /** The cpu cfs quota. */
  	@Column
	  Boolean cpuCfsQuota;

	  /** The debug. */
  	@Column
	  Boolean debug;
	  
	  /** The docker root dir. */
  	@Column
	  String dockerRootDir;

	  /** The storage driver. */
  	@Column
	  String storageDriver;
	  
	  /** The driver status. */
  	@Column
	  List<String> driverStatus;
	   

	  /** The experimental build. */
  	@Column
	  Boolean experimentalBuild;

	  /** The http proxy. */
  	@Column
	  String httpProxy;


	  /** The https proxy. */
  	@Column
	  String httpsProxy;

	  

	  /** The ipv 4 forwarding. */
  	@Column
	  Boolean ipv4Forwarding;

	  /** The images. */
  	@Column
	  Integer images;

	  /** The index server address. */
  	@Column
	  String indexServerAddress;


	  /** The init path. */
  	@Column
	  String initPath;

	  /** The init sha 1. */
  	@Column
	  String initSha1;

	  /** The kernel memory. */
  	@Column
	  Boolean kernelMemory;

	  /** The kernel version. */
  	@Column
	  String kernelVersion;

	  /** The labels. */
  	@Column
	  List<String> labels;

	  /** The mem total. */
  	@Column
	  Long memTotal;


	  /** The memory limit. */
  	@Column
	  Boolean memoryLimit;

	  /** The cpus. */
  	@Column
	  Integer cpus;

	  /** The events listener. */
  	@Column
	  Integer eventsListener;

	  /** The file descriptors. */
  	@Column
	  Integer fileDescriptors;

	  /** The goroutines. */
  	@Column
	  Integer goroutines;


	  /** The no proxy. */
  	@Column
	  String noProxy;

	  /** The oom kill disable. */
  	@Column
	  Boolean oomKillDisable;

	  /** The operating system. */
  	@Column
	  String operatingSystem;

	  /** The os type. */
  	@Column
	  String osType;

	  /** The plugins. */
  	@Column
	  Plugins plugins;

	  /** The registry config. */
  	@Column
	  RegistryConfig registryConfig;

	  /** The server version. */
  	@Column
	  String serverVersion;

	  /** The swap limit. */
  	@Column
	  Boolean swapLimit;
	  
	  /** The swarm info id. */
  	@Column
	  String swarmInfoId;
	  
	  /** The system status. */
  	@Column
	  List<String> systemStatus;

	  /** The system time. */
  	@Column
	  Date systemTime;


}
