/**
 * 
 */
package io.kryptoblocks.msa.user.service.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Anoop Manghat
 * @since Mar 31, 2018
 *
 */
public final class UserServiceUtils {

	/**
	 * This method will generate unique code based on emailID
	 * 
	 * @param emailID
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateEncodedKey(String emailID) throws NoSuchAlgorithmException {
		String generatedKey = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(emailID.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedKey = sb.toString().substring(0, 8);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		return generatedKey;
	}

	public static String genRandom() {
		int length = 7;
		boolean useLetters = true;
		boolean useNumbers = false;
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}

	public static String getCreditCardTypeByNumber(String creditCardNumber) {

		String regVisa = "^4[0-9]{12}(?:[0-9]{3})?$";
		String regMaster = "^5[1-5][0-9]{14}$";
		String regExpress = "^3[47][0-9]{13}$";
		String regDiners = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
		String regDiscover = "^6(?:011|5[0-9]{2})[0-9]{12}$";
		String regJCB = "^(?:2131|1800|35\\d{3})\\d{11}$";

		if (creditCardNumber.matches(regVisa))
			return "visa";
		if (creditCardNumber.matches(regMaster))
			return "mastercard";
		if (creditCardNumber.matches(regExpress))
			return "amex";
		if (creditCardNumber.matches(regDiners))
			return "DINERS";
		if (creditCardNumber.matches(regDiscover))
			return "discover";
		if (creditCardNumber.matches(regJCB))
			return "jcb";
		return "invalid";
	}

}
