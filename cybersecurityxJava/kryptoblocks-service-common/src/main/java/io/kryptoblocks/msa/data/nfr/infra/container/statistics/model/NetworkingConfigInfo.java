package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;


import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.NetworkingConfigInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.NetworkEndpointConfigInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkingConfigInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_NETWORKING_CONFIG_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new networking config info.
 */
@NoArgsConstructor

/**
 * Instantiates a new networking config info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param endpointsConfig the endpoints config
 */
@AllArgsConstructor
public class NetworkingConfigInfo {
	
	/** The key. */
	@EmbeddedId
	NetworkingConfigInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	
	/** The endpoints config. */
	@ElementCollection 
	Map<String, NetworkEndpointConfigInfo> endpointsConfig;

}