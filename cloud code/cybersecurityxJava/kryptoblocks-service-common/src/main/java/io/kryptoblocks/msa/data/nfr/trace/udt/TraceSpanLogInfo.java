package io.kryptoblocks.msa.data.nfr.trace.udt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class TraceSpanLogInfo.
 */
@Embeddable

/**
 * Instantiates a new trace span log info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new trace span log info.
 *
 * @param timestamp the timestamp
 * @param event the event
 */
@AllArgsConstructor	
public class TraceSpanLogInfo implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The timestamp. */
	@Column
	long timestamp;
	
	/** The event. */
	@Column
	String event;

}
