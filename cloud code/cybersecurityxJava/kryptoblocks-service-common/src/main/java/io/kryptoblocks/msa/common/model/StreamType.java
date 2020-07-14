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
 * The Enum StreamType.
 */
public enum StreamType {
	
	/** The log. */
	LOG ("LOG"),
	
	/** The kafka. */
	KAFKA ("KAFKA"),	
	
	/** The rabbitmq. */
	RABBITMQ ("RABBITMQ");
	
	
	/**
	 * Find by value.
	 *
	 * @param value the value
	 * @return the stream type
	 */
	public static StreamType findByValue (String value) {
        if (value != null) {
            for (StreamType destinationType : StreamType.values()) {
                if (value.equalsIgnoreCase(destinationType.getValue())) {
                    return destinationType;
                }
            }
        }

        return null;
    }
	
    /**
     * Instantiates a new stream type.
     *
     * @param value the value
     */
    private StreamType(String value){
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


