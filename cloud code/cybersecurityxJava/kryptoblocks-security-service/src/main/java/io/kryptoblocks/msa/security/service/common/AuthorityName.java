/*
 * The security service authority name class.
 * This class provide a named elements for identifying the supported service authorities 
 * <p>
 * Copyright 2017-2017 MetLife Investment.
 */
package io.kryptoblocks.msa.security.service.common;

 
// TODO: Auto-generated Javadoc
/**
 * The Enum AuthorityName.
 *
 * @author      MetLife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
public enum AuthorityName {
	
	/** The service op rd. */
	SERVICE_OP_RD ("SERVICE_OP_RD"), 
	
	/** The service op wt. */
	SERVICE_OP_WT("SERVICE_OP_WT"), 
	
	/** The service op dl. */
	SERVICE_OP_DL("SERVICE_OP_DL"),
	
	/** The service op ut. */
	SERVICE_OP_UT("SERVICE_OP_UT"),
	
	/** The service ad. */
	SERVICE_AD("SERVICE_AD");
	
	
	/**
	 * Static method to find a authority by value.
	 * 
	 * @param value input value
	 * @return {@link AuthorityName} identified by the input value
	 */
	public static AuthorityName findByValue (String value) {
        if (value != null) {
            for (AuthorityName authorityName : AuthorityName.values()) {
                if (value.equalsIgnoreCase(authorityName.getValue())) {
                    return authorityName;
                }
            }
        }

        return null;
    }
	
	
    /**
	 * Method to find a authority's value.
	 * 
	 * @return {@link String} value of the authority name 
	 */
    public String getValue(){
    	return value;
    }
    
    /**
     * Instantiates a new authority name.
     *
     * @param value the value
     */
    private AuthorityName(String value){
        this.value = value;
    }
    
    /** The value. */
    private final String value;
}