package io.kryptoblocks.msa.data.nfr.infra.engine.statistics.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.engine.statistics.key.SwamInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.engine.statistics.udt.RemoteManagerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SwamInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_ENGINE_SWAM_INFO_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new swam info.
 */
@NoArgsConstructor

/**
 * Instantiates a new swam info.
 *
 * @param key the key
 * @param clusterInfoId the cluster info id
 * @param controlAvailable the control available
 * @param error the error
 * @param localNodeState the local node state
 * @param nodeAddr the node addr
 * @param nodeId the node id
 * @param nodes the nodes
 * @param managers the managers
 * @param remoteManagers the remote managers
 */
@AllArgsConstructor
public class SwamInfo {
	
	/** The key. */
	@EmbeddedId
	SwamInfoKey key;
	
	/** The cluster info id. */
	@Column
	String clusterInfoId;
	  
	/** The control available. */
	@Column
	boolean controlAvailable;

	/** The error. */
	@Column
	String error;

	/** The local node state. */
	@Column
	String localNodeState;

	/** The node addr. */
	@Column
	String nodeAddr;

	/** The node id. */
	@Column
	String nodeId;

	/** The nodes. */
	@Column
	Integer nodes;

	/** The managers. */
	@Column
	Integer managers;
	  
	/** The remote managers. */
	@ElementCollection
	List<RemoteManagerInfo> remoteManagers;

}
