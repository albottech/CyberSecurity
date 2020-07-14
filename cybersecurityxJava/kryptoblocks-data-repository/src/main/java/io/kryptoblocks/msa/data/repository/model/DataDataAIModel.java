package io.kryptoblocks.msa.data.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.repository.key.DataDataAIKey;
import io.kryptoblocks.msa.data.repository.key.DataDataEnrichmentKey;
import io.kryptoblocks.msa.data.repository.key.DataDataProcessActivityKey;
import io.kryptoblocks.msa.data.repository.key.DataDataSORKey;
import io.kryptoblocks.msa.data.repository.key.DataDataSourcingKey;
import io.kryptoblocks.msa.data.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DataDataAIModel.
 */
@Entity
@Table(name = ConfigName.DATA_DATA_AI_MODEL_TABLE_NAME, schema=ConfigName.DATA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new data data AI model.
 */
@NoArgsConstructor

/**
 * Instantiates a new data data AI model.
 *
 * @param key the key
 * @param networkDataAIBytes the network data AI bytes
 */
@AllArgsConstructor
public class DataDataAIModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	DataDataAIKey key;
 
	/** The network data AI bytes. */
	@Column
	byte[] networkDataAIBytes;
		
		
	
	
}