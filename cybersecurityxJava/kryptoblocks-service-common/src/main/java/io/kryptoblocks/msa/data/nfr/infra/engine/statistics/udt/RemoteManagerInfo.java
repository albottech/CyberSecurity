package io.kryptoblocks.msa.data.nfr.infra.engine.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 
// TODO: Auto-generated Javadoc
/**
 * The Class RemoteManagerInfo.
 */
@Embeddable

/**
 * Instantiates a new remote manager info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new remote manager info.
 *
 * @param addr the addr
 * @param nodeId the node id
 */
@AllArgsConstructor
public class RemoteManagerInfo {
	
	/** The addr. */
	@Column
	String addr;

	  /** The node id. */
  	@Column
	  String nodeId;


}
