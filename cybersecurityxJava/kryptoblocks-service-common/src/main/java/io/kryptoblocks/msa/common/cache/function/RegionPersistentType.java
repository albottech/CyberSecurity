package io.kryptoblocks.msa.common.cache.function;

// TODO: Auto-generated Javadoc
/**
 * The Enum RegionPersistentType.
 */
public enum RegionPersistentType {
	
	/** The empty. */
	EMPTY("EMPTY"),
	
	/** The normal. */
	NORMAL("NORMAL"),	
	
	/** The preloaded. */
	PRELOADED ("PRELOADED"),
	
	/** The partition. */
	PARTITION ("PARTITION"),
	
	/** The replication. */
	REPLICATION ("REPLICATION"),
	
	/** The persistent partition. */
	PERSISTENT_PARTITION ("PERSISTENT_PARTITION"),
	
	/** The persistent replicate. */
	PERSISTENT_REPLICATE ("PERSISTENT_REPLICATE");
	
	
	

    /**
     * Instantiates a new region persistent type.
     *
     * @param value the value
     */
    private RegionPersistentType(String value){
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
