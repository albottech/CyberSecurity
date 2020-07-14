/**
 * The utility class for data formatting requirements.
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.common.model;

 

// TODO: Auto-generated Javadoc
/**
 * The Enum TeleMetryType.
 */
public enum TeleMetryType {
	
	/** The service business activity. */
	SERVICE_BUSINESS_ACTIVITY ("SERVICE_BUSINESS_ACTIVITY"),
	
	/** The service exception. */
	SERVICE_EXCEPTION ("SERVICE_EXCEPTION"),
	
	/** The service trace. */
	SERVICE_TRACE ("SERVICE_TRACE");
	 
	
	
	/**
	 * Find by value.
	 *
	 * @param value the value
	 * @return the tele metry type
	 */
	public static TeleMetryType findByValue (String value) {
        if (value != null) {
            for (TeleMetryType destinationType : TeleMetryType.values()) {
                if (value.equalsIgnoreCase(destinationType.getValue())) {
                    return destinationType;
                }
            }
        }

        return null;
    }
	
    /**
     * Instantiates a new tele metry type.
     *
     * @param value the value
     */
    private TeleMetryType(String value){
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
    
    


}


