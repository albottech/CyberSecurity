package io.kryptoblocks.msa.common.cache.function;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import io.kryptoblocks.msa.common.cache.pdx.PdxObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new single region operation input.
 */
@Data
public class SingleRegionOperationInput implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The region. */
	String region;
	
	/** The operation. */
	String operation;
	
	/** The key. */
	String key;
	
	/** The value. */
	PdxObject value;
	
	/** The query. */
	String query;
	
	/** The query param. */
	Object[] queryParam;
	
	/** The value map. */
	Map<String,PdxObject> valueMap;
	
	/** The region attributes. */
	Map<String, Object> regionAttributes;
	
	 

}
