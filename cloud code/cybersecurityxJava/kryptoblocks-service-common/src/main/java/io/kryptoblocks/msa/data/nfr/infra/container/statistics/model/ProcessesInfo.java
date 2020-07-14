package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ProcessesKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessesInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_TOP_RESULTS_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new processes info.
 */
@NoArgsConstructor

/**
 * Instantiates a new processes info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param titles the titles
 * @param processes the processes
 */
@AllArgsConstructor
public class ProcessesInfo {
	
	/** The key. */
	@EmbeddedId
	ProcessesKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The titles. */
	@Column
	List<String> titles;
	
	/*
	 * List<String> to String
	 */
	
	/** The processes. */
	@Column
	List<String> processes;

}
