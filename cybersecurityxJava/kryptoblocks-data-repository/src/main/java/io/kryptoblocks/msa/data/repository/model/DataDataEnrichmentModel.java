package io.kryptoblocks.msa.data.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.repository.key.DataDataEnrichmentKey;
import io.kryptoblocks.msa.data.repository.key.DataDataProcessActivityKey;
import io.kryptoblocks.msa.data.repository.key.DataDataSourcingKey;
import io.kryptoblocks.msa.data.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DataDataEnrichmentModel.
 */
@Entity
@Table(name = ConfigName.DATA_DATA_ENRICHMENT_MODEL_TABLE_NAME, schema=ConfigName.DATA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new data data enrichment model.
 */
@NoArgsConstructor

/**
 * Instantiates a new data data enrichment model.
 *
 * @param key the key
 * @param networkDataEnrichedBytes the network data enriched bytes
 */
@AllArgsConstructor
public class DataDataEnrichmentModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	DataDataEnrichmentKey key;
 
	/** The network data enriched bytes. */
	@Column
	byte[] networkDataEnrichedBytes;
		
		
	
	
}
