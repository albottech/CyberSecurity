package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class AttachedNetworkInfo.
 */
@Embeddable

/**
 * Instantiates a new attached network info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new attached network info.
 *
 * @param aliases the aliases
 * @param networkId the network id
 * @param endpointId the endpoint id
 * @param gateway the gateway
 * @param ipAddress the ip address
 * @param ipPrefixLen the ip prefix len
 * @param ipv6Gateway the ipv 6 gateway
 * @param globalIPv6Address the global I pv 6 address
 * @param globalIPv6PrefixLen the global I pv 6 prefix len
 * @param macAddress the mac address
 */
@AllArgsConstructor
public class AttachedNetworkInfo {
	
	/** The aliases. */
	@Column
	List<String> aliases;
	
	/** The network id. */
	@Column
	String networkId;
	
	/** The endpoint id. */
	@Column
	String endpointId;
	
	/** The gateway. */
	@Column
	String gateway;
	
	/** The ip address. */
	@Column
	String ipAddress;
	
	/** The ip prefix len. */
	@Column
	Integer ipPrefixLen;
	
	/** The ipv 6 gateway. */
	@Column
	String ipv6Gateway;
	
	/** The global I pv 6 address. */
	@Column
	String globalIPv6Address;
	
	/** The global I pv 6 prefix len. */
	@Column
	Integer globalIPv6PrefixLen;
	
	/** The mac address. */
	@Column
	String macAddress;

}
