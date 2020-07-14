package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import java.util.List;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 
// TODO: Auto-generated Javadoc
/**
 * The Class NetworkEndpointConfigInfo.
 */
@Embeddable

/**
 * Instantiates a new network endpoint config info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new network endpoint config info.
 *
 * @param ipv4Address the ipv 4 address
 * @param ipv6Address the ipv 6 address
 * @param linkLocalIPs the link local I ps
 * @param links the links
 * @param aliases the aliases
 * @param gateway the gateway
 * @param ipAddress the ip address
 * @param ipPrefixLen the ip prefix len
 * @param ipv6Gateway the ipv 6 gateway
 * @param globalIPv6Address the global I pv 6 address
 * @param globalIPv6PrefixLen the global I pv 6 prefix len
 * @param macAddress the mac address
 */
@AllArgsConstructor
public class NetworkEndpointConfigInfo {

	/** The ipv 4 address. */
	String ipv4Address;

	/** The ipv 6 address. */
	String ipv6Address;

	/** The link local I ps. */
	List<String> linkLocalIPs;

	/** The links. */
	List<String> links;

	/** The aliases. */
	List<String> aliases;

	/** The gateway. */
	String gateway;

	/** The ip address. */
	String ipAddress;

	/** The ip prefix len. */
	Integer ipPrefixLen;

	/** The ipv 6 gateway. */
	String ipv6Gateway;

	/** The global I pv 6 address. */
	String globalIPv6Address;

	/** The global I pv 6 prefix len. */
	Integer globalIPv6PrefixLen;

	/** The mac address. */
	String macAddress;

}
