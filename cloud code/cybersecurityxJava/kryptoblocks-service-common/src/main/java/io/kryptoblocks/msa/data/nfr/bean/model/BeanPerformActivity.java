package io.kryptoblocks.msa.data.nfr.bean.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.bean.key.BeanPerfKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.notification.key.NotificationKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanPerformActivity.
 */
@Entity
@Table(name = ConfigName.APP_OBJECT_PERF_INFO_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new bean perform activity.
 */
@NoArgsConstructor

/**
 * Instantiates a new bean perform activity.
 *
 * @param streamType the stream type
 * @param key the key
 * @param collectedTime the collected time
 * @param reportedTime the reported time
 * @param serviceName the service name
 * @param lastValue the last value
 * @param hits the hits
 * @param average the average
 * @param total the total
 * @param min the min
 * @param max the max
 * @param maxActive the max active
 * @param active the active
 * @param averageActive the average active
 * @param firstAccess the first access
 * @param lastAccess the last access
 */
@AllArgsConstructor
public class BeanPerformActivity {
	
	/** The stream type. */
	String streamType;
	
	/** The key. */
	@EmbeddedId
	BeanPerfKey key;
	 	
	/** The collected time. */
	@Column
	String collectedTime;
	
	/** The reported time. */
	@Column
	String reportedTime;
	
	/** The service name. */
	@Column
	String serviceName;
	
	/** The last value. */
	@Column
	double lastValue;
	
	/** The hits. */
	@Column
	double hits;
	
	/** The average. */
	@Column
	double average;
	
	/** The total. */
	@Column
	double total;
	
	/** The min. */
	@Column
	double min;
	
	/** The max. */
	@Column
	double max;
	
	/** The max active. */
	@Column
	double maxActive;
	
	/** The active. */
	@Column
	String active;
	
	/** The average active. */
	@Column
	String averageActive;
	
	
	
	/** The first access. */
	@Column
	Date firstAccess;
	
	/** The last access. */
	@Column
	Date lastAccess;
	
	

}
