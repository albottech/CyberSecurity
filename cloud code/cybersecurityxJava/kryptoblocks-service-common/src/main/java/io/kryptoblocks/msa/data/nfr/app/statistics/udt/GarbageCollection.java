package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.lang.management.GarbageCollectorMXBean;

// TODO: Auto-generated Javadoc
/**
 * The Class GarbageCollection.
 */
@SuppressWarnings("unused")
@Embeddable

/**
 * Instantiates a new garbage collection.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new garbage collection.
 *
 * @param gcCollectionCount the gc collection count
 * @param gcCollectionTime the gc collection time
 */
@AllArgsConstructor
public class GarbageCollection implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The gc collection count. */
	@Column
	long gcCollectionCount;
	
	/** The gc collection time. */
	@Column
	long gcCollectionTime;	  

}
