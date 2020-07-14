package io.kryptoblocks.msa.infra.repository.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new infra data enrichment key.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new infra data enrichment key.
 *
 * @param client the client
 * @param dataCenter the data center
 * @param rountingIdentifier the rounting identifier
 * @param enricherType the enricher type
 * @param timeStamp the time stamp
 */
@AllArgsConstructor
@Embeddable
public class InfraDataEnrichmentKey implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The client. */
	@Column
	String client;
	
	/** The data center. */
	@Column
	String dataCenter;
	
	/** The rounting identifier. */
	@Column
	String rountingIdentifier;
	
	/** The enricher type. */
	@Column
	String enricherType;
	
	/** The time stamp. */
	@Column
	String timeStamp;
	
}
