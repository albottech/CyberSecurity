package io.kryptoblocks.msa.common.cache.pdx;

import java.io.Serializable;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new pdx object.
 */
@Data
public class PdxObject implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	String key;
	
	/** The region. */
	String region;
	
	/** The data. */
	String data;
	  

}
