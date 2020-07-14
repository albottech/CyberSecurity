package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class LxcConfParameterInfo.
 */
@Embeddable

/**
 * Instantiates a new lxc conf parameter info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new lxc conf parameter info.
 *
 * @param key the key
 * @param value the value
 */
@AllArgsConstructor
public class LxcConfParameterInfo {
	
	/** The key. */
	@Column
	String key;

	/** The value. */
	@Column
    String value;

}
