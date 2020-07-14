package io.kryptoblocks.msa.data.nfr.security.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.key.SecurityActivityKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityActivity.
 */
@Entity
@Table(name = ConfigName.SECURITY_ACTIVITY_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new security activity.
 */
@NoArgsConstructor

/**
 * Instantiates a new security activity.
 *
 * @param key the key
 * @param collectedTime the collected time
 * @param reportedTime the reported time
 * @param streamType the stream type
 * @param collectionType the collection type
 * @param userId the user id
 * @param usageContext the usage context
 */
@AllArgsConstructor
public class SecurityActivity {
	
	/** The key. */
	@EmbeddedId
	SecurityActivityKey key;
	
	/** The collected time. */
	@Column
	String collectedTime;
	
	/** The reported time. */
	@Column
	String reportedTime;
	
	 
	/** The stream type. */
	String streamType;
	
	 
	/** The collection type. */
	String collectionType;
	
	/** The user id. */
	@Column
	String userId;
	
	/** The usage context. */
	@Column
	String usageContext;
	 
}
