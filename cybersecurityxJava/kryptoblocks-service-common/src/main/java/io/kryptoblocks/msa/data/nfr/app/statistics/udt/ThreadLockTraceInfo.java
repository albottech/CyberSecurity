package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

 
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadLockTraceInfo.
 */
@Embeddable

/**
 * Instantiates a new thread lock trace info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new thread lock trace info.
 *
 * @param className the class name
 * @param identityHashCode the identity hash code
 */
@AllArgsConstructor
public class ThreadLockTraceInfo {
	
	/** The class name. */
	@Column
	String className;
	
	/** The identity hash code. */
	@Column
    int    identityHashCode;
	
}
