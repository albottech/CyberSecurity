package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class BlkIoWeightDevice.
 */
@Embeddable

/**
 * Instantiates a new blk io weight device.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new blk io weight device.
 *
 * @param path the path
 * @param weight the weight
 */
@AllArgsConstructor
public class BlkIoWeightDevice {

	/** The path. */
	@Column
	String path;
	
	/** The weight. */
	@Column
	Integer weight;

}
