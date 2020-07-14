package io.kryptoblocks.msa.security.service.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceRequestToken;
import org.springframework.security.kerberos.authentication.KerberosTicketValidation;
import org.springframework.security.kerberos.authentication.KerberosTicketValidator;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.model.AuthenticationResult;
import io.kryptoblocks.msa.common.model.AuthorizationVerificationResult;

import io.jsonwebtoken.Claims;
import io.kryptoblocks.msa.security.service.common.AuthorityName;
import io.kryptoblocks.msa.security.service.kerberos.Krb5UserDetailsService;
import io.kryptoblocks.msa.security.service.message.SecurityMessage;
import io.kryptoblocks.msa.security.service.model.Authority;
import io.kryptoblocks.msa.security.service.model.LoginUser;
import io.kryptoblocks.msa.security.service.model.User;
import io.kryptoblocks.msa.security.service.util.AuthenticationProviderType;
import io.kryptoblocks.msa.security.service.util.JwtTokenUtil;
 

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationService.
 */
public class AuthenticationService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
	
	/** The ad authentication provider. */
	@Autowired
	ActiveDirectoryLdapAuthenticationProvider adAuthenticationProvider;
	
	/** The kerberos service authentication provider. */
	@Autowired
	KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider;
	
	/** The user details service. */
	@Resource(name="userDetailsService")
	UserDetailsService userDetailsService;
	
	/** The jwt token util. */
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	/** The kerberos ticket validator. */
	@Autowired
	KerberosTicketValidator kerberosTicketValidator;
	
	/** The sun jaas kerberos ticket validator. */
	@Autowired
	SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator;
	
	/** The krb 5 user details service. */
	@Autowired
	Krb5UserDetailsService krb5UserDetailsService;
	
	/** The user details checker. */
	private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
	
	/**
	 * User name password authentication.
	 *
	 * @param loginUser the login user
	 * @param device the device
	 * @return the authentication result
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unused")
	public AuthenticationResult userNamePasswordAuthentication(LoginUser loginUser, Device device) throws BusinessException {
		AuthenticationResult returnValue = new AuthenticationResult();
		
		try {
			final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
					loginUser.getUsername(), loginUser.getPassword());
			
			String searchFilter = "(&(objectClass=user)(CN="+ loginUser.getUsername()+"))";
			adAuthenticationProvider.setSearchFilter(searchFilter);
			Authentication ldapAuthentication = adAuthenticationProvider.authenticate(loginToken);
			
			
			if (ldapAuthentication != null) {
				
				LdapUserDetails ldapUserDetails = (LdapUserDetails)ldapAuthentication.getPrincipal();
				LOGGER.debug("Authenticated user AD DN : {}", ldapUserDetails.getDn());
				
				Collection<? extends GrantedAuthority> ldapAuthority = ldapUserDetails.getAuthorities();
				int authCount = 0;
				for(GrantedAuthority curAuthority: ldapAuthority) {
					++authCount;
					LOGGER.debug("Authenticated user AD authority" + authCount + ": {}", curAuthority.getAuthority());
				}
				
				User user = userDetailsService.loadUserByUsername(ldapAuthentication.getName());
				if(user == null){
					List<String> defaultUserServiceAuthority = new ArrayList<String>();
					//TODO 
					//add all real externally managed service authority
					defaultUserServiceAuthority.add(AuthorityName.SERVICE_OP_RD.getValue());
					 
					user = userDetailsService.createUserFromAuthenticationData(loginUser.getUsername(),ldapAuthentication, AuthenticationProviderType.LDAP.getValue(), defaultUserServiceAuthority); 
				}
				user = userDetailsService.updadeUserWithTransientData(user, ldapAuthentication, AuthenticationProviderType.LDAP.getValue());
				
				LOGGER.debug("Authenticated user name : {}", user.getUsername());
				LOGGER.debug("Authenticated user account enabled status : {}", user.isAccountEnabled());
				LOGGER.debug("Authenticated user account non expired syatus : {}", user.isAccountNonExpired());
				LOGGER.debug("Authenticated user account non locked : {}", user.accountNonLocked);
				LOGGER.debug("Authenticated user account credential non expired status: {}", user.isCredentialNonExpired());
				
				Collection<Authority> dbAuthority = user.getAuthorities();
				int dbAuthorityCount = 0;
				for(Authority curDbAuthority: dbAuthority) {
					++dbAuthorityCount;
					LOGGER.debug("Authenticated user DB managed authority" + dbAuthorityCount + ": {}", curDbAuthority.getAuthorityname());
				}
				if(user != null) {
					List<Authority> finalUserAuthority = user.getAuthorities();
					 for(GrantedAuthority curLdapAuthority: ldapAuthority) {
						 Authority curUserAuthority = new Authority();
						 curUserAuthority.setAuthorityname(curLdapAuthority.getAuthority());	
						 finalUserAuthority.add(curUserAuthority);
					} 
					user.setAuthorities(finalUserAuthority);
					 
					String jwtToken = jwtTokenUtil.generateToken(user, device);
					if(jwtToken != null) {
						returnValue.setToken(jwtToken);
						returnValue.setUserName(user.getUsername());
						returnValue.setMsg(SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE);
					}else {
						returnValue.setUserName(user.getUsername());
						returnValue.setMsg(SecurityMessage.TOKEN_NOT_CREATED_AUTHENTICATION_RESPONSE_MESSAGE);							
					}
					
				}else {
					returnValue.setMsg(SecurityMessage.UNABLE_TO_FIND_USER_FROM_AUTH_DATA_STOR_AUTHENTICATION_RESPONSE_MESSAGE);					
				}
				
			}else {
				returnValue.setMsg(SecurityMessage.UNABLE_TO_AUTHENTICATE_USERNAME_PASSWORD_AUTHENTICATION_RESPONSE_MESSAGE);
			}
			
			
			
		}catch(Exception e) {
			LOGGER.debug("Exception in authentication service username password authentication method: {}", ExceptionUtils.getStackTrace(e));
			throw new BusinessException(ExceptionUtils.getStackTrace(e), e);
			
		}
		//just for testing
		//Exception e = new Exception("testing business exception");		
		//throw new BusinessException(ExceptionUtils.getStackTrace(e), e);
		
		return returnValue;
	}
	
	
	
	/**
	 * Kerberos ticket authentication.
	 *
	 * @param request the request
	 * @param token the token
	 * @param device the device
	 * @return the authentication result
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unused")
	public AuthenticationResult kerberosTicketAuthentication(HttpServletRequest request, String token, Device device ) throws BusinessException {

			AuthenticationResult returnValue = new AuthenticationResult();
			LdapUserDetails ldapUserDetails = null;
			Authentication ldapAuthentication = null;
			String ticketUserName = null;
			//GSSException
		 
			LOGGER.debug("Received negotiate token " + ": " + token);
			try {
				byte[] base64Token = token.substring(token.indexOf(" ") + 1).getBytes("UTF-8");
				byte[] kerberosTicket = Base64.decode(base64Token);
				KerberosServiceRequestToken kerberosServiceRequestToken = new KerberosServiceRequestToken(kerberosTicket);
				AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
				kerberosServiceRequestToken.setDetails(authenticationDetailsSource.buildDetails(request));				
				byte[] finalToken = kerberosServiceRequestToken.getToken();
				LOGGER.debug("try to validate kerberos token");
				KerberosTicketValidation ticketValidation = sunJaasKerberosTicketValidator.validateTicket(finalToken);
				ticketUserName = ticketValidation.username();
				LOGGER.debug("succesfully validated user : {}", ticketUserName);
				returnValue.setUserName(ticketUserName);
				UserDetails userDetails = krb5UserDetailsService.loadUserByUsername(ticketValidation.username());
				userDetailsChecker.check(userDetails);				
				KerberosServiceRequestToken responseAuth = new KerberosServiceRequestToken(
						userDetails, ticketValidation,
						userDetails.getAuthorities(), finalToken);
				responseAuth.setDetails(kerberosServiceRequestToken.getDetails());
				ldapAuthentication = responseAuth;
				
				if (ldapAuthentication != null) {					
					ldapUserDetails = (LdapUserDetails)ldapAuthentication.getPrincipal();
					LOGGER.debug("authenticated user AD DN : {}", ldapUserDetails.getDn());
					
					Collection<? extends GrantedAuthority> ldapAuthority = ldapUserDetails.getAuthorities();
					int authCount = 0;
					for(GrantedAuthority curAuthority: ldapAuthority) {
						++authCount;
						LOGGER.debug("authenticated user AD authority: count : {}, authority: {}",  authCount, curAuthority.getAuthority());
					}
					
					LOGGER.debug("authenticated user ldap user name : {}", ldapAuthentication.getName());
					
					User user = null;
					
					if(ldapAuthentication.getName() != null) {
						user = userDetailsService.loadUserByUsername(ldapAuthentication.getName());
					}else {
						user = userDetailsService.loadUserByUsername(ticketUserName);
					}
					
					if(user == null){
						List<String> defaultUserServiceAuthority = new ArrayList<String>();
						defaultUserServiceAuthority.add(AuthorityName.SERVICE_OP_RD.getValue());						
						user = userDetailsService.createUserFromAuthenticationData(ticketUserName, ldapAuthentication, AuthenticationProviderType.LDAP.getValue(), defaultUserServiceAuthority); 
						returnValue.setUserName(user.getUsername());
					}
					user = userDetailsService.updadeUserWithTransientData(user, ldapAuthentication, AuthenticationProviderType.LDAP.getValue());
					
					LOGGER.debug("Authenticated user name : {}", user.getUsername());					
					LOGGER.debug("Authenticated user account enabled status : {}", user.isAccountEnabled());
					LOGGER.debug("Authenticated user account non expired syatus : {}", user.isAccountNonExpired());
					LOGGER.debug("Authenticated user account non locked : {}", user.accountNonLocked);
					LOGGER.debug("Authenticated user account credential non expired status: {}", user.isCredentialNonExpired());
					
					Collection<Authority> dbAuthority = user.getAuthorities();
					int dbAuthorityCount = 0;
					for(Authority curDbAuthority: dbAuthority) {
						++dbAuthorityCount;
						LOGGER.debug("Authenticated user DB managed authority" + dbAuthorityCount + ": {}", curDbAuthority.getAuthorityname());
					}
					if(user != null) {
						List<Authority> finalUserAuthority = user.getAuthorities();
						 for(GrantedAuthority curLdapAuthority: ldapAuthority) {
							 Authority curUserAuthority = new Authority();
							 curUserAuthority.setAuthorityname(curLdapAuthority.getAuthority());	
							 finalUserAuthority.add(curUserAuthority);
						} 
						user.setAuthorities(finalUserAuthority);
						String jwtToken = jwtTokenUtil.generateToken(user, device);
						if(jwtToken != null) {
							returnValue.setToken(jwtToken);
							returnValue.setUserName(user.getUsername());
							returnValue.setMsg(SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE);
						}else {
							returnValue.setUserName(user.getUsername());
							returnValue.setMsg(SecurityMessage.TOKEN_NOT_CREATED_AUTHENTICATION_RESPONSE_MESSAGE);						
						}
						
					}else {
						returnValue.setMsg(SecurityMessage.UNABLE_TO_FIND_USER_FROM_AUTH_DATA_STOR_AUTHENTICATION_RESPONSE_MESSAGE);						
					}
					
				}else {
					returnValue.setMsg(SecurityMessage.UNABLE_TO_AUTHENTICATE_KRB_TICKET_AUTHENTICATION_RESPONSE_MESSAGE);
				}
			} catch (Exception e) {
				if(ldapUserDetails != null) {
					//do nothing
					LOGGER.debug("bypassing exception in authentication service kerberosTicketAuthentication method: {}", ExceptionUtils.getStackTrace(e));
				}else {
					LOGGER.debug("Exception in authentication service kerberosTicketAuthentication method: {}", ExceptionUtils.getStackTrace(e));
					throw new BusinessException(ExceptionUtils.getStackTrace(e), e);	
				}
			}
			
		return returnValue;

	}
	
	
	/**
	 * Jwt token authentication.
	 *
	 * @param jwtAuthToken the jwt auth token
	 * @return the authentication result
	 * @throws BusinessException the business exception
	 */
	@SuppressWarnings("unused")
	public AuthenticationResult jwtTokenAuthentication(String jwtAuthToken ) throws BusinessException {

		AuthenticationResult returnValue = new AuthenticationResult();	
		
			LOGGER.debug("received jwt token: {}", jwtAuthToken);
			
			try {
				Claims tokenClaims = jwtTokenUtil.getClaimsFromToken(jwtAuthToken);
				String userNameFromToken = (String) tokenClaims.get(JwtTokenUtil.CLAIM_KEY_USERNAME);
				User user = userDetailsService.loadUserByUsername(userNameFromToken);
				returnValue.setUserName(userNameFromToken);
				if (jwtTokenUtil.validateToken(jwtAuthToken, user)) {
					returnValue.setToken(jwtAuthToken);	
					returnValue.setMsg(SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE);
				}else {					
					returnValue.setMsg(SecurityMessage.INVALID_JWT_TOKEN_AUTHENTICATION_RESPONSE_MESSAGE);	
				}
			} catch (Exception e) {
				LOGGER.debug("Exception in authentication service jwt token authentication method: {}", ExceptionUtils.getStackTrace(e));
				throw new BusinessException(ExceptionUtils.getStackTrace(e), e);
			}
			
		return returnValue;

	}
	
	/**
	 * Derive authorization.
	 *
	 * @param jwtToken the jwt token
	 * @return the authorization verification result
	 * @throws BusinessException the business exception
	 */
	public AuthorizationVerificationResult deriveAuthorization(String jwtToken) throws BusinessException {
		
		AuthorizationVerificationResult returnValue = new AuthorizationVerificationResult();
		
		try {
			LOGGER.debug("received jwt token : {}", jwtToken);
			Claims tokenClaims = jwtTokenUtil.getClaimsFromToken(jwtToken);
			if(!jwtTokenUtil.isTokenExpired(jwtToken)) {
			String authGrpAsString = (String) tokenClaims.get(JwtTokenUtil.CLAIM_DATA_AUTHORIZATION_GRP_LIST);
			returnValue.setUserName((String)tokenClaims.get(JwtTokenUtil.CLAIM_KEY_USERNAME));
			if(authGrpAsString != null) {
				String finalAuthorityAsString = null;
				LOGGER.debug("jwt token authority claim: {}", authGrpAsString);
				if(authGrpAsString.endsWith(",")) {
					finalAuthorityAsString = StringUtils.chop(authGrpAsString).toUpperCase();
				}else {
					finalAuthorityAsString = authGrpAsString.toUpperCase();
				}
				StringUtils.chop(authGrpAsString);
				LOGGER.debug("authority as list : {}", finalAuthorityAsString);
				returnValue.setAuthorizationList(finalAuthorityAsString);
				returnValue.setMsg(SecurityMessage.AUTH_VERIFICATION_SUCCESS_RESPONSE_MESSAGE);
			}
			}else {
				returnValue.setAuthorizationList(null);
				returnValue.setMsg(SecurityMessage.AUTH_VERIFICATION_INVALID_TOKEN_SUCCESS_RESPONSE_MESSAGE);
			}
			
		}catch(Exception e) {
			LOGGER.debug("exception in authentication service derive authorization method: {}", ExceptionUtils.getStackTrace(e));
			throw new BusinessException(ExceptionUtils.getStackTrace(e), e);			
		}
		return returnValue;
	}
	
	 
	
	

}
