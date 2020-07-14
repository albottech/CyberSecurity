/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anoop Manghat
 * @since May 19, 2018
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDLUpdateInput {
	String email;
	String nameOnCard;
	String number;
	String country;
	String state;
	String issueDate;
	String expiryDate;
	String city;
	String addressLine1;
	String addressLine2;
	String zipCode;
	boolean addressVerified;
	boolean active;
	
}
