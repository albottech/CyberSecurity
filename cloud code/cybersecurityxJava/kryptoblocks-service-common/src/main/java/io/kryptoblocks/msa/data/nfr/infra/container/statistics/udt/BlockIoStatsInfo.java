package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class BlockIoStatsInfo.
 */
@Embeddable

/**
 * Instantiates a new block io stats info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new block io stats info.
 *
 * @param ioServiceBytesRecursive the io service bytes recursive
 * @param ioServicedRecursive the io serviced recursive
 * @param ioQueueRecursive the io queue recursive
 * @param ioServiceTimeRecursive the io service time recursive
 * @param ioWaitTimeRecursive the io wait time recursive
 * @param ioMergedRecursive the io merged recursive
 * @param ioTimeRecursive the io time recursive
 * @param sectorsRecursive the sectors recursive
 */
@AllArgsConstructor
public class BlockIoStatsInfo {
	
	/** The io service bytes recursive. */
	@Column 
	List<String> ioServiceBytesRecursive;
	
	/** The io serviced recursive. */
	@Column  
	List<String> ioServicedRecursive;
	
	/** The io queue recursive. */
	@Column  
	List<String> ioQueueRecursive;
	
	/** The io service time recursive. */
	@Column  
	List<String> ioServiceTimeRecursive;
	
	/** The io wait time recursive. */
	@Column  
	List<String> ioWaitTimeRecursive;
	
	/** The io merged recursive. */
	@Column
	List<String> ioMergedRecursive;
	
	/** The io time recursive. */
	@Column  
	List<String> ioTimeRecursive;
	
	/** The sectors recursive. */
	@Column  
	List<String> sectorsRecursive;

}
