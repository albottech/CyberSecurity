package io.kryptoblocks.msa.data.nfr.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new coordinator key.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new coordinator key.
 *
 * @param name the name
 * @param id the id
 */
@AllArgsConstructor
@Embeddable
public class CoordinatorKey implements  Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@Column
	String name;		
	 
	/** The id. */
	@Column
	String id;

}
