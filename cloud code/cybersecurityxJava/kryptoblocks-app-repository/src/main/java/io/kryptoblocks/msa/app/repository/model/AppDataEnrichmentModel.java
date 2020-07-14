package io.kryptoblocks.msa.app.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.app.repository.key.AppDataEnrichmentKey;
import io.kryptoblocks.msa.app.repository.key.AppDataProcessActivityKey;
import io.kryptoblocks.msa.app.repository.key.AppDataSourcingKey;
import io.kryptoblocks.msa.app.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class AppDataEnrichmentModel.
 */
@Entity
@Table(name = ConfigName.APP_DATA_ENRICHMENT_MODEL_TABLE_NAME, schema=ConfigName.APP_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new app data enrichment model.
 */
@NoArgsConstructor

/**
 * Instantiates a new app data enrichment model.
 *
 * @param key the key
 * @param networkDataEnrichedBytes the network data enriched bytes
 */
@AllArgsConstructor
public class AppDataEnrichmentModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	AppDataEnrichmentKey key;
 
	/** The network data enriched bytes. */
	@Column
	byte[] networkDataEnrichedBytes;
		
		
	
	
}
