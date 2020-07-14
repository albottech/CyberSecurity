package io.kryptoblocks.msa.data.nfr.infra.engine.statistics.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.spotify.docker.client.messages.swarm.RemoteManager;
import com.spotify.docker.client.messages.swarm.SwarmCluster;
import com.spotify.docker.client.messages.swarm.SwarmSpec;
import com.spotify.docker.client.messages.swarm.Version;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.engine.statistics.key.ClusterInfoKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ClusterInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_ENGINE_SWAM_CLUSTER_INFO_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new cluster info.
 */
@NoArgsConstructor

/**
 * Instantiates a new cluster info.
 *
 * @param key the key
 * @param version the version
 * @param createdAt the created at
 * @param updatedAt the updated at
 * @param swarmSpecId the swarm spec id
 */
@AllArgsConstructor
public class ClusterInfo {
	
	/** The key. */
	@EmbeddedId
	ClusterInfoKey key;

	/** The version. */
	@Column
	Version version;

	/** The created at. */
	@Column
	Date createdAt;

	/** The updated at. */
	@Column
	Date updatedAt;

	/** The swarm spec id. */
	@Column
	String swarmSpecId;

}
