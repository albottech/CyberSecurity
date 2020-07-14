package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.HostConfigInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.BlkIoDeviceRateInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.BlkIoWeightDevice;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.DeviceInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.LogConfigInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.LxcConfParameterInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.RestartPolicyInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.UlimitInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class HostConfigInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_HOST_CONFIG_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new host config info.
 */
@NoArgsConstructor

/**
 * Instantiates a new host config info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param binds the binds
 * @param blkioWeight the blkio weight
 * @param blkIoWeightDevice the blk io weight device
 * @param blkIoDeviceReadBps the blk io device read bps
 * @param blkIoDeviceWriteBps the blk io device write bps
 * @param blkIoDeviceReadIOps the blk io device read I ops
 * @param blkioDeviceWriteIOps the blkio device write I ops
 * @param containerIdFile the container id file
 * @param lxcConf the lxc conf
 * @param privileged the privileged
 * @param portBindings the port bindings
 * @param links the links
 * @param publishAllPorts the publish all ports
 * @param dns the dns
 * @param dnsOptions the dns options
 * @param dnsSearch the dns search
 * @param extraHosts the extra hosts
 * @param volumesFrom the volumes from
 * @param capAdd the cap add
 * @param capDrop the cap drop
 * @param networkMode the network mode
 * @param securityOpt the security opt
 * @param devices the devices
 * @param memory the memory
 * @param memorySwap the memory swap
 * @param memorySwappiness the memory swappiness
 * @param memoryReservation the memory reservation
 * @param nanoCpus the nano cpus
 * @param cpuPeriod the cpu period
 * @param cpuShares the cpu shares
 * @param cpusetCpus the cpuset cpus
 * @param cpusetMems the cpuset mems
 * @param cpuQuota the cpu quota
 * @param cgroupParent the cgroup parent
 * @param restartPolicy the restart policy
 * @param logConfig the log config
 * @param ipcMode the ipc mode
 * @param ulimits the ulimits
 * @param pidMode the pid mode
 * @param shmSize the shm size
 * @param oomKillDisable the oom kill disable
 * @param oomScoreAdj the oom score adj
 * @param autoRemove the auto remove
 * @param pidsLimit the pids limit
 * @param tmpfs the tmpfs
 * @param readonlyRootfs the readonly rootfs
 * @param storageOpt the storage opt
 */
@AllArgsConstructor
public class HostConfigInfo {
	
	/** The key. */
	@EmbeddedId
	HostConfigInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The binds. */
	@Column
	List<String> binds;
	
	/** The blkio weight. */
	@Column
	Integer blkioWeight;
	
	/** The blk io weight device. */
	@ElementCollection
	List<BlkIoWeightDevice> blkIoWeightDevice;
	
	/** The blk io device read bps. */
	@ElementCollection
	List<BlkIoDeviceRateInfo> blkIoDeviceReadBps;
	
	/** The blk io device write bps. */
	@ElementCollection
	List<BlkIoDeviceRateInfo> blkIoDeviceWriteBps;
	
	/** The blk io device read I ops. */
	@ElementCollection
	List<BlkIoDeviceRateInfo> blkIoDeviceReadIOps;
	
	/** The blkio device write I ops. */
	@ElementCollection
	List<BlkIoDeviceRateInfo> blkioDeviceWriteIOps;
	
	/** The container id file. */
	@Column
	String containerIdFile;
	
	/** The lxc conf. */
	@ElementCollection
	List<LxcConfParameterInfo> lxcConf;
	
	/** The privileged. */
	@Column
	Boolean privileged;
	
	/** The port bindings. */
	/*
	 * List<PortBinding> to String
	 */
	@ElementCollection
	Map<String, String> portBindings;
	
	
	
	/** The links. */
	@Column
	List<String> links;
	
	/** The publish all ports. */
	@Column
	Boolean publishAllPorts;
	
	/** The dns. */
	@Column
	List<String> dns;
	
	/** The dns options. */
	@Column
	List<String> dnsOptions;
	
	/** The dns search. */
	@Column
	List<String> dnsSearch;
	
	/** The extra hosts. */
	@Column
	List<String> extraHosts;
	
	/** The volumes from. */
	@Column
	List<String> volumesFrom;
	
	/** The cap add. */
	@Column
	List<String> capAdd;
	
	/** The cap drop. */
	@Column
	List<String> capDrop;
	
	/** The network mode. */
	@Column
	String networkMode;
	
	/** The security opt. */
	@Column
	List<String> securityOpt;

	/** The devices. */
	@ElementCollection
	List<DeviceInfo> devices;
	
	/** The memory. */
	@Column
	Long memory;
	
	/** The memory swap. */
	@Column
	Long memorySwap;
	
	/** The memory swappiness. */
	@Column
	Integer memorySwappiness;
	
	/** The memory reservation. */
	@Column
	Long memoryReservation;
	
	/** The nano cpus. */
	@Column
	Long nanoCpus;
	
	/** The cpu period. */
	@Column
	Long cpuPeriod;
	
	/** The cpu shares. */
	@Column
	Long cpuShares;

	/** The cpuset cpus. */
	@Column
	String cpusetCpus;
	
	/** The cpuset mems. */
	@Column
	String cpusetMems;
	
	/** The cpu quota. */
	@Column
	Long cpuQuota;
	
	/** The cgroup parent. */
	@Column
	String cgroupParent;
	
	/** The restart policy. */
	@Embedded
	RestartPolicyInfo restartPolicy;
	
	/** The log config. */
	@Embedded
	LogConfigInfo logConfig;

	/** The ipc mode. */
	@Column
	String ipcMode;
	
	/** The ulimits. */
	@ElementCollection
	List<UlimitInfo> ulimits;
	
	/** The pid mode. */
	@Column
	String pidMode;

	/** The shm size. */
	@Column
	Long shmSize;
	
	/** The oom kill disable. */
	@Column
	Boolean oomKillDisable;
	
	/** The oom score adj. */
	@Column
	Integer oomScoreAdj;
	
	/** The auto remove. */
	@Column
	Boolean autoRemove;
	
	/** The pids limit. */
	@Column
	Integer pidsLimit;
	
	/** The tmpfs. */
	@Column
	Map<String, String> tmpfs;

	/** The readonly rootfs. */
	@Column
	Boolean readonlyRootfs;
	
	/** The storage opt. */
	@Column
	Map<String, String> storageOpt;
}