package io.kryptoblocks.msa.data.nfr.business.activity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.kryptoblocks.msa.data.nfr.business.activity.key.BusinessActivityKey;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.RequestDetails;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.ResponseDetails;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.key.ChannelKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivity.
 */
@Entity
@Table(name = ConfigName.BUSINESS_ACTIVITY_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new business activity.
 */
@NoArgsConstructor

/**
 * Instantiates a new business activity.
 *
 * @param streamType the stream type
 * @param collectionType the collection type
 * @param key the key
 * @param destination the destination
 * @param instance the instance
 * @param durationInMilliSeconds the duration in milli seconds
 * @param reportedTime the reported time
 * @param prossedTimeTime the prossed time time
 * @param indexedTime the indexed time
 * @param location the location
 * @param requestDetails the request details
 * @param responseDetails the response details
 */
@AllArgsConstructor
@JsonIgnoreProperties(value = { "streamType" })
public class BusinessActivity implements Serializable {	
	 
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The stream type. */
		String streamType;
		
		/** The collection type. */
		String collectionType;
		
		
		/** The key. */
		@EmbeddedId
		BusinessActivityKey key;
		
		
		/** The destination. */
		@Column
		String destination;
		
		/** The instance. */
		@Column
		String instance;
				 
		
		/** The duration in milli seconds. */
		@Column
		String durationInMilliSeconds;
		
		/** The reported time. */
		@Column
		String reportedTime;
		
		/** The prossed time time. */
		@Column
		String prossedTimeTime;
		
		/** The indexed time. */
		@Column
		String indexedTime;
		
		/** The location. */
		@Column
		String location;
		
		/** The request details. */
		@Embedded
		RequestDetails requestDetails;
		
		/** The response details. */
		@Embedded
		ResponseDetails responseDetails;
		
}
