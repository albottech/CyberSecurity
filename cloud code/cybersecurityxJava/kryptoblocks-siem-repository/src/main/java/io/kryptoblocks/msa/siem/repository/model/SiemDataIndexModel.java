package io.kryptoblocks.msa.siem.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.siem.repository.key.SiemDataAIKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataEnrichmentKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataIndexKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataProcessActivityKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataSORKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataSourcingKey;
import io.kryptoblocks.msa.siem.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataIndexModel.
 */
@Entity
@Table(name = ConfigName.SIEM_DATA_INDEX_MODEL_TABLE_NAME, schema=ConfigName.SIEM_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new siem data index model.
 */
@NoArgsConstructor

/**
 * Instantiates a new siem data index model.
 *
 * @param key the key
 * @param networkDataAIBytes the network data AI bytes
 */
@AllArgsConstructor
public class SiemDataIndexModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	SiemDataIndexKey key;
 
	/** The network data AI bytes. */
	@Column
	byte[] networkDataAIBytes;
		
		
	
	
}
