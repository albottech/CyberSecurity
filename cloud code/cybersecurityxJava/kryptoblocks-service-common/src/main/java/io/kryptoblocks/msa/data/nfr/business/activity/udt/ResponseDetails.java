package io.kryptoblocks.msa.data.nfr.business.activity.udt;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 
// TODO: Auto-generated Javadoc
/**
 * The Class ResponseDetails.
 */
@Embeddable

/**
 * Instantiates a new response details.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new response details.
 *
 * @param content the content
 */
@AllArgsConstructor

public class ResponseDetails implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The content. */
	@Column
	String content;
	 
}
