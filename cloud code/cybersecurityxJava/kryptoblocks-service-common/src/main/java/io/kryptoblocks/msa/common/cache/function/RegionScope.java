package io.kryptoblocks.msa.common.cache.function;

// TODO: Auto-generated Javadoc
/**
 * The Enum RegionScope.
 */
public enum RegionScope {
	
	/** The distributed ack. */
	DISTRIBUTED_ACK ("DISTRIBUTED_ACK "),
	
	/** The distributed no ack. */
	DISTRIBUTED_NO_ACK ("DISTRIBUTED_NO_ACK"),	
	
	/** The global. */
	GLOBAL ("GLOBAL"),
	
	/** The local. */
	LOCAL("LOCAL ");
	
    /**
     * Instantiates a new region scope.
     *
     * @param value the value
     */
    private RegionScope(String value){
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
