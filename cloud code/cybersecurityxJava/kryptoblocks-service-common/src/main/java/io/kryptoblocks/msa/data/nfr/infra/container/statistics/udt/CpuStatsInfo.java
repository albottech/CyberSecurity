package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class CpuStatsInfo.
 */
@Embeddable

/**
 * Instantiates a new cpu stats info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new cpu stats info.
 *
 * @param totalUsage the total usage
 * @param percpuUsage the percpu usage
 * @param usageInKernelmode the usage in kernelmode
 * @param usageInUsermode the usage in usermode
 * @param systemCpuUsage the system cpu usage
 * @param throttlingDataPeriods the throttling data periods
 * @param throttledPeriod the throttled period
 * @param throttledTime the throttled time
 */
@AllArgsConstructor
public class CpuStatsInfo {
	
	/** The total usage. */
	@Column
	Long totalUsage;
	
	/** The percpu usage. */
	@Column
	String percpuUsage;
	
	/** The usage in kernelmode. */
	@Column
	Long usageInKernelmode;
	
	/** The usage in usermode. */
	@Column
	Long usageInUsermode;
	
	/** The system cpu usage. */
	@Column
	Long systemCpuUsage;
		 
	
	/** The throttling data periods. */
	@Column 
	Long throttlingDataPeriods;
	
	/** The throttled period. */
	@Column
	Long throttledPeriod;
	
	/** The throttled time. */
	@Column
	Long throttledTime;

}
