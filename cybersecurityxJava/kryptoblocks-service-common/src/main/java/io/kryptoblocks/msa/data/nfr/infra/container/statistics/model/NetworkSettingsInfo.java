package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.NetworkSettingsInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.AttachedNetworkInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
 
// TODO: Auto-generated Javadoc
/**
 * The Class NetworkSettingsInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_NETWORK_SETTINGS_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new network settings info.
 */
@NoArgsConstructor

/**
 * Instantiates a new network settings info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param ipAddress the ip address
 * @param ipPrefixLen the ip prefix len
 * @param gateway the gateway
 * @param bridge the bridge
 * @param portMapping the port mapping
 * @param ports the ports
 * @param macAddress the mac address
 * @param networks the networks
 * @param endpointId the endpoint id
 * @param sandboxId the sandbox id
 * @param sandboxKey the sandbox key
 * @param hairpinMode the hairpin mode
 * @param linkLocalIPv6Address the link local I pv 6 address
 * @param linkLocalIPv6PrefixLen the link local I pv 6 prefix len
 * @param globalIPv6Address the global I pv 6 address
 * @param globalIPv6PrefixLen the global I pv 6 prefix len
 * @param ipv6Gateway the ipv 6 gateway
 */
@AllArgsConstructor
public class NetworkSettingsInfo {
	
	/** The key. */
	@EmbeddedId
	NetworkSettingsInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	/** The ip address. */
	@Column 
	String ipAddress;
	
	/** The ip prefix len. */
	@Column
	Integer ipPrefixLen;
	
	/** The gateway. */
	@Column
	String gateway;
	
	/** The bridge. */
	@Column
	String bridge;
	
	 
	/** The port mapping. */
	@Column
	/*
	 * Map<String, String> to String
	 */
	Map<String, String> portMapping;
	
	
	
	/** The ports. */
	/*
	 * List<PortBinding> to String
	 */
	@Column
	Map<String, String> ports; 

	/** The mac address. */
	@Column
	String macAddress;
	
	/** The networks. */
	@ElementCollection
	Map<String, AttachedNetworkInfo> networks;

	/** The endpoint id. */
	@Column
	String endpointId;

	/** The sandbox id. */
	@Column
	String sandboxId;
	
	/** The sandbox key. */
	@Column
	String sandboxKey;
	
	/** The hairpin mode. */
	@Column
	Boolean hairpinMode;
	
	/** The link local I pv 6 address. */
	@Column
	String linkLocalIPv6Address;
	
	/** The link local I pv 6 prefix len. */
	@Column
	Integer linkLocalIPv6PrefixLen;
	
	/** The global I pv 6 address. */
	@Column
	String globalIPv6Address;
	
	/** The global I pv 6 prefix len. */
	@Column
	Integer globalIPv6PrefixLen;
	
	/** The ipv 6 gateway. */
	@Column
	String ipv6Gateway;

}
