package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;


import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ResourceUsageInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.BlockIoStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.CpuStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.MemoryStatsInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.NetworkStatsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 

// TODO: Auto-generated Javadoc
/**
 * The Class ResourceUsageInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_STATS_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new resource usage info.
 */
@NoArgsConstructor

/**
 * Instantiates a new resource usage info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param readTime the read time
 * @param network the network
 * @param networks the networks
 * @param memoryStats the memory stats
 * @param blockIoStats the block io stats
 * @param cpuStats the cpu stats
 * @param precpuStats the precpu stats
 */
@AllArgsConstructor
public class ResourceUsageInfo {
	
	/** The key. */
	@EmbeddedId
	ResourceUsageInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The read time. */
	@Column
	String readTime;
	
	/** The network. */
	@Embedded
	NetworkStatsInfo network;
	
	/** The networks. */
	@ElementCollection
	Map<String, NetworkStatsInfo> networks;
	
	/** The memory stats. */
	@Embedded
	MemoryStatsInfo memoryStats;
	
	/** The block io stats. */
	@Embedded
	BlockIoStatsInfo blockIoStats;
	
	/** The cpu stats. */
	@Embedded
	CpuStatsInfo cpuStats;
	
	/** The precpu stats. */
	@Embedded
	CpuStatsInfo precpuStats;

}
