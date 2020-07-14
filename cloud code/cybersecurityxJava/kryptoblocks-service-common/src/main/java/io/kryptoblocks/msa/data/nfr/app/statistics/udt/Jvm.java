package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.lang.management.MemoryUsage;

// TODO: Auto-generated Javadoc
/**
 * The Class Jvm.
 */
@SuppressWarnings("unused")
@Embeddable

/**
 * Instantiates a new jvm.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new jvm.
 *
 * @param upTime the up time
 * @param averageLoad the average load
 * @param threadTotalStarted the thread total started
 * @param threads the threads
 */
@AllArgsConstructor
public class Jvm implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	 	
	/** The up time. */
	@Column
	long upTime;
	
	/** The average load. */
	@Column
	double averageLoad;
	
	/** The thread total started. */
	@Column
    long threadTotalStarted;
	
	/** The threads. */
	@Column
    long threads;
	
}
