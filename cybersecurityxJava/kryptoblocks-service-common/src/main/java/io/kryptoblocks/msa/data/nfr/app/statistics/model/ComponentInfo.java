package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.ComponentInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_COMPONENT_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new component info.
 */
@NoArgsConstructor

/**
 * Instantiates a new component info.
 *
 * @param key the key
 * @param details the details
 */
@AllArgsConstructor
public class ComponentInfo {
	
	/** The key. */
	@EmbeddedId
	ComponentInfoKey key;
		
	/** The details. */
	@Column
	List<String>  details;
	
}
