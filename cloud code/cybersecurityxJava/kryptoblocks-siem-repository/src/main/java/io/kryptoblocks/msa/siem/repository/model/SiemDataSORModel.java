package io.kryptoblocks.msa.siem.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.siem.repository.key.SiemDataEnrichmentKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataProcessActivityKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataSORKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataSourcingKey;
import io.kryptoblocks.msa.siem.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataSORModel.
 */
@Entity
@Table(name = ConfigName.SIEM_DATA_SOR_MODEL_TABLE_NAME, schema=ConfigName.SIEM_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new siem data SOR model.
 */
@NoArgsConstructor

/**
 * Instantiates a new siem data SOR model.
 *
 * @param key the key
 * @param networkDataSORBytes the network data SOR bytes
 */
@AllArgsConstructor
public class SiemDataSORModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	SiemDataSORKey key;
 
	/** The network data SOR bytes. */
	@Column
	byte[] networkDataSORBytes;
		
		
	
	
}
