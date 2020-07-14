package io.kryptoblocks.msa.security.service.kerberos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.kryptoblocks.msa.security.service.exception.SecurityConfigurationException;

// TODO: Auto-generated Javadoc
/**
 * The Class Krb5UserDetailsService.
 */
public class Krb5UserDetailsService implements UserDetailsService {

	/** The krb 5 util. */
	@Autowired
	Krb5Util krb5Util;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails returnValue = null;
		try {
			returnValue = krb5Util.getUserDetails(username);
			
		} catch (SecurityConfigurationException se) {
			throw new UsernameNotFoundException(se.getMessage());
		}
		return returnValue;
	}

}
