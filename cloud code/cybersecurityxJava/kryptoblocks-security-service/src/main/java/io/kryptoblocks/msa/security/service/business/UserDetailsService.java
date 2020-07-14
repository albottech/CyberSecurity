package io.kryptoblocks.msa.security.service.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;

import io.kryptoblocks.msa.common.exception.BusinessException;

import io.kryptoblocks.msa.security.service.common.AuthorityName;
import io.kryptoblocks.msa.security.service.model.Authority;
import io.kryptoblocks.msa.security.service.model.User;
import io.kryptoblocks.msa.security.service.repository.AuthorityRepository;
import io.kryptoblocks.msa.security.service.repository.UserRepository;
import io.kryptoblocks.msa.security.service.util.AuthenticationProviderType;

 
// TODO: Auto-generated Javadoc
/**
 * The Class UserDetailsService.
 */
@Service("userDetailsService")
public class UserDetailsService {

    /** The user repository. */
    @Autowired
    private UserRepository userRepository;
    
    /** The authority repository. */
    @Autowired
    private AuthorityRepository authorityRepository;
        
    /** The service name. */
    @Value("${spring.application.name}")	
	private String serviceName;
	
    

   
    /**
     * Load user by username.
     *
     * @param username the username
     * @return the user
     * @throws BusinessException the business exception
     */
    public User loadUserByUsername(String username) throws BusinessException {
    	try {
    	return userRepository.findByUsername(username);
    	}catch(Exception e) {
    		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();    		
    		throw formatServiceException(methodName, e);    		
    	}
         
    }
    
	/**
	 * Updade user with transient data.
	 *
	 * @param user the user
	 * @param authentication the authentication
	 * @param authenticationProviderName the authentication provider name
	 * @return the user
	 * @throws BusinessException the business exception
	 */
	public User updadeUserWithTransientData(User user, Authentication authentication, String authenticationProviderName)
			throws BusinessException {
		try {
			AuthenticationProviderType authenticationProviderType = AuthenticationProviderType
					.findByValue(authenticationProviderName);
			switch (authenticationProviderType) {
			case AUTH2:
				break;
			case LDAP:
				LdapUserDetails ldapUserDetails = (LdapUserDetails) authentication.getPrincipal();
				user.setAccountNonExpired(ldapUserDetails.isAccountNonExpired());
				user.setAccountNonLocked(ldapUserDetails.isAccountNonLocked());
				user.setCredentialNonExpired(ldapUserDetails.isCredentialsNonExpired());
				user.setAccountEnabled(ldapUserDetails.isEnabled());
				break;
			case DB:
				break;
			default:
				break;
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			throw formatServiceException(methodName, e);
		}

		return user;
	}

    /**
     * Creates the user from authentication data.
     *
     * @param userName the user name
     * @param authentication the authentication
     * @param authenticationProviderName the authentication provider name
     * @param authorityName the authority name
     * @return the user
     */
    public User createUserFromAuthenticationData(String userName, Authentication authentication, String authenticationProviderName, List<String> authorityName) {
    	User user = new User();
    	//check and create a list of user authority list
    	List<Authority> userAuthority = new ArrayList<Authority>();
    	
    	for(String curAuthorityName: authorityName) {
    		Authority curAuthority = authorityRepository.findByAuthorityname(curAuthorityName);
    		if(curAuthority == null) {
    			curAuthority = new Authority();
    			curAuthority.setAuthorityname(AuthorityName.findByValue(curAuthorityName).getValue());	    			
    		}
    		userAuthority.add(curAuthority);    		
    	}
    	AuthenticationProviderType authenticationProviderType = AuthenticationProviderType.findByValue(authenticationProviderName);
    	switch(authenticationProviderType) {
    	case AUTH2:
    		break;
    	case LDAP:
    		LdapUserDetails ldapUserDetails = (LdapUserDetails)authentication.getPrincipal();
    		user.setUsername(userName);
        	user.setAccountNonExpired(ldapUserDetails.isAccountNonExpired());
        	user.setAccountNonLocked(ldapUserDetails.isAccountNonLocked());
        	user.setCredentialNonExpired(ldapUserDetails.isCredentialsNonExpired());
        	user.setAccountEnabled(ldapUserDetails.isEnabled());
        	userRepository.save(user);        	
    		break;
    	case DB:
    		break;
    	default :
    		break;
    	}
    	authorityRepository.save(userAuthority);
    	user.setAuthorities(userAuthority);
    	userRepository.save(user);
    	return user; 
    }
    
    
    /**
     * Format service exception.
     *
     * @param methodName the method name
     * @param exception the exception
     * @return the business exception
     */
    private BusinessException formatServiceException(String methodName, Exception exception) {
    	String msg = "Exception at " + methodName + " method";    
    	BusinessException businessException = new BusinessException(msg, exception);
		return businessException;
    	
    }
    
}
