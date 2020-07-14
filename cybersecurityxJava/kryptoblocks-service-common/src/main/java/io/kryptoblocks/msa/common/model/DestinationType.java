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
 * The Enum DestinationType.
 */
public enum DestinationType {
	
	/** The log. */
	LOG ("LOG"),
	
	/** The kafka. */
	KAFKA ("KAFKA"),
	
	/** The oms. */
	OMS ("OMS"),
	
	/** The db. */
	DB ("DB"),
	
	/** The elastic search. */
	ELASTIC_SEARCH ("ELASTIC_SEARCH"),
	
	/** The rabbitmq. */
	RABBITMQ ("RABBITMQ");
	
	
	/**
	 * Find by value.
	 *
	 * @param value the value
	 * @return the destination type
	 */
	public static DestinationType findByValue (String value) {
        if (value != null) {
            for (DestinationType destinationType : DestinationType.values()) {
                if (value.equalsIgnoreCase(destinationType.getValue())) {
                    return destinationType;
                }
            }
        }

        return null;
    }
	
    /**
     * Instantiates a new destination type.
     *
     * @param value the value
     */
    private DestinationType(String value){
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


