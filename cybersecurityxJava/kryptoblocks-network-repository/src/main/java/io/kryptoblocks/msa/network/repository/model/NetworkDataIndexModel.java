package io.kryptoblocks.msa.network.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.network.repository.key.NetworkDataSourcingKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataAIKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataEnrichmentKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataIndexKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataProcessActivityKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataSORKey;
import io.kryptoblocks.msa.network.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkDataIndexModel.
 */
@Entity
@Table(name = ConfigName.NETWORK_DATA_INDEX_MODEL_TABLE_NAME, schema=ConfigName.NETWORK_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new network data index model.
 */
@NoArgsConstructor

/**
 * Instantiates a new network data index model.
 *
 * @param key the key
 * @param networkDataAIBytes the network data AI bytes
 */
@AllArgsConstructor
public class NetworkDataIndexModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	NetworkDataIndexKey key;
 
	/** The network data AI bytes. */
	@Column
	byte[] networkDataAIBytes;
		
		
	
	
}
