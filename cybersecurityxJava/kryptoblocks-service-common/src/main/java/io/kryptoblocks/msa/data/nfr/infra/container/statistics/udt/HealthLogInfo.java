package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

 

import javax.persistence.Column;
import javax.persistence.Embeddable;


 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthLogInfo.
 */
@Embeddable

/**
 * Instantiates a new health log info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new health log info.
 *
 * @param start the start
 * @param end the end
 * @param exitCode the exit code
 * @param output the output
 */
@AllArgsConstructor
public class HealthLogInfo {
	
	/** The start. */
	@Column
	String start;
	
	/** The end. */
	@Column
    String end;
	
	/** The exit code. */
	@Column
    Integer exitCode;
    
	/** The output. */
	@Column
    String output;
}
