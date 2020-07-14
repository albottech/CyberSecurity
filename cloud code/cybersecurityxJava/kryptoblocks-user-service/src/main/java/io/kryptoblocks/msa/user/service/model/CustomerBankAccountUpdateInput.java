/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;

 

import lombok.Data;

/**
 * @author Anoop Manghat
 * @since May 20, 2018
 *
 */
@Data
public class CustomerBankAccountUpdateInput {
	String email;
	boolean ibanEnabled;
	String bankName;	 
	String accountNumber;
	String bicOrSwift;
	String routingNumber;
	String ifscCode;
	String micrCode;
	String addressLine1;
	String addressLine2;
	String state;
	String country;
	String zipCode;
	boolean active;
}
