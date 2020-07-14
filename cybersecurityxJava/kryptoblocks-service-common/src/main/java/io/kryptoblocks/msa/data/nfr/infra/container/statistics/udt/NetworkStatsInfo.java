package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;
 
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// TODO: Auto-generated Javadoc
/**
 * The Class NetworkStatsInfo.
 */
@Embeddable

/**
 * Instantiates a new network stats info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new network stats info.
 *
 * @param rxBytes the rx bytes
 * @param rxPackets the rx packets
 * @param rxDropped the rx dropped
 * @param rxErrors the rx errors
 * @param txBytes the tx bytes
 * @param txPackets the tx packets
 * @param txDropped the tx dropped
 * @param txErrors the tx errors
 */
@AllArgsConstructor
public class NetworkStatsInfo {
	
	/** The rx bytes. */
	@Column 
	Long rxBytes;
	
	/** The rx packets. */
	@Column 
	Long rxPackets;
	
	/** The rx dropped. */
	@Column 
	Long rxDropped;
	
	/** The rx errors. */
	@Column 
	Long rxErrors;
	
	/** The tx bytes. */
	@Column 
	Long txBytes;
	
	/** The tx packets. */
	@Column 
	Long txPackets;
	
	/** The tx dropped. */
	@Column 
	Long txDropped;
	
	/** The tx errors. */
	@Column 
	Long txErrors;

}
