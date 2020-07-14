package io.kryptoblocks.msa.user.repository.udt;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String addressLine1;
	String addressLine2;
	String country;
	String state;
	String county;
	String zipCode;
	boolean active;

}
