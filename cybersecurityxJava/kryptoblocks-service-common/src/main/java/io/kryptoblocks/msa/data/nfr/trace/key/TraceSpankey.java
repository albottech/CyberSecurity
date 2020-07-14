package io.kryptoblocks.msa.data.nfr.trace.key;

 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new trace spankey.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new trace spankey.
 *
 * @param traceSpanId the trace span id
 * @param id the id
 */
@AllArgsConstructor
@Embeddable
public class TraceSpankey implements  Serializable  {
	  
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trace span id. */
	@Column 	
	long traceSpanId;	
	 
	/** The id. */
	@Column	
	String id;
	 
}
