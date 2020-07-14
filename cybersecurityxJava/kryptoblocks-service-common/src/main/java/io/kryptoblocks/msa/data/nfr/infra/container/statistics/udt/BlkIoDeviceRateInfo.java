package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class BlkIoDeviceRateInfo.
 */
@Embeddable

/**
 * Instantiates a new blk io device rate info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new blk io device rate info.
 *
 * @param path the path
 * @param rate the rate
 */
@AllArgsConstructor
public class BlkIoDeviceRateInfo {
	
	/** The path. */
	@Column
	String path;
	
	/** The rate. */
	@Column
	Integer rate;

}
