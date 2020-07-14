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
 * The Class Memory.
 */
@SuppressWarnings("unused")
@Embeddable

/**
 * Instantiates a new memory.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new memory.
 *
 * @param heapInit the heap init
 * @param heapUsed the heap used
 * @param heapCommitted the heap committed
 * @param heapMax the heap max
 * @param nonHeapInit the non heap init
 * @param nonHeapUsed the non heap used
 * @param nonHeapCommitted the non heap committed
 * @param nonHeapMax the non heap max
 */
@AllArgsConstructor
public class Memory implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	 	
	/** The heap init. */
	@Column
	long heapInit;
	
	/** The heap used. */
	@Column
	long heapUsed;
	
	/** The heap committed. */
	@Column
    long heapCommitted;
	
	/** The heap max. */
	@Column
    long heapMax;
	
	/** The non heap init. */
	@Column
	long nonHeapInit;
	
	/** The non heap used. */
	@Column
	long nonHeapUsed;
	
	/** The non heap committed. */
	@Column
    long nonHeapCommitted;
	
	/** The non heap max. */
	@Column
    long nonHeapMax;

}
