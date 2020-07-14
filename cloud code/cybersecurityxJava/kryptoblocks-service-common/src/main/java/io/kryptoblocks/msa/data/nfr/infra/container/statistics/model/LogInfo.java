package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.LogDetailsInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.LogInfoKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class LogInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_LOG_INFO_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new log info.
 */
@NoArgsConstructor

/**
 * Instantiates a new log info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param log the log
 */
@AllArgsConstructor
public class LogInfo {
	
	/** The key. */
	@EmbeddedId
	LogInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The log. */
	@Column
	String log;
	
	 
}
