package io.kryptoblocks.msa.network.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.network.repository.key.NetworkDataSourcingKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataEnrichmentKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataProcessActivityKey;
import io.kryptoblocks.msa.network.repository.key.NetworkDataSORKey;
import io.kryptoblocks.msa.network.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkDataSORModel.
 */
@Entity
@Table(name = ConfigName.NETWORK_DATA_SOR_MODEL_TABLE_NAME, schema=ConfigName.NETWORK_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new network data SOR model.
 */
@NoArgsConstructor

/**
 * Instantiates a new network data SOR model.
 *
 * @param key the key
 * @param networkDataSORBytes the network data SOR bytes
 */
@AllArgsConstructor
public class NetworkDataSORModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	NetworkDataSORKey key;
 
	/** The network data SOR bytes. */
	@Column
	byte[] networkDataSORBytes;
		
		
	
	
}
