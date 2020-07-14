package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ContainerMountInfo.
 */
@Embeddable

/**
 * Instantiates a new container mount info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new container mount info.
 *
 * @param type the type
 * @param name the name
 * @param source the source
 * @param destination the destination
 * @param driver the driver
 * @param mode the mode
 * @param rw the rw
 * @param propagation the propagation
 */
@AllArgsConstructor
public class ContainerMountInfo {
	
	/** The type. */
	@Column
	String type;

	/** The name. */
	@Column
	String name;

	/** The source. */
	@Column
	String source;

	/** The destination. */
	@Column
	String destination;

	/** The driver. */
	@Column
	String driver;

	/** The mode. */
	@Column
	String mode;

	/** The rw. */
	@Column
	Boolean rw;

	/** The propagation. */
	@Column
	String propagation;

}
