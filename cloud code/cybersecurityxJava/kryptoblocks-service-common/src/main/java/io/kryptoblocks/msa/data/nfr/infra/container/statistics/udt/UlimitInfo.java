package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class UlimitInfo.
 */
@Embeddable

/**
 * Instantiates a new ulimit info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new ulimit info.
 *
 * @param name the name
 * @param soft the soft
 * @param hard the hard
 */
@AllArgsConstructor
public class UlimitInfo {
	
	/** The name. */
	@Column
	String name;
	
	/** The soft. */
	@Column
    Long soft;

	/** The hard. */
	@Column
	Long hard;


}
