package io.kryptoblocks.msa.common.cache.function;

	// TODO: Auto-generated Javadoc
/**
	 * The Enum CacheSupportedFunctionOperation.
	 */
	public enum CacheSupportedFunctionOperation{
		
		/** The containskey. */
		CONTAINSKEY("CONTAINSKEY"),
		
		/** The containsvalue. */
		CONTAINSVALUE("CONTAINSVALUE"),
		
		/** The containsvalueforkey. */
		CONTAINSVALUEFORKEY("CONTAINSVALUEFORKEY"),
		
		/** The create. */
		CREATE("CREATE"),
		
		/** The find. */
		FIND("FIND"),
		
		/** The findunique. */
		FINDUNIQUE("FINDUNIQUE"),
		
		/** The get. */
		GET("GET"),
		
		/** The getall. */
		GETALL("GETALL"),
		
		/** The get for update. */
		GET_FOR_UPDATE("GET_FOR_UPDATE"),
		
		/** The update. */
		UPDATE("UPDATE"),
		
		/** The put. */
		PUT("PUT"),
		
		/** The putall. */
		PUTALL("PUTALL"),
		
		/** The putifabsent. */
		PUTIFABSENT("PUTIFABSENT"),
		
		/** The query. */
		QUERY("QUERY"),
		
		/** The remove. */
		REMOVE("REMOVE"),
		
		/** The replace. */
		REPLACE("REPLACE"),
		
		/** The replacewithverification. */
		REPLACEWITHVERIFICATION("REPLACEWITHVERIFICATION"),
		
		/** The createregion. */
		CREATEREGION("CREATEREGION"),
		
		/** The clear. */
		CLEAR("CLEAR");
		
		
		
		
		/**
		 * Instantiates a new cache supported function operation.
		 *
		 * @param value the value
		 */
		private CacheSupportedFunctionOperation(String value){
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


