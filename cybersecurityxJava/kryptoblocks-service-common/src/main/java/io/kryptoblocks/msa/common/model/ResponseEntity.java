package io.kryptoblocks.msa.common.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseEntity.
 */
public class ResponseEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	@Getter /**
  * Sets the content.
  *
  * @param content the new content
  */
 @Setter(AccessLevel.PUBLIC)
	String content;
	
	 
}
