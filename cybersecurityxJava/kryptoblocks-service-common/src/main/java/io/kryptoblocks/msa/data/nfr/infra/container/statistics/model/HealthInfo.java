package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

 
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.HealthInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.HealthLogInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 

// TODO: Auto-generated Javadoc
/**
 * The Class HealthInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_HEALTH_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new health info.
 */
@NoArgsConstructor

/**
 * Instantiates a new health info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param status the status
 * @param failingStreak the failing streak
 * @param log the log
 */
@AllArgsConstructor
public class HealthInfo {
	
	
	/** The key. */
	@EmbeddedId
	HealthInfoKey key;	
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The status. */
	@Column
	 String status;
	
	/** The failing streak. */
	@Column
	 Integer failingStreak;
		
	/** The log. */
	@ElementCollection
	List<HealthLogInfo> log;	

}
