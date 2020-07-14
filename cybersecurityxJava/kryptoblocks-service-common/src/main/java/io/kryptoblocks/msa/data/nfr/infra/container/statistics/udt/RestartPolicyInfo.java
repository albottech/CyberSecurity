package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import com.datastax.driver.core.DataType;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



// TODO: Auto-generated Javadoc
/**
 * The Class RestartPolicyInfo.
 */
@Embeddable

/**
 * Instantiates a new restart policy info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new restart policy info.
 *
 * @param name the name
 * @param maxRetryCount the max retry count
 */
@AllArgsConstructor
public class RestartPolicyInfo {
	
	/** The name. */
	@Column
	String name;
	
	/** The max retry count. */
	@Column
	Integer maxRetryCount;

}
