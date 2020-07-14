package io.kryptoblocks.msa.common.cache.function;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class CacheFuctionExcectionException.
 */
public class CacheFuctionExcectionException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	@Getter /**
  * Sets the region.
  *
  * @param region the new region
  */
 @Setter (AccessLevel.PUBLIC)private String region;
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	@Getter 
 /**
  * Sets the description.
  *
  * @param description the new description
  */
 @Setter (AccessLevel.PUBLIC)private String description;
	
	/**
	 * Instantiates a new cache fuction excection exception.
	 *
	 * @param region the region
	 * @param description the description
	 */
	public CacheFuctionExcectionException(String region, String description){
		
		this.region = region;
		this.description = description;
		
	}
	

}
