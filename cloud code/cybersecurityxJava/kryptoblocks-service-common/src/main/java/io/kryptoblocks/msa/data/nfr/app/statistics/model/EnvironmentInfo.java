package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.EnvironmentInfoKey;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class EnvironmentInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_ENVIRONMENT_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new environment info.
 */
@NoArgsConstructor

/**
 * Instantiates a new environment info.
 *
 * @param key the key
 * @param profiles the profiles
 * @param systemProperties the system properties
 * @param inLinedTestProperties the in lined test properties
 * @param systemEnvironment the system environment
 * @param applicationConfig the application config
 * @param cloudClientHsotInfo the cloud client hsot info
 * @param defaultProperties the default properties
 */
@AllArgsConstructor
public class EnvironmentInfo {
	
	/** The key. */
	@EmbeddedId
	EnvironmentInfoKey key;
	
	/** The profiles. */
	@Column
	String profiles;
	
	/** The system properties. */
	@Column
	String systemProperties;
	
	/** The in lined test properties. */
	@Column
	String inLinedTestProperties;
	
	/** The system environment. */
	@Column
	String systemEnvironment;
	
	/** The application config. */
	@Column
	String applicationConfig;
	
	/** The cloud client hsot info. */
	@Column
	String cloudClientHsotInfo;
	
	/** The default properties. */
	@Column
	String defaultProperties;
	
	
	
	
	
	
	
	

}
