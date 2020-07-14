/*
 * Service exception handler type
 * <p>
 * Copyright 2017-2017 MetLife Investment.
 */
package io.kryptoblocks.msa.common.model;

 
// TODO: Auto-generated Javadoc
/**
 * The Enum ExceptionHandlerType.
 *
 * @author      MetLife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
public enum ExceptionHandlerType {
	
	/** The data access exception handler. */
	DATA_ACCESS_EXCEPTION_HANDLER ("DATA_ACCESS_EXCEPTION_HANDLER"), 
	
	/** The messaging exception handler. */
	MESSAGING_EXCEPTION_HANDLER("MESSAGING_EXCEPTION_HANDLER"), 
	
	/** The business exception handler. */
	BUSINESS_EXCEPTION_HANDLER("BUSINESS_EXCEPTION_HANDLER"),
	
	/** The input exception handler. */
	INPUT_EXCEPTION_HANDLER("INPUT_EXCEPTION_HANDLER"),
	
	/** The security exception handler. */
	SECURITY_EXCEPTION_HANDLER("SECURITY_EXCEPTION_HANDLER"),
	
	/** The eventing exception handler. */
	EVENTING_EXCEPTION_HANDLER("EVENTING_EXCEPTION_HANDLER");
	
	
	/**
	 * Static method to find an exception handler type by value.
	 * 
	 * @param value input value
	 * @return {@link ExceptionHandlerType} identified by the input value
	 */
	public static ExceptionHandlerType findByValue (String value) {
        if (value != null) {
            for (ExceptionHandlerType authorityName : ExceptionHandlerType.values()) {
                if (value.equalsIgnoreCase(authorityName.getValue())) {
                    return authorityName;
                }
            }
        }

        return null;
    }
	
	
    /**
	 * Method to find a exception handler value.
	 * 
	 * @return string value of the exception handler name 
	 */
    public String getValue(){
    	return value;
    }
    
    /**
     * Instantiates a new exception handler type.
     *
     * @param value the value
     */
    private ExceptionHandlerType(String value){
        this.value = value;
    }
    
    /** The value. */
    private final String value;
}