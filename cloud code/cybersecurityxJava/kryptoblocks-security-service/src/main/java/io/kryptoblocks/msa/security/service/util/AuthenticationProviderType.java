/**
 * The utility class for data formatting requirements.
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.util;

 

// TODO: Auto-generated Javadoc
/**
 * The Enum AuthenticationProviderType.
 */
public enum AuthenticationProviderType {
	
	/** The ldap. */
	LDAP ("LDAP"),
	
	/** The db. */
	DB ("DB"),
	
	/** The auth2. */
	AUTH2 ("AUTH2");
	
	
    /**
     * Instantiates a new authentication provider type.
     *
     * @param value the value
     */
    private AuthenticationProviderType(String value){
        this.value = value;
    }
    
    /** The value. */
    private final String value;
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue(){
    	return value;
    }
    
    /**
     * Find by value.
     *
     * @param value the value
     * @return the authentication provider type
     */
    public static AuthenticationProviderType findByValue (String value) {
        if (value != null) {
            for (AuthenticationProviderType authenticationProviderType : AuthenticationProviderType.values()) {
                if (value.equalsIgnoreCase(authenticationProviderType.getValue())) {
                    return authenticationProviderType;
                }
            }
        }

        return null;
    }

}


