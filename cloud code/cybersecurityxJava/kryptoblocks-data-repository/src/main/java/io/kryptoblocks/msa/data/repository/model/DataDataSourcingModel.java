package io.kryptoblocks.msa.data.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.repository.key.DataDataProcessActivityKey;
import io.kryptoblocks.msa.data.repository.key.DataDataSourcingKey;
import io.kryptoblocks.msa.data.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DataDataSourcingModel.
 */
@Entity
@Table(name = ConfigName.DATA_DATA_SOURCING_MODEL_TABLE_NAME, schema=ConfigName.DATA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new data data sourcing model.
 */
@NoArgsConstructor

/**
 * Instantiates a new data data sourcing model.
 *
 * @param key the key
 * @param networkDataSourcingBytes the network data sourcing bytes
 */
@AllArgsConstructor
public class DataDataSourcingModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	DataDataSourcingKey key;
 
	/** The network data sourcing bytes. */
	@Column
	byte[] networkDataSourcingBytes;
		
		
	
	
}
