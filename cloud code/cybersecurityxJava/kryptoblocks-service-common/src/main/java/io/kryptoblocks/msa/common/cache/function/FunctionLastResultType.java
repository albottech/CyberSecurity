package io.kryptoblocks.msa.common.cache.function;

// TODO: Auto-generated Javadoc
/**
 * The Enum FunctionLastResultType.
 */
public enum FunctionLastResultType {
	
	/** The commit. */
	COMMIT ("COMMIT"),
	
	/** The rollback. */
	ROLLBACK ("ROLLBACK");
    
    /**
     * Instantiates a new function last result type.
     *
     * @param value the value
     */
    private FunctionLastResultType(String value){
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
