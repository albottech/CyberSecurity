package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class MountInfo.
 */
@Embeddable

/**
 * Instantiates a new mount info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new mount info.
 *
 * @param type the type
 * @param name the name
 * @param source the source
 * @param target the target
 * @param readOnly the read only
 * @param volumeOptionsDriver the volume options driver
 * @param volumeOptionsLabels the volume options labels
 * @param volumeOptionsNoCopy the volume options no copy
 * @param tmfOptionsMode the tmf options mode
 * @param tmfOptionsSizeBytes the tmf options size bytes
 * @param bindOptionsPropagation the bind options propagation
 */
@AllArgsConstructor
public class MountInfo {
	
	/** The type. */
	@Column
	String type;
	
	/** The name. */
	@Column
	String name;
	
	/** The source. */
	@Column
	String source;
	
	/** The target. */
	@Column
	String target;
	
	/** The read only. */
	@Column
	boolean readOnly;
	
	/** The volume options driver. */
	@Column
	String volumeOptionsDriver;
	
	/** The volume options labels. */
	@Column
	String volumeOptionsLabels;
	
	/** The volume options no copy. */
	@Column
	boolean volumeOptionsNoCopy;
	
	/** The tmf options mode. */
	@Column
	Integer tmfOptionsMode;
	
	/** The tmf options size bytes. */
	@Column
	long tmfOptionsSizeBytes;
	
		
	/** The bind options propagation. */
	@Column
	String bindOptionsPropagation;
}