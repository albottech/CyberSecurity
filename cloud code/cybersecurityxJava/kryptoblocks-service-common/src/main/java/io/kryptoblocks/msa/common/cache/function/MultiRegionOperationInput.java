package io.kryptoblocks.msa.common.cache.function;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

 

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class MultiRegionOperationInput.
 */
public class MultiRegionOperationInput implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the region input.
	 *
	 * @return the region input
	 */
	@Getter /**
  * Sets the region input.
  *
  * @param regionInput the new region input
  */
 @Setter (AccessLevel.PUBLIC)	
	@NotNull
	private List<SingleRegionOperationInput> regionInput;	 

}
