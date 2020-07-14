package io.kryptoblocks.msa.data.nfr.infra.container.statistics.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new perf info key.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new perf info key.
 *
 * @param serviceName the service name
 * @param id the id
 * @param destination the destination
 */
@AllArgsConstructor
@Embeddable
public class PerfInfoKey implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The service name. */
	@Column 
	String serviceName;		
	 
	/** The id. */
	@Column 
	String id;
	
	/** The destination. */
	@Column 
	String destination;


}
