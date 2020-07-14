package io.kryptoblocks.msa.user.repository.udt;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Embeddable
public class CreditCardAddress implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String addressLine1;
	String addressLine2;
	String city;
	String state;
	String zipCode;
	String country;
	boolean verified;

}
