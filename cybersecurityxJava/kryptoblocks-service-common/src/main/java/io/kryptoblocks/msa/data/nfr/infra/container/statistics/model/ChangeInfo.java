package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ChangeInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ConfigKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_CHANGE_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new change info.
 */
@NoArgsConstructor

/**
 * Instantiates a new change info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param path the path
 * @param kind the kind
 */
@AllArgsConstructor
public class ChangeInfo {
	
	/** The key. */
	@EmbeddedId
	ChangeInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;		
			
	/** The path. */
	@Column
	String path;
	
	/** The kind. */
	@Column
	Integer kind;
}
