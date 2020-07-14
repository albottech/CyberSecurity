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
public class CustomerBankAccountDeleteInput {
	String email;	 
	String bankName;	 
	String accountNumber;	 
	String routingNumber;
	 
}
