package io.kryptoblocks.msa.data.repository.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new data data SOR key.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new data data SOR key.
 *
 * @param client the client
 * @param dataCenter the data center
 * @param rountingIdentifier the rounting identifier
 * @param SORType the SOR type
 * @param timeStamp the time stamp
 */
@AllArgsConstructor
@Embeddable
public class DataDataSORKey implements Serializable{
	
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
	
	/** The SOR type. */
	@Column
	String SORType;
	
	/** The time stamp. */
	@Column
	String timeStamp;
	
}
