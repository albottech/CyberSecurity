package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceInfo.
 */
@Embeddable

/**
 * Instantiates a new device info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new device info.
 *
 * @param pathOnHost the path on host
 * @param pathInContainer the path in container
 * @param cgroupPermissions the cgroup permissions
 */
@AllArgsConstructor
public class DeviceInfo {
	
	/** The path on host. */
	@Column
	String pathOnHost;
	
	/** The path in container. */
	@Column
	String pathInContainer;
	
	/** The cgroup permissions. */
	@Column
	String cgroupPermissions;

}
