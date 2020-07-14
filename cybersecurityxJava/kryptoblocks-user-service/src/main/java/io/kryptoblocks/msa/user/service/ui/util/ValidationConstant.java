/**
 * 
 */
package io.kryptoblocks.msa.user.service.ui.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Anoop Manghat
 * @since Mar 31, 2018
 *
 */
public interface ValidationConstant {
	
	static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	static List<String> ALLOWED_CARDS = Arrays.asList(new String[]{"MASTERCARD", "VISA", "AMEX", "DISCOVER"}); 
}
