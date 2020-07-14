package io.kryptoblocks.msa.data.nfr.exception.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.exception.key.ExceptionActivityKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivity.
 */
@Entity
@Table(name = ConfigName.EXCEPTION_ACTIVITY_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new exception activity.
 */
@NoArgsConstructor

/**
 * Instantiates a new exception activity.
 *
 * @param streamType the stream type
 * @param collectionType the collection type
 * @param key the key
 * @param destination the destination
 * @param reportedTime the reported time
 * @param prossedTimeTime the prossed time time
 * @param indexedTime the indexed time
 * @param alertStatus the alert status
 * @param alertNotificationMsg the alert notification msg
 * @param time the time
 * @param location the location
 * @param details the details
 */
@AllArgsConstructor
public class ExceptionActivity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The stream type. */
	String streamType;
	
	/** The collection type. */
	String collectionType;
	
	/** The key. */
	@EmbeddedId
	ExceptionActivityKey key;
	
	
	
	/** The destination. */
	@Column
	String destination;

	

	/** The reported time. */
	@Column
	String reportedTime;

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
