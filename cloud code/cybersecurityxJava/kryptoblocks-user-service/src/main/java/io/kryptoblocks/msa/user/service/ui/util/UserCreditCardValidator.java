/**
 * 
 */
package io.kryptoblocks.msa.user.service.ui.util;

import org.apache.commons.validator.routines.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;

import io.kryptoblocks.msa.user.service.model.CustomerCreditCardInput;

/**
 * @author Anoop Manghat
 * @since Apr 24, 2018
 *
 */
 
public class UserCreditCardValidator implements Validator {

	@Autowired
	private CreditCardValidator creditCardValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerCreditCardInput.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors err) {
		CustomerCreditCardInput userCreditCard = (CustomerCreditCardInput) target;

		ValidationUtils.rejectIfEmpty(err, "email", "login.email.empty");
		ValidationUtils.rejectIfEmpty(err, "creditCardNumber", "creditcard.empty");
		ValidationUtils.rejectIfEmpty(err, "creditCardType", "creditcard.type.empty");
		ValidationUtils.rejectIfEmpty(err, "expiryDate", "creditcard.expiryDate.empty");
		ValidationUtils.rejectIfEmpty(err, "verificationCode", "creditcard.verificationCode.empty");
		ValidationUtils.rejectIfEmpty(err, "addressLine1", "creditcard.addressLine1.empty");
		ValidationUtils.rejectIfEmpty(err, "country", "creditcard.country.empty");
		ValidationUtils.rejectIfEmpty(err, "state", "creditcard.state.empty");
		ValidationUtils.rejectIfEmpty(err, "zipCode", "creditcard.zipCode.empty");

		if (userCreditCard.getEmail() != null
				&& !(ValidationConstant.EMAIL_PATTERN.matcher(userCreditCard.getEmail()).matches())) {
			err.rejectValue("email", "login.email.invalid");

		}

		if (userCreditCard.getCreditCardNumber() != null
				&& !creditCardValidator.isValid(userCreditCard.getCreditCardNumber())) {
			err.rejectValue("creditCardNumber", "creditcard.invalid");
		}

		if (userCreditCard.getCreditCardType() != null
				&& !ValidationConstant.ALLOWED_CARDS.contains(userCreditCard.getCreditCardType())) {
			err.rejectValue("creditCardType", "creditcard.creditCardType.invalid");
		}

		/*
		 * Expiry Date a non-capturing group of: 0 followed by 1-9, or 1
		 * followed by 0-2
		 * 
		 * followed by "/" followed by 0-9, twice.
		 * 
		 * so this version requires zero-padded months (01 - 12). Add a ? after
		 * the first 0 to prevent this.
		 */
		if (userCreditCard.getExpiryDate() != null
				&& !userCreditCard.getExpiryDate().matches("(?:0[1-9]|1[0-2])/[0-9]{2}")) {
			err.rejectValue("expiryDate", "creditcard.expiryDate.invalid");
		}
	}

}
