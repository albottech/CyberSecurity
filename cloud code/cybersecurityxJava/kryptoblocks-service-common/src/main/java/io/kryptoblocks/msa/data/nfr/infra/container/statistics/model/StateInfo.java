package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.StateInfoKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// TODO: Auto-generated Javadoc
/**
 * The Class StateInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_STATE_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new state info.
 */
@NoArgsConstructor

/**
 * Instantiates a new state info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param status the status
 * @param running the running
 * @param paused the paused
 * @param restarting the restarting
 * @param pid the pid
 * @param exitCode the exit code
 * @param startedAt the started at
 * @param finishedAt the finished at
 * @param error the error
 * @param oomKilled the oom killed
 * @param containerHealthId the container health id
 */
@AllArgsConstructor
public class StateInfo {
	
	/** The key. */
	@EmbeddedId
	StateInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The status. */
	@Column
	String status;
	
	/** The running. */
	@Column
	Boolean running;
    
	/** The paused. */
	@Column
	Boolean paused;

	/** The restarting. */
	@Column
	Boolean restarting;
	
	/** The pid. */
	@Column
	Integer pid;
	
	/** The exit code. */
	@Column
	Integer exitCode;

	/** The started at. */
	@Column
	String startedAt;
	
	/** The finished at. */
	@Column
	String finishedAt;
	
	/** The error. */
	@Column
	String error;
	
	/** The oom killed. */
	@Column
	Boolean oomKilled;
	
	/** The container health id. */
	@Column
	String containerHealthId;

}
