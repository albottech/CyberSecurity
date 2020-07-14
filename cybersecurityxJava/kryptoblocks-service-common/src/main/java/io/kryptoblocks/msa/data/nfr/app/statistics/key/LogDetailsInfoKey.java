package io.kryptoblocks.msa.data.nfr.app.statistics.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new log details info key.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new log details info key.
 *
 * @param name the name
 * @param id the id
 */
@AllArgsConstructor
@Embeddable
public class LogDetailsInfoKey implements  Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@Column 
	String name;		
	 
	/** The id. */
	@Column 	
	String id;

}
