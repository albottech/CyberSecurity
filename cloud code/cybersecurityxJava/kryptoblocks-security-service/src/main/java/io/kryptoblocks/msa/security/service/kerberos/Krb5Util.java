package io.kryptoblocks.msa.security.service.kerberos;

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.util.StringUtils;

import io.kryptoblocks.msa.common.util.StringEncryptionUtil;

import io.kryptoblocks.msa.security.service.exception.SecurityConfigurationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class Krb5Util.
 */
public class Krb5Util {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Krb5Util.class);

	/** The krb 5 jaas config. */
	@Value("${krb5.jaas.config}")
	private String krb5JaasConfig;

	/** The krb 5 realm. */
	@Value("${krb5.realm}")
	private String krb5Realm;

	/** The krb 5 kdc host. */
	@Value("${krb5.kdc}")
	private String krb5KdcHost;

	/** The krb 5 debug. */
	@Value("${krb5.debug}")
	private String krb5Debug;

	/** The krb 5 authentication method. */
	@Value("${krb5.authentication.method}")
	private String krb5AuthenticationMethod;

	/** The simple authentication method. */
	@Value("${simple.authentication.method}")
	private String simpleAuthenticationMethod;
	
	/** The simple authentication username. */
	@Value("${simple.authentication.username}")
	private String simpleAuthenticationUsername;
	
	/** The simple authentication password. */
	@Value("${simple.authentication.password}")
	private String simpleAuthenticationPassword;

	/** The ad server. */
	@Value("${ad.server}")
	private String adServer;

	/** The ad root DN. */
	@Value("${ad.root.dn}")
	private String adRootDN;

	/**
	 * Gets the user subject.
	 *
	 * @return the user subject
	 */
	@Getter
	
	/**
	 * Sets the user subject.
	 *
	 * @param userSubject the new user subject
	 */
	@Setter
	private Subject userSubject;

	/**
	 * Gets the key tab subject.
	 *
	 * @return the key tab subject
	 */
	@Getter
	
	/**
	 * Sets the key tab subject.
	 *
	 * @param keyTabSubject the new key tab subject
	 */
	@Setter
	private Subject keyTabSubject;
	
	/** The string encryption util. */
	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	/** The authorities mapper. */
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	/** The user details context mapper. */
	protected UserDetailsContextMapper userDetailsContextMapper = new LdapUserDetailsMapper();

	/**
	 * Sets the system properties.
	 *
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	public void setSystemProperties() throws SecurityConfigurationException {
		try {

			LOGGER.debug("initializing krb5 system properties: ");
			LOGGER.debug("java.security.auth.login.config : {}", krb5JaasConfig);			
			Resource jaasConfResource = new ClassPathResource(krb5JaasConfig);			
			System.setProperty("java.security.auth.login.config", jaasConfResource.getURL().toString());
			System.setProperty("java.security.krb5.realm", krb5Realm);
			LOGGER.debug("java.security.krb5.realm : {}", krb5Realm);
			System.setProperty("java.security.krb5.kdc", krb5KdcHost);
			LOGGER.debug("java.security.krb5.kdc : {}", krb5KdcHost);
			System.setProperty("sun.security.krb5.debug", krb5Debug);
			LOGGER.debug("sun.security.krb5.debug : {}", krb5Debug);
		} catch (Exception e) {
			LOGGER.debug(
					"exception in set security system properties method:, throwing back the security config exception");
			throw new SecurityConfigurationException(e);

		}
	}

	/**
	 * Verify simple mode search config.
	 *
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	public void verifySimpleModeSearchConfig() throws SecurityConfigurationException {
		try {
			LOGGER.debug("verifying simple LDAP search setup : ");
			LOGGER.debug("setting up the service account for simple search: ");
			CallbackHandler callbackHandler = new CallbackHandler() {
				@Override
				public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
					for (Callback callback : callbacks) {
						if (callback instanceof NameCallback) {
							((NameCallback) callback).setName("simpleSearchServiceAccount");
						}
						if (callback instanceof PasswordCallback) {
							((PasswordCallback) callback).setPassword("syk1Tuev".toCharArray());
						}
					}

				}
			};

			LOGGER.debug("service account configuration for simple mode search is completed : ");
			LOGGER.debug("verifying account configuration for simple mode search : ");

			LoginContext userLoginContext = new LoginContext("simpleSearchServiceAccount", callbackHandler);
			LOGGER.debug("user context for simple mode search is created : ");
			userLoginContext.login();
			setUserSubject(userLoginContext.getSubject());
			Set<Principal> userprincipals = userSubject.getPrincipals();
			Set<Object> userCredentials = userSubject.getPrivateCredentials();
			LOGGER.debug("user context for simple mode search verification principal : {}", userprincipals);
			LOGGER.debug("user context for simple mode search verification credential : {}", userCredentials);
			LOGGER.debug("user context for simple mode search verification completed: ");
		} catch (Exception e) {
			LOGGER.debug(
					"exception in verify simple mode search config method:, throwing back the security config exception");
			throw new SecurityConfigurationException(e);
		}
	}

	/**
	 * Verify krb 5 config.
	 *
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	public void verifyKrb5Config() throws SecurityConfigurationException {

		try {
			LOGGER.debug("verifying krb5 LDAP search : ");
			LOGGER.debug("setting up the keytab account for GSAPI search: ");
			CallbackHandler callbackHandler = new CallbackHandler() {
				@Override
				public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
				}
			};

			LOGGER.debug("keytab account configuration for GSAPI mode search is completed : ");
			LOGGER.debug("verifying account configuration for GSAPI mode search : ");

			LoginContext keyTabUserContext = new LoginContext("glider", callbackHandler);

			LOGGER.debug("keytab verification statrt : ");
			keyTabUserContext.login();
			LOGGER.debug("user context for GSAPI mode search is created : ");
			setKeyTabSubject(keyTabUserContext.getSubject());
			Set<Principal> keyTabPprincipals = keyTabSubject.getPrincipals();
			Set<Object> keyTabCredentials = keyTabSubject.getPrivateCredentials();
			LOGGER.debug("user context for GSAPI mode search verification principal: {} : ", keyTabPprincipals);
			LOGGER.debug("user context for GSAPI mode search verification credential {} : ", keyTabCredentials);
			LOGGER.debug("user context for GSAPI mode search verification completed: ");
		} catch (Exception e) {
			LOGGER.debug("exception in verify krb5 config method:, throwing back the security config exception");
			throw new SecurityConfigurationException(e);
		}
	}

	/**
	 * Gets the LDAP context for GSAPI search.
	 *
	 * @return the LDAP context for GSAPI search
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	@SuppressWarnings("unchecked")
	public DirContext getLDAPContextForGSAPISearch() throws SecurityConfigurationException {

		DirContext returnContext = null;
		try {
			LOGGER.debug("creating an LDAP search context for GSAPI search: ");

			returnContext = (DirContext) Subject.doAs(getKeyTabSubject(),
					new LdapJndiActionUtil(krb5AuthenticationMethod, adServer, adRootDN));
			if (returnContext != null) {
				LOGGER.debug("successfully created LDAP search context for GSAPI search: ");
			} else {
				LOGGER.debug("created LDAP search context for GSAPI search is NULL: ");
			}
		} catch (Exception e) {
			LOGGER.debug(
					"exception in get LDAP context for GSAPI search method:, throwing back the security config exception");
			throw new SecurityConfigurationException(e);
		}
		return returnContext;
	}

	/**
	 * Gets the LDAP context for simple search.
	 *
	 * @return the LDAP context for simple search
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	@SuppressWarnings("unchecked")
	public DirContext getLDAPContextForSimpleSearch() throws SecurityConfigurationException {
		DirContext returnContext = null;
		try {
			LOGGER.debug("creating an LDAP search context for simple search: ");

			returnContext = (DirContext) Subject.doAs(getUserSubject(),
					new LdapJndiActionUtil(simpleAuthenticationMethod, adServer, adRootDN));
			if (returnContext != null) {
				LOGGER.debug("successfully created LDAP search context for simple search: ");
			} else {
				LOGGER.debug("created LDAP search context for simple search is NULL: ");
			}
		} catch (Exception e) {
			LOGGER.debug(
					"exception in get LDAP context for simple search method:, throwing back the security config exception");
			throw new SecurityConfigurationException(e);
		}
		return returnContext;
	}
	
	 
	  

	/**
	 * Gets the authentication data.
	 *
	 * @param userName the user name
	 * @return the authentication data
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	public Authentication getAuthenticationData( String userName) throws SecurityConfigurationException {
		Authentication returnData = null;
		try {
			LOGGER.debug("LDAP search verification for service user: ");
			
			DirContextOperations dirContextOperation = searchForUser(bindAsServiceUser(), userName);

			UserDetails userDetails = this.userDetailsContextMapper.mapUserFromContext(dirContextOperation, userName,
					loadUserAuthorities(dirContextOperation));

			returnData = createSuccessfulAuthentication(userDetails, userName);

		} catch (Exception e) {
			LOGGER.debug("exception in get authentication data method: reurning security configuration exception ");
			throw new SecurityConfigurationException(e);
		}
		if(returnData != null) {
			LOGGER.debug("get authentication data method verification for test user  {} was successful:", userName );
		}else {
			LOGGER.debug("get authentication data method verification for test user  {} was NOT successful:", userName );
		}
		return returnData;
	}
	
	/**
	 * Gets the user details.
	 *
	 * @param userName the user name
	 * @return the user details
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	public UserDetails getUserDetails(String userName) throws SecurityConfigurationException {
		LOGGER.debug("get user details: ");
		UserDetails returnData = null;
		try {
		
		DirContextOperations dirContextOperation = searchForUser(bindAsServiceUser(), userName);

		returnData =   this.userDetailsContextMapper.mapUserFromContext(dirContextOperation, userName,
				loadUserAuthorities(dirContextOperation));
		}catch(Exception e) {
			throw new SecurityConfigurationException(e);
		}
		return returnData;

	}
	
	/**
	 * Bind as service user.
	 *
	 * @return the dir context
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	private DirContext bindAsServiceUser() throws SecurityConfigurationException {

		DirContext returnValue = null;
		try {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		String bindPrincipal = createBindPrincipal(simpleAuthenticationUsername);
		env.put(Context.SECURITY_PRINCIPAL, bindPrincipal);
		env.put(Context.PROVIDER_URL, adServer);
		env.put(Context.SECURITY_CREDENTIALS, stringEncryptionUtil.decrypt(simpleAuthenticationPassword));
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.OBJECT_FACTORIES, DefaultDirObjectFactory.class.getName());
		returnValue = new  InitialLdapContext(env, null);
		}
		catch (Exception e) {
			
			throw new SecurityConfigurationException(e);
			
		}
		return returnValue;
	}

	/**
	 * Search for user.
	 *
	 * @param context the context
	 * @param username the username
	 * @return the dir context operations
	 * @throws NamingException the naming exception
	 */
	private DirContextOperations searchForUser(DirContext context, String username) throws NamingException {
		String searchFilter = "(&(objectClass=user)(CN=" + getBaseUserName(username) + "))";
		LOGGER.debug("keb5 search filter : {}", searchFilter);
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		String bindPrincipal = createBindPrincipal(username);
		String searchRoot = adRootDN != null ? adRootDN : searchRootFromPrincipal(bindPrincipal);

		try {
			return SpringSecurityLdapTemplate.searchForSingleEntryInternal(context, searchControls, searchRoot,
					searchFilter, new Object[] { bindPrincipal });
		} catch (IncorrectResultSizeDataAccessException incorrectResults) {
			// Search should never return multiple results if properly configured - just
			// rethrow
			if (incorrectResults.getActualSize() != 0) {
				throw incorrectResults;
			}
			// If we found no results, then the username/password did not match
			throw badCredentials("User " + username + " not found in directory.");
		}
	}

	/**
	 * Creates the bind principal.
	 *
	 * @param username the username
	 * @return the string
	 */
	private String createBindPrincipal(String username) {
		if (krb5Realm == null || username.toLowerCase().endsWith(krb5Realm)) {
			return username;
		}

		return username + "@" + krb5Realm;
	}

	/**
	 * Search root from principal.
	 *
	 * @param bindPrincipal the bind principal
	 * @return the string
	 */
	private String searchRootFromPrincipal(String bindPrincipal) {
		int atChar = bindPrincipal.lastIndexOf('@');

		if (atChar < 0) {
			LOGGER.debug("user principal '" + bindPrincipal
					+ "' does not contain the domain, and no domain has been configured");
			throw badCredentials(null);
		}

		return rootDnFromDomain(bindPrincipal.substring(atChar + 1, bindPrincipal.length()));
	}

	/**
	 * Root dn from domain.
	 *
	 * @param domain the domain
	 * @return the string
	 */
	private String rootDnFromDomain(String domain) {
		String[] tokens = StringUtils.tokenizeToStringArray(domain, ".");
		StringBuilder root = new StringBuilder();

		for (String token : tokens) {
			if (root.length() > 0) {
				root.append(',');
			}
			root.append("dc=").append(token);
		}

		return root.toString();
	}

	/**
	 * Bad credentials.
	 *
	 * @param message the message
	 * @return the bad credentials exception
	 */
	private BadCredentialsException badCredentials(String message) {
		if (message != null) {
			return new BadCredentialsException(message);
		} else {
			return new BadCredentialsException("simple LDAP search exception");

		}
	}

	/**
	 * Gets the base user name.
	 *
	 * @param krb5UserName the krb 5 user name
	 * @return the base user name
	 */
	private String getBaseUserName(String krb5UserName) {
		String returnString = null;
		if (StringUtils.hasText("@")) {
			returnString = StringUtils.split(krb5UserName, "@")[0];
		} else {
			returnString = krb5UserName;
		}
		return returnString;
	}

	/**
	 * Creates the successful authentication.
	 *
	 * @param userDetails the user details
	 * @param userName the user name
	 * @return the authentication
	 */
	private Authentication createSuccessfulAuthentication(UserDetails userDetails, String userName) {
		Object password = "";
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userName, password,
				this.authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
		// result.setDetails(userDetails.getDetails());

		return result;
	}

	/**
	 * Load user authorities.
	 *
	 * @param dirContextOperation the dir context operation
	 * @return the collection<? extends granted authority>
	 */
	private Collection<? extends GrantedAuthority> loadUserAuthorities(DirContextOperations dirContextOperation) {
		String[] groups = dirContextOperation.getStringAttributes("memberOf");

		if (groups == null) {
			LOGGER.debug("No values for 'memberOf' attribute.");

			return AuthorityUtils.NO_AUTHORITIES;
		}

		LOGGER.debug("'memberOf' attribute values: " + Arrays.asList(groups));

		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(groups.length);

		for (String group : groups) {
			authorities.add(new SimpleGrantedAuthority(new DistinguishedName(group).removeLast().getValue()));
		}

		return authorities;
	}
	
	 

}
