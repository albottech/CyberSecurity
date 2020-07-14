package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.PropertiesInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_PROPERTIES_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new properties info.
 */
@NoArgsConstructor

/**
 * Instantiates a new properties info.
 *
 * @param key the key
 * @param properties the properties
 */
@AllArgsConstructor
public class PropertiesInfo {
	
	/** The key. */
	@EmbeddedId
	PropertiesInfoKey key;
		
	/** The properties. */
	@Column
	Map<String, String>  properties;
	
}
