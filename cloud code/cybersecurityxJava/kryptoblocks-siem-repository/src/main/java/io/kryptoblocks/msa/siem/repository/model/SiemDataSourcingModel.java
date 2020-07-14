package io.kryptoblocks.msa.siem.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.siem.repository.key.SiemDataProcessActivityKey;
import io.kryptoblocks.msa.siem.repository.key.SiemDataSourcingKey;
import io.kryptoblocks.msa.siem.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataSourcingModel.
 */
@Entity
@Table(name = ConfigName.SIEM_DATA_SOURCING_MODEL_TABLE_NAME, schema=ConfigName.SIEM_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new siem data sourcing model.
 */
@NoArgsConstructor

/**
 * Instantiates a new siem data sourcing model.
 *
 * @param key the key
 * @param networkDataSourcingBytes the network data sourcing bytes
 */
@AllArgsConstructor
public class SiemDataSourcingModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	SiemDataSourcingKey key;
 
	/** The network data sourcing bytes. */
	@Column
	byte[] networkDataSourcingBytes;
		
		
	
	
}
