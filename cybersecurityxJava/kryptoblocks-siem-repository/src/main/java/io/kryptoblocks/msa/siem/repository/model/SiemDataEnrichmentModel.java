package io.kryptoblocks.msa.siem.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.siem.repository.key.SiemDataEnrichmentKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataProcessActivityKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataSourcingKey;
import io.kryptoblocks.msa.siem.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataEnrichmentModel.
 */
@Entity
@Table(name = ConfigName.SIEM_DATA_ENRICHMENT_MODEL_TABLE_NAME, schema=ConfigName.SIEM_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new siem data enrichment model.
 */
@NoArgsConstructor

/**
 * Instantiates a new siem data enrichment model.
 *
 * @param key the key
 * @param networkDataEnrichedBytes the network data enriched bytes
 */
@AllArgsConstructor
public class SiemDataEnrichmentModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	SiemDataEnrichmentKey key;
 
	/** The network data enriched bytes. */
	@Column
	byte[] networkDataEnrichedBytes;
		
		
	
	
}
