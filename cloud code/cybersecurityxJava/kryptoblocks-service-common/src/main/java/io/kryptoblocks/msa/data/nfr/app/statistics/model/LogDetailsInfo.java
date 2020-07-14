package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.common.util.BaseLogConfig;
import io.kryptoblocks.msa.data.nfr.app.statistics.key.AutoConfigInfoKey;
import io.kryptoblocks.msa.data.nfr.app.statistics.key.LogDetailsInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class LogDetailsInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_LOG_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new log details info.
 */
@NoArgsConstructor

/**
 * Instantiates a new log details info.
 *
 * @param key the key
 * @param logLevels the log levels
 * @param logConfigs the log configs
 */
@AllArgsConstructor
public class LogDetailsInfo {
	
	/** The key. */
	@EmbeddedId
	LogDetailsInfoKey key;
	
	/** The log levels. */
	@Column
	List<String> logLevels;
	
	/** The log configs. */
	@Column
	Map<String, BaseLogConfig> logConfigs;
	
	 
}
