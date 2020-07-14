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
 * The Class Threads.
 */
@SuppressWarnings("unused")
@Embeddable

/**
 * Instantiates a new threads.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new threads.
 *
 * @param peakThread the peak thread
 * @param threadDaemon the thread daemon
 * @param threadTotalStarted the thread total started
 * @param threads the threads
 */
@AllArgsConstructor
public class Threads implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	 	
	/** The peak thread. */
	@Column
	long peakThread;
	
	/** The thread daemon. */
	@Column
	long threadDaemon;
	
	/** The thread total started. */
	@Column
    long threadTotalStarted;
	
	/** The threads. */
	@Column
    long threads;
	
}
