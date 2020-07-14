package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.InfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new generic info.
 */
@NoArgsConstructor

/**
 * Instantiates a new generic info.
 *
 * @param key the key
 * @param details the details
 */
@AllArgsConstructor
public class GenericInfo {
	
	/** The key. */
	@EmbeddedId
	InfoKey key;
	
	
	/** The details. */
	@Column
	Map<String, String> details;
	
}
