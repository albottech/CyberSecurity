package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 */
@Embeddable

/**
 * Instantiates a new logger.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new logger.
 *
 * @param loggerName the logger name
 * @param configuredLevel the configured level
 * @param activeLevel the active level
 */
@AllArgsConstructor
public class Logger implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The logger name. */
	@Column
	String loggerName;

	/** The configured level. */
	@Column
	String configuredLevel;
	
	/** The active level. */
	@Column
	String activeLevel;
	
	 

}
