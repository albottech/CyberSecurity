package io.kryptoblocks.msa.common.cache.function;

 
// TODO: Auto-generated Javadoc
/**
 * The Enum Status.
 */
public enum Status {
	
	/** The successful. */
	SUCCESSFUL("SUCCESSFUL"), 
	
	/** The unsuccessful. */
	UNSUCCESSFUL("UNSUCCESSFUL"),
	
	/** The already exists. */
	ALREADY_EXISTS("ALREADY_EXISTS"), 
	
	/** The invalid input. */
	INVALID_INPUT("INVALID_INPUT"),
	
	/** The no result. */
	NO_RESULT("NO_RESULT"),
	
	/** The unknown operation. */
	UNKNOWN_OPERATION("UNKNOWN_OPERATION");
	
	/**
	 * Instantiates a new status.
	 *
	 * @param value the value
	 */
	private Status(String value){
        this.value = value;
    }
    
    /** The value. */
    private final String value;
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue(){return value;}
};