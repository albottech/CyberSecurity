package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class EndpointConfig.
 */
@Embeddable

/**
 * Instantiates a new endpoint config.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new endpoint config.
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
public class EndpointConfig {
	
	/** The ipv 4 address. */
	@Column
	String ipv4Address;

	/** The ipv 6 address. */
	@Column
	String ipv6Address;
	
	/** The link local I ps. */
	@Column
	List<String> linkLocalIPs;
	
	/** The links. */
	@Column
	List<String> links;
	
	/** The aliases. */
	@Column
	List<String> aliases;
	
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
