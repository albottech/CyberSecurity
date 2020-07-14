package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class Change.
 */
@Embeddable

/**
 * Instantiates a new change.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new change.
 *
 * @param path the path
 * @param kind the kind
 */
@AllArgsConstructor
public class Change {
	
	/** The path. */
	@Column
	String path;
	
	/** The kind. */
	@Column
	Integer kind;
}
