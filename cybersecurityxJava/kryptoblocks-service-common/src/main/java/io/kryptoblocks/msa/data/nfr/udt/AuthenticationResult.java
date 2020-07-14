package io.kryptoblocks.msa.data.nfr.udt;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
 
// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationResult.
 */
@Embeddable

/**
 * Instantiates a new authentication result.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new authentication result.
 *
 * @param msg the msg
 * @param userName the user name
 * @param token the token
 */
@AllArgsConstructor
public class AuthenticationResult implements Serializable{

	 
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The msg. */
	@Column 
	String msg;
	
	/** The user name. */
	@Column	
	String userName;
	
	/** The token. */
	@Column
	String token;

}
