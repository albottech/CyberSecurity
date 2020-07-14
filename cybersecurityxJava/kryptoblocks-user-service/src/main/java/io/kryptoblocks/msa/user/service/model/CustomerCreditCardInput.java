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
public class CustomerCreditCardInput {
	
	String nameOnCard;
	String email;
	String creditCardNumber;
	String creditCardType;
	String expiryDate;
	String verificationCode;
	boolean active;
	String addressLine1;
	String addressLine2;
	String country;
	String state;
	String city;
	String zipCode;
	boolean addressVerified;

}
