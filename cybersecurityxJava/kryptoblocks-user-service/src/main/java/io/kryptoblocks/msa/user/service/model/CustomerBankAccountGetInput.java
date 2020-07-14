/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;

 

import lombok.Data;

 
@Data
public class CustomerBankAccountGetInput {
	String email;	 
	String bankName;	 
	String accountNumber;	 
	String routingNumber;
	 
}
