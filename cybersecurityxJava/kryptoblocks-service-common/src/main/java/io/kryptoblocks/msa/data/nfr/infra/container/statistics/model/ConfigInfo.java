package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.ConfigKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_CONFIG_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new config info.
 */
@NoArgsConstructor

/**
 * Instantiates a new config info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param hostname the hostname
 * @param domainname the domainname
 * @param user the user
 * @param attachStdin the attach stdin
 * @param attachStdout the attach stdout
 * @param attachStderr the attach stderr
 * @param portSpecs the port specs
 * @param exposedPorts the exposed ports
 * @param tty the tty
 * @param openStdin the open stdin
 * @param stdinOnce the stdin once
 * @param env the env
 * @param cmd the cmd
 * @param image the image
 * @param volumeNames the volume names
 * @param volumes the volumes
 * @param workingDir the working dir
 * @param entrypoint the entrypoint
 * @param networkDisabled the network disabled
 * @param onBuild the on build
 * @param labels the labels
 * @param macAddress the mac address
 * @param hostConfigKey the host config key
 * @param stopSignal the stop signal
 * @param healthcheckId the healthcheck id
 * @param networkingConfigId the networking config id
 */
@AllArgsConstructor
public class ConfigInfo {
	
	/** The key. */
	@EmbeddedId
	ConfigKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The hostname. */
	@Column
	String hostname;

	/** The domainname. */
	@Column
	String domainname;
	
	/** The user. */
	@Column
	String user;
	
	/** The attach stdin. */
	@Column
	Boolean attachStdin;
	
	/** The attach stdout. */
	@Column
	Boolean attachStdout;

	/** The attach stderr. */
	@Column
	Boolean attachStderr;
	
	/** The port specs. */
	@Column
	List<String> portSpecs;

	/** The exposed ports. */
	@Column
	List<String> exposedPorts;

	/** The tty. */
	@Column
	Boolean tty;
	
	/** The open stdin. */
	@Column
	Boolean openStdin;

	/** The stdin once. */
	@Column
	Boolean stdinOnce;

	/** The env. */
	@Column
	List<String> env;

	/** The cmd. */
	@Column
	List<String> cmd;

	/** The image. */
	@Column
	String image;
	
	/** The volume names. */
	@Column
	List<String> volumeNames;
	
	/** The volumes. */
	/*
	 * Map to String
	 */
	@Column
	Map<String, String> volumes;
	
	/** The working dir. */
	@Column
	String workingDir;
	
	/** The entrypoint. */
	@Column
	List<String> entrypoint;

	/** The network disabled. */
	@Column
	Boolean networkDisabled;

	/** The on build. */
	@Column
	List<String> onBuild;

	/** The labels. */
	@Column
	Map<String, String> labels;

	/** The mac address. */
	@Column
	String macAddress;
	
	/** The host config key. */
	@Column
	String hostConfigKey;

	/** The stop signal. */
	@Column
	String stopSignal;

	/** The healthcheck id. */
	@Column
	String healthcheckId;

	/** The networking config id. */
	@Column
	String networkingConfigId;

}
