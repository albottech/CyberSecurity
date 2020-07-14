package io.kryptoblocks.msa.common.cache.function;

// TODO: Auto-generated Javadoc
/**
 * The Enum RegionOperationType.
 */
public enum RegionOperationType {
	
	/** The single region operation. */
	SINGLE_REGION_OPERATION("SINGLE_REGION_OPERATION"),
	
	/** The multi region operation. */
	MULTI_REGION_OPERATION("MULTI_REGION_OPERATION");

    /**
     * Instantiates a new region operation type.
     *
     * @param value the value
     */
    private RegionOperationType(String value){
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
