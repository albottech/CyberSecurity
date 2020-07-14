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
 * The Class RequestDetails.
 */
@Embeddable

/**
 * Instantiates a new request details.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new request details.
 *
 * @param operation the operation
 * @param parameters the parameters
 * @param body the body
 * @param remoteAddress the remote address
 */
@AllArgsConstructor
public class RequestDetails implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The operation. */
	@Column
	String operation;
	
	/** The parameters. */
	@Column
	Map<String, String> parameters;
	
	/** The body. */
	@Column
	String body;
	
	/** The remote address. */
	@Column
	String remoteAddress;

}
