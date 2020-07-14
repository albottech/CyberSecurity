/**
 * The web configuration class for security authentication
 * <p>
 *  
 * @author      Lxur LLC
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.config;

import java.util.Arrays;

import javax.naming.directory.DirContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.GlobalSunJaasKerberosConfig;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

import io.kryptoblocks.msa.security.service.business.AuthenticationService;
import io.kryptoblocks.msa.security.service.business.UserDetailsService;
import io.kryptoblocks.msa.security.service.exception.SecurityConfigurationException;
import io.kryptoblocks.msa.security.service.filter.KerberosAuthenticationProcessingFilter;
import io.kryptoblocks.msa.security.service.kerberos.Krb5UserDetailsService;
import io.kryptoblocks.msa.security.service.kerberos.Krb5Util;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityConfig.
 */
@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	/** The ad domain. */
	@Value("${ad.domain}")
	private String AD_DOMAIN;

	/** The ad server. */
	@Value("${ad.server}")
	private String AD_SERVER;

	/** The ad root dn. */
	@Value("${ad.root.dn}")
	private String AD_ROOT_DN;

	/** The ad service principal. */
	@Value("${ad.service.principal}")
	private String AD_SERVICE_PRINCIPAL;

	/** The ad keytab resource name. */
	@Value("${ad.keytab.resource.name}")
	private String AD_KEYTAB_RESOURCE_NAME;

	/** The ad keytab location. */
	@Value("${ad.keytab.resource.location}")
	private String AD_KEYTAB_LOCATION;

	/** The ad keytab config name. */
	@Value("${ad.keytab.config.name}")
	private String AD_KEYTAB_CONFIG_NAME;

	/** The ad user search base. */
	@Value("${ad.user.search.base}")
	private String AD_USER_SEARCH_BASE;

	/** The ad user search filter. */
	@Value("${ad.user.search.filter}")
	private String AD_USER_SEARCH_FILTER;
	
	/** The simple authentication username. */
	@Value("${simple.authentication.username}")
	private String simpleAuthenticationUsername;

	/** The krb 5 util. */
	@Autowired
	Krb5Util krb5Util;

	/**
	 * Instantiates a new security config.
	 */
	public SecurityConfig() {
		super(true);
	}

	 

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.exceptionHandling().and().anonymous().and().servletApi().and().headers().cacheControl().and().and()
				.authorizeRequests()

				// allow anonymous font and template requests
				.antMatchers("/").permitAll().antMatchers("/favicon.ico").permitAll().antMatchers("/resources/**")
				.permitAll().antMatchers("/swagger-ui.html").permitAll().antMatchers("/v2/api-docs").permitAll()
				.antMatchers("/swagger-resources/**").permitAll().antMatchers("/webjars/**").permitAll()

				// allow anonymous POSTs to service auth, token verification, authority check
				.antMatchers(HttpMethod.POST, "${service.route.authentication.path}").permitAll()
				.antMatchers(HttpMethod.POST, "${service.route.token.verification.path}").permitAll()
				.antMatchers(HttpMethod.POST, "${service.route.authorization.path}").permitAll()

				// allow anonymous GETs to API docs
				.antMatchers(HttpMethod.GET, "api/v2/api-docs").permitAll().antMatchers(HttpMethod.GET, "v2/api-docs")
				.permitAll().and();

	}

	/**
	 * Configure.
	 *
	 * @param authManagerBuilder the auth manager builder
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.authenticationProvider(adAuthenticationProvider());
	}

	/**
	 * Authentication manager.
	 *
	 * @return the authentication manager
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Arrays.asList(adAuthenticationProvider()));
	}

	/**
	 * Ad authentication provider.
	 *
	 * @return the active directory ldap authentication provider
	 */
	@Bean
	public ActiveDirectoryLdapAuthenticationProvider adAuthenticationProvider() {
		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(AD_DOMAIN,
				AD_SERVER, AD_ROOT_DN);

		provider.setConvertSubErrorCodesToExceptions(true);
		provider.setUseAuthenticationRequestCredentials(true);
		return provider;
	}

	 

	/**
	 * Sun jaas kerberos ticket validator.
	 *
	 * @return the sun jaas kerberos ticket validator
	 */
	@Bean
	public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
		SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
		ticketValidator.setServicePrincipal(AD_SERVICE_PRINCIPAL);
		ticketValidator.setKeyTabLocation(new FileSystemResource(AD_KEYTAB_LOCATION));
		ticketValidator.setDebug(true);		
		ticketValidator.setDebug(true);
		return ticketValidator;
	}

	/**
	 * Krb 5 util.
	 *
	 * @return the krb 5 util
	 */
	@Bean
	Krb5Util krb5Util() {
		return new Krb5Util();
	}
	
	

	/**
	 * Login config.
	 *
	 * @return the sun jaas krb 5 login config
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	@Bean
	@DependsOn("krb5Util")
	public SunJaasKrb5LoginConfig loginConfig() throws SecurityConfigurationException{
		SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();
		loginConfig.setKeyTabLocation(new FileSystemResource(AD_KEYTAB_LOCATION));
		loginConfig.setServicePrincipal(AD_SERVICE_PRINCIPAL);
		loginConfig.setDebug(true);
		loginConfig.setIsInitiator(true);
		
		//for krb5 config verification
		/*
			krb5Util.setSystemProperties();
			krb5Util.verifySimpleModeSearchConfig();
			krb5Util.verifyKrb5Config();
			krb5Util.getLDAPContextForSimpleSearch();		
			krb5Util.getAuthenticationData(simpleAuthenticationUsername + "@METNET.NET");
		*/
		return loginConfig;
	}

	/**
	 * Authentication service.
	 *
	 * @return the authentication service
	 */
	@Bean
	AuthenticationService authenticationService() {
		return  new AuthenticationService();
	}

	/**
	 * Kerberos service authentication provider.
	 *
	 * @return the kerberos service authentication provider
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	@Bean
	public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() throws SecurityConfigurationException {
		KerberosServiceAuthenticationProvider provider = new KerberosServiceAuthenticationProvider();
		provider.setTicketValidator(sunJaasKerberosTicketValidator());
		//Not useful NO GSAPI support
		//provider.setUserDetailsService(ldapUserDetailsService());
		provider.setUserDetailsService(krb5UserDetailsService());
		return provider;
	}

	/**
	 * Ldap user details service.
	 *
	 * @return the ldap user details service
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	// Don't use it without AD support GSAPI mode, use krb5UserDetailsService instead
	@Bean
	public LdapUserDetailsService ldapUserDetailsService() throws SecurityConfigurationException {
		FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(AD_USER_SEARCH_BASE, AD_USER_SEARCH_FILTER,
				kerberosLdapContextSource());
		LdapUserDetailsService service = new LdapUserDetailsService(userSearch);
		service.setUserDetailsMapper(new LdapUserDetailsMapper());
		return service;
	}
	
	/**
	 * Krb 5 user details service.
	 *
	 * @return the krb 5 user details service
	 */
	@Bean
	Krb5UserDetailsService krb5UserDetailsService() {
		return new Krb5UserDetailsService();
	}

	/**
	 * Kerberos ldap context source.
	 *
	 * @return the kerberos ldap context source
	 * @throws SecurityConfigurationException the security configuration exception
	 */
	@Bean
	public KerberosLdapContextSource kerberosLdapContextSource() throws SecurityConfigurationException {
		KerberosLdapContextSource contextSource = new KerberosLdapContextSource(AD_SERVER);
		contextSource.setLoginConfig(loginConfig());
		return contextSource;
	}

	 

}
