package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import java.util.Map;

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
 * The Class LogConfigInfo.
 */
@Embeddable

/**
 * Instantiates a new log config info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new log config info.
 *
 * @param logType the log type
 * @param logOptions the log options
 */
@AllArgsConstructor
public class LogConfigInfo {
	
	/** The log type. */
	@Column
	String logType;
	
	/** The log options. */
	@Column 
	Map<String, String> logOptions;

}
