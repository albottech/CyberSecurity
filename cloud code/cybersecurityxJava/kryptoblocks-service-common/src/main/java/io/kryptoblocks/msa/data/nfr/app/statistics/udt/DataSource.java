package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DataSource.
 */
@Embeddable

/**
 * Instantiates a new data source.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new data source.
 *
 * @param poolUsage the pool usage
 * @param activeConnection the active connection
 * @param maximumConnection the maximum connection
 * @param minimumConnection the minimum connection
 * @param validationQuery the validation query
 */
@AllArgsConstructor
public class DataSource implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pool usage. */
	@Column
	Integer poolUsage;
	
	/** The active connection. */
	@Column
	Integer activeConnection;
	
	/** The maximum connection. */
	@Column
	Integer maximumConnection;
	
	/** The minimum connection. */
	@Column
	Integer minimumConnection;
	
	/** The validation query. */
	@Column
	String validationQuery;
	
	
}
