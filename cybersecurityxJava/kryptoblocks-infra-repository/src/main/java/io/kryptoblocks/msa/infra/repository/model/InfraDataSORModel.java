package io.kryptoblocks.msa.infra.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.infra.repository.key.InfraDataEnrichmentKey;
import io.kryptoblocks.msa.infra.repository.key.InfraDataProcessActivityKey;
import io.kryptoblocks.msa.infra.repository.key.InfraDataSORKey;
import io.kryptoblocks.msa.infra.repository.key.InfraDataSourcingKey;
import io.kryptoblocks.msa.infra.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraDataSORModel.
 */
@Entity
@Table(name = ConfigName.INFRA_DATA_SOR_MODEL_TABLE_NAME, schema=ConfigName.INFRA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new infra data SOR model.
 */
@NoArgsConstructor

/**
 * Instantiates a new infra data SOR model.
 *
 * @param key the key
 * @param networkDataSORBytes the network data SOR bytes
 */
@AllArgsConstructor
public class InfraDataSORModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	InfraDataSORKey key;
 
	/** The network data SOR bytes. */
	@Column
	byte[] networkDataSORBytes;
		
		
	
	
}
