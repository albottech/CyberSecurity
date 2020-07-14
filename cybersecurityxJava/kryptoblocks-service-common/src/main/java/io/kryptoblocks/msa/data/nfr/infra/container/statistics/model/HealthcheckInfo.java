package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.cassandra.mapping.CassandraType;

import com.datastax.driver.core.DataType;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.HealthCheckInfoKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthcheckInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_HEALTH_CHECK_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new healthcheck info.
 */
@NoArgsConstructor

/**
 * Instantiates a new healthcheck info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param test the test
 * @param interval the interval
 * @param timeout the timeout
 * @param retries the retries
 * @param startPerion the start perion
 */
@AllArgsConstructor
public class HealthcheckInfo {
	
	/** The key. */
	@EmbeddedId	
	HealthCheckInfoKey key;	
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The test. */
	@Column 
	List<String> test;
	
	/** The interval. */
	@Column
	Long interval;
	
	/** The timeout. */
	@Column
	Long timeout;
	
	/** The retries. */
	@Column 
	Integer retries;
	
	/** The start perion. */
	@Column
	Long startPerion;
}
