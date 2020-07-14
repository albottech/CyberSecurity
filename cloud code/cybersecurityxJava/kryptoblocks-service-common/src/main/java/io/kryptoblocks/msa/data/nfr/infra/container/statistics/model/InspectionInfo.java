package io.kryptoblocks.msa.data.nfr.infra.container.statistics.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.key.InspectionInfoKey;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.ContainerMountInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.MountInfo;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt.NodeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// TODO: Auto-generated Javadoc
/**
 * The Class InspectionInfo.
 */
@Entity
@Table(name = ConfigName.INFRA_CONTAINER_INFO_TABLE_NAME, schema = ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new inspection info.
 */
@NoArgsConstructor

/**
 * Instantiates a new inspection info.
 *
 * @param key the key
 * @param instanceId the instance id
 * @param collectedTime the collected time
 * @param path the path
 * @param args the args
 * @param containerConfigId the container config id
 * @param containerHostConfigId the container host config id
 * @param containerStateId the container state id
 * @param image the image
 * @param networkSettingsId the network settings id
 * @param resolvConfPath the resolv conf path
 * @param hostnamePath the hostname path
 * @param hostsPath the hosts path
 * @param driver the driver
 * @param execDriver the exec driver
 * @param processLabel the process label
 * @param mountLabel the mount label
 * @param appArmorProfile the app armor profile
 * @param execIds the exec ids
 * @param logPath the log path
 * @param restartCount the restart count
 * @param mounts the mounts
 * @param node the node
 */
@AllArgsConstructor
public class InspectionInfo {
	
	/** The key. */
	@EmbeddedId
	InspectionInfoKey key;
	
	/** The instance id. */
	@Column
	String instanceId;
	
	/** The collected time. */
	@Column
	String collectedTime;	
	
	
	/** The path. */
	@Column
	String path;
	
	/** The args. */
	@Column
	List<String> args;
	
	/** The container config id. */
	@Column
	String containerConfigId;

	/** The container host config id. */
	@Column
	String containerHostConfigId;

	/** The container state id. */
	@Column
	String containerStateId;

	/** The image. */
	@Column
	String image;
	
	/** The network settings id. */
	@Column
	String networkSettingsId;
	
	/** The resolv conf path. */
	@Column
	String resolvConfPath;
	
	/** The hostname path. */
	@Column	
	String hostnamePath;
	
	/** The hosts path. */
	@Column
	String hostsPath;
	
		
	/** The driver. */
	@Column
	String driver;
	
	/** The exec driver. */
	@Column
	String execDriver;
	
	/** The process label. */
	@Column
	String processLabel;
	
	/** The mount label. */
	@Column
	String mountLabel;
	
	/** The app armor profile. */
	@Column
	String appArmorProfile;
	
	/** The exec ids. */
	@Column
	List<String> execIds;
	
	/** The log path. */
	@Column
	String logPath;
	
	/** The restart count. */
	@Column
	Long restartCount;
	
	/** The mounts. */
	@ElementCollection
	List<ContainerMountInfo> mounts;
	
	/** The node. */
	@Embedded
	NodeInfo node; 

}
