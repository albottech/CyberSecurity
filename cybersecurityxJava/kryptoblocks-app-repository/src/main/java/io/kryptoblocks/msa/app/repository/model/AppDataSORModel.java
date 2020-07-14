package io.kryptoblocks.msa.app.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.app.repository.key.AppDataEnrichmentKey;
import io.kryptoblocks.msa.app.repository.key.AppDataProcessActivityKey;
import io.kryptoblocks.msa.app.repository.key.AppDataSORKey;
import io.kryptoblocks.msa.app.repository.key.AppDataSourcingKey;
import io.kryptoblocks.msa.app.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class AppDataSORModel.
 */
@Entity
@Table(name = ConfigName.APP_DATA_SOR_MODEL_TABLE_NAME, schema=ConfigName.APP_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new app data SOR model.
 */
@NoArgsConstructor

/**
 * Instantiates a new app data SOR model.
 *
 * @param key the key
 * @param networkDataSORBytes the network data SOR bytes
 */
@AllArgsConstructor
public class AppDataSORModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The key. */
	@EmbeddedId
	AppDataSORKey key;
 
	/** The network data SOR bytes. */
	@Column
	byte[] networkDataSORBytes;
		
		
	
	
}
