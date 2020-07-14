package io.kryptoblocks.msa.infra.repository.udt;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new address.
 */
@NoArgsConstructor

/**
 * Instantiates a new address.
 *
 * @param addressLine1 the address line 1
 * @param addressLine2 the address line 2
 * @param country the country
 * @param state the state
 * @param county the county
 * @param zipCode the zip code
 * @param active the active
 */
@AllArgsConstructor
@Embeddable
public class Address implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The address line 1. */
	String addressLine1;
	
	/** The address line 2. */
	String addressLine2;
	
	/** The country. */
	String country;
	
	/** The state. */
	String state;
	
	/** The county. */
	String county;
	
	/** The zip code. */
	String zipCode;
	
	/** The active. */
	boolean active;

}
