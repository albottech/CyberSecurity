package io.kryptoblocks.msa.data.nfr.app.statistics.model;

 
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.AutoConfigInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoConfigInfo.
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
 * Instantiates a new auto config info.
 */
@NoArgsConstructor

/**
 * Instantiates a new auto config info.
 *
 * @param key the key
 * @param exclussion the exclussion
 * @param positiveMatch the positive match
 * @param negativeMatch the negative match
 */
@AllArgsConstructor
public class AutoConfigInfo {
	
	/** The key. */
	@EmbeddedId
	AutoConfigInfoKey key;
	
	/** The exclussion. */
	@Column
	String exclussion;
	
	/** The positive match. */
	@Column
	Map<String, String> positiveMatch;
	
	/** The negative match. */
	@Column
	Map<String, String> negativeMatch;
	 
}
