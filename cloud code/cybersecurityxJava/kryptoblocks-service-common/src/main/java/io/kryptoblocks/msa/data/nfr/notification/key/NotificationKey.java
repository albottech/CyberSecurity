package io.kryptoblocks.msa.data.nfr.notification.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new notification key.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new notification key.
 *
 * @param serviceName the service name
 * @param id the id
 */
@AllArgsConstructor
@Embeddable
public class NotificationKey implements  Serializable  {
	  
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The service name. */
	@Column 	
	String serviceName;	
	 
	/** The id. */
	@Column	
	private String id;
	 
}