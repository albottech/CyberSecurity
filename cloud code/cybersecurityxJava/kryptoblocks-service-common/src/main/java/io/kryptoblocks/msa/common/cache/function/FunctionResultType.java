package io.kryptoblocks.msa.common.cache.function;

// TODO: Auto-generated Javadoc
/**
 * The Enum FunctionResultType.
 */
public enum FunctionResultType {
	
	/** The boolean. */
	BOOLEAN ("BOOLEAN"),
	
	/** The objects. */
	OBJECTS ("OBJECTS"),	
	
	/** The void. */
	VOID ("VOID");
    
    /**
     * Instantiates a new function result type.
     *
     * @param value the value
     */
    private FunctionResultType(String value){
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

    

}
