package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.ServletInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ServletInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_SERVLET_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new servlet info.
 */
@NoArgsConstructor

/**
 * Instantiates a new servlet info.
 *
 * @param key the key
 * @param maxHttpSession the max http session
 * @param activeHttpSession the active http session
 */
@AllArgsConstructor
public class ServletInfo {
	
	/** The key. */
	@EmbeddedId
	ServletInfoKey key;
		
	/** The max http session. */
	@Column
	Long maxHttpSession;
	
	/** The active http session. */
	@Column
	Long activeHttpSession;
	
	
	
}
