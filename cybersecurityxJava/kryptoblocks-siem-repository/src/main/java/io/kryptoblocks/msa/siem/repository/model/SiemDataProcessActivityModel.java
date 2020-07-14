package io.kryptoblocks.msa.siem.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.siem.repository.key.SiemDataProcessActivityKey;
import io.kryptoblocks.msa.siem.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataProcessActivityModel.
 */
@Entity
@Table(name = ConfigName.SIEM_DATA_PROCESS_ACTIVITY_TABLE_NAME, schema=ConfigName.SIEM_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new siem data process activity model.
 */
@NoArgsConstructor

/**
 * Instantiates a new siem data process activity model.
 *
 * @param key the key
 * @param activityType the activity type
 * @param startTime the start time
 * @param endTime the end time
 * @param status the status
 * @param streamSource the stream source
 * @param streamDestination the stream destination
 */
@AllArgsConstructor
public class SiemDataProcessActivityModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	SiemDataProcessActivityKey key;
	
	 
	
	/** The activity type. */
	@Column
	String activityType;
	
	/** The start time. */
	@Column
	String startTime;
	
	/** The end time. */
	@Column
	String endTime;
	
	/** The status. */
	@Column
	String status;
	
	
	/** The stream source. */
	@Column
	/*
	 * always a kafka topic
	 */
	String streamSource;
	
	/** The stream destination. */
	@Column
	/*
	 * this can be spark, cassandra or elastic search
	 */
	String streamDestination;
		
		
	
	
}
