/**
 * 
 */
package io.kryptoblocks.msa.user.service.ui.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import io.kryptoblocks.msa.user.service.model.CustomerDLUpdateInput;

/**
 * @author Anoop Manghat
 * @since Mar 29, 2018
 *
 */

public class UserDLValidator implements Validator {
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerDLUpdateInput.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors err) {
		
		ValidationUtils.rejectIfEmpty(err, "email", "login.email.empty");
		ValidationUtils.rejectIfEmpty(err, "country", "dl.country.empty");
		ValidationUtils.rejectIfEmpty(err, "state", "dl.state.empty");
		ValidationUtils.rejectIfEmpty(err, "issueDate", "dl.issueDate.empty");
		ValidationUtils.rejectIfEmpty(err, "expiryDate", "dl.expiryDate.empty");
	
	}

}
