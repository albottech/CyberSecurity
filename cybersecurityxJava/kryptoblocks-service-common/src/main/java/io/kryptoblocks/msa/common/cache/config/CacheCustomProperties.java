package io.kryptoblocks.msa.common.cache.config;

import java.util.List;
import java.util.Map;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new cache custom properties.
 */
@Data
public class CacheCustomProperties {
	
	/** The region. */
	//region list
	private List<String> region;
	
	/*
	public static class Credentials {
        private String authMethod;
        private String username;
        private String password;
 
       // standard getters and setters
    }
    private String host;
    private int port;
    private String from;
    private Credentials credentials;
    */
    
    //private Map<String, String> additionalHeaders;
  
    // standard getters and setters
    
    /*
     * #Simple properties
mail.host=mailer@mail.com
mail.port=9000
mail.from=mailer@mail.com
 
#List properties
mail.defaultRecipients[0]=admin@mail.com
mail.defaultRecipients[1]=owner@mail.com
 
#Map Properties
mail.additionalHeaders.redelivery=true
mail.additionalHeaders.secure=true
 
#Object properties
mail.credentials.username=john
mail.credentials.password=password
mail.credentials.authMethod=SHA1
     */

}
