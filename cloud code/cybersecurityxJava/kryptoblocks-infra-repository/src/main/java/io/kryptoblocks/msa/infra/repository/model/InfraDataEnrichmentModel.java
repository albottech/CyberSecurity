package io.kryptoblocks.msa.infra.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.infra.repository.key.InfraDataEnrichmentKey;
import io.kryptoblocks.msa.infra.repository.key.InfraDataProcessActivityKey;
import io.kryptoblocks.msa.infra.repository.key.InfraDataSourcingKey;
import io.kryptoblocks.msa.infra.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraDataEnrichmentModel.
 */
@Entity
@Table(name = ConfigName.INFRA_DATA_ENRICHMENT_MODEL_TABLE_NAME, schema=ConfigName.INFRA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new infra data enrichment model.
 */
@NoArgsConstructor

/**
 * Instantiates a new infra data enrichment model.
 *
 * @param key the key
 * @param networkDataEnrichedBytes the network data enriched bytes
 */
@AllArgsConstructor
public class InfraDataEnrichmentModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	InfraDataEnrichmentKey key;
 
	/** The network data enriched bytes. */
	@Column
	byte[] networkDataEnrichedBytes;
		
		
	
	
}
