/* 
 */

package io.kryptoblocks.msa.data.nfr.trace.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.trace.key.TraceKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Data transfer object for a collection of spans from a given host.
 *
 * @author Associate-1
 * @since 1.0.0
 */
@Entity
@Table(name = ConfigName.TRACE_ACTIVITY_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new trace.
 */
@NoArgsConstructor

/**
 * Instantiates a new trace.
 *
 * @param key the key
 * @param serviceName the service name
 * @param host the host
 * @param port the port
 * @param spansId the spans id
 */
@AllArgsConstructor
public class Trace {
	
	/** The key. */
	@EmbeddedId
	TraceKey key;
	
	/** The service name. */
	@Column
	String serviceName;
	
	/** The host. */
	@Column  
	String host;
	
	/** The port. */
	@Column 
	Integer port;
	
	/** The spans id. */
	@Column
	List<String> spansId;
	
	 
}
