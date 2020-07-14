package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassLoader.
 */
@Embeddable

/**
 * Instantiates a new class loader.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new class loader.
 *
 * @param currentClassLoadCount the current class load count
 * @param totalClassLoadCount the total class load count
 * @param unloadedClassLoadCount the unloaded class load count
 */
@AllArgsConstructor
public class ClassLoader implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The current class load count. */
	@Column
	long currentClassLoadCount;
	
	/** The total class load count. */
	@Column
	long totalClassLoadCount;
	
	/** The unloaded class load count. */
	@Column
	long unloadedClassLoadCount;	 

}
