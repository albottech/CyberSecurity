/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anoop Manghat
 * @since Apr 24, 2018
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditCardDeleteInput {
	
	 
	String email;
	String creditCardNumber;
	 

}
