package io.kryptoblocks.msa.security.service.kerberos;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class LdapJndiActionUtil.
 */
public class LdapJndiActionUtil implements java.security.PrivilegedAction {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapJndiActionUtil.class);
	
	/**
	 * Gets the authentication mode.
	 *
	 * @return the authentication mode
	 */
	@Getter /**
  * Sets the authentication mode.
  *
  * @param authenticationMode the new authentication mode
  */
 @Setter
    private String authenticationMode;
  
	/**
	 * Gets the ad server.
	 *
	 * @return the ad server
	 */
	@Getter /**
  * Sets the ad server.
  *
  * @param adServer the new ad server
  */
 @Setter
	private String adServer;
	
	/**
	 * Gets the ad root DN.
	 *
	 * @return the ad root DN
	 */
	@Getter /**
  * Sets the ad root DN.
  *
  * @param adRootDN the new ad root DN
  */
 @Setter
    private String adRootDN;
    
    /**
     * Instantiates a new ldap jndi action util.
     *
     * @param authenticationMode the authentication mode
     * @param adServer the ad server
     * @param adRootDN the ad root DN
     */
    public LdapJndiActionUtil(String authenticationMode, String adServer, String adRootDN  ) {
    	setAuthenticationMode(authenticationMode);
    	setAdServer(adServer);
    	setAdRootDN(adRootDN);    	
    }

    /**
     * Run.
     *
     * @return the object
     */
    public Object run() {
    try {	
    	return getDirContext( );
    }catch(Exception e) {
    	LOGGER.debug("unable to create LDAP context jndi operation:");
    	LOGGER.debug("exception details are: {}", ExceptionUtils.getFullStackTrace(e));  
    }
	return null;
    }

/**
 * Gets the dir context.
 *
 * @return the dir context
 * @throws Exception the exception
 */
public DirContext getDirContext() throws Exception {
    	
    	Hashtable<String, String> env = new Hashtable<String, String>();    	
    	env.put(Context.INITIAL_CONTEXT_FACTORY, 
    	    "com.sun.jndi.ldap.LdapCtxFactory");
    	env.put(Context.PROVIDER_URL, 
    	    adServer);
    	env.put(Context.SECURITY_AUTHENTICATION, authenticationMode);
    	/*
    	 * env.put(Context.SECURITY_AUTHENTICATION, "simple");
		String bindPrincipal = createBindPrincipal(username);
		env.put(Context.SECURITY_PRINCIPAL, bindPrincipal);
		env.put(Context.PROVIDER_URL, bindUrl);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.OBJECT_FACTORIES, DefaultDirObjectFactory.class.getName());
    	 */
    	LOGGER.debug("creating directory context using the following input: ad server: {}, authentication mode: {},", adServer, authenticationMode);
    	    /* Create initial context */
    	    return new InitialDirContext(env);
    	        	
        }
}