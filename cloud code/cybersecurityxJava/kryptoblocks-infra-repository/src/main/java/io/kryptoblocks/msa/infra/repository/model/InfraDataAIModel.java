package io.kryptoblocks.msa.infra.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.infra.repository.key.InfraDataAIKey;
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
 * The Class InfraDataAIModel.
 */
@Entity
@Table(name = ConfigName.INFRA_DATA_AI_MODEL_TABLE_NAME, schema=ConfigName.INFRA_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new infra data AI model.
 */
@NoArgsConstructor

/**
 * Instantiates a new infra data AI model.
 *
 * @param key the key
 * @param networkDataAIBytes the network data AI bytes
 */
@AllArgsConstructor
public class InfraDataAIModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	InfraDataAIKey key;
 
	/** The network data AI bytes. */
	@Column
	byte[] networkDataAIBytes;
		
		
	
	
}
