package io.kryptoblocks.msa.data.nfr.udt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class Exception.
 */
@Embeddable

/**
 * Instantiates a new exception.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new exception.
 *
 * @param serviceName the service name
 * @param destination the destination
 * @param id the id
 * @param prossedTimeTime the prossed time time
 * @param indexedTime the indexed time
 * @param alertStatus the alert status
 * @param alertNotificationMsg the alert notification msg
 * @param time the time
 * @param location the location
 * @param details the details
 */
@AllArgsConstructor
public class Exception implements Serializable {
	
		
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The service name. */
	@Column
	String serviceName;
	
	/** The destination. */
	@Column
	String destination;
	
	/** The id. */
	@Column
	String id;
	
	/** The prossed time time. */
	@Column
	String prossedTimeTime;
	
	/** The indexed time. */
	@Column
	String indexedTime;
	
	/** The alert status. */
	@Column
	boolean alertStatus;
	
	/** The alert notification msg. */
	@Column
	String alertNotificationMsg;
	
	/** The time. */
	@Column
	String time;
	
	/** The location. */
	@Column 
	String location;
	
	/** The details. */
	@Column 
	String details;
	
}
