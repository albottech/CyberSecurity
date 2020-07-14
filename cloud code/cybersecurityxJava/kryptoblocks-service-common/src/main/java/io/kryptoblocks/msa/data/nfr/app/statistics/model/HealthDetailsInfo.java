package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.HealthDetailsInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthDetailsInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_HEALTH_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new health details info.
 */
@NoArgsConstructor

/**
 * Instantiates a new health details info.
 *
 * @param key the key
 * @param status the status
 * @param description the description
 * @param details the details
 */
@AllArgsConstructor
public class HealthDetailsInfo {
	
	/** The key. */
	@EmbeddedId
	HealthDetailsInfoKey key;
	
	/** The status. */
	@Column
	String status;
		
	/** The description. */
	@Column
	String description;
	
	/** The details. */
	@Column
	Map<String, String> details;
	
}
