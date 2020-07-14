package io.kryptoblocks.msa.infra.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.infra.repository.key.InfraDataProcessActivityKey;
import io.kryptoblocks.msa.infra.repository.key.InfraDataSourcingKey;
import io.kryptoblocks.msa.infra.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraDataSourcingModel.
 */
@Entity
@Table(name = ConfigName.INFRA_DATA_SOURCING_MODEL_TABLE_NAME, schema=ConfigName.INFRA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new infra data sourcing model.
 */
@NoArgsConstructor

/**
 * Instantiates a new infra data sourcing model.
 *
 * @param key the key
 * @param networkDataSourcingBytes the network data sourcing bytes
 */
@AllArgsConstructor
public class InfraDataSourcingModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	InfraDataSourcingKey key;
 
	/** The network data sourcing bytes. */
	@Column
	byte[] networkDataSourcingBytes;
		
		
	
	
}
