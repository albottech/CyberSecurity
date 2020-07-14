package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

 
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class NodeInfo.
 */
@Embeddable

/**
 * Instantiates a new node info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new node info.
 *
 * @param id the id
 * @param ip the ip
 * @param addr the addr
 * @param name the name
 */
@AllArgsConstructor
public class NodeInfo {
	
	/** The id. */
	@Column
	String id;
	
	/** The ip. */
	@Column
    String ip;
	
	/** The addr. */
	@Column
    String addr;
	
	/** The name. */
	@Column
    String name;
  }