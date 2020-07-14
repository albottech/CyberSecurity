package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

 

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadStackTraceInfo.
 */
@Embeddable

/**
 * Instantiates a new thread stack trace info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new thread stack trace info.
 *
 * @param declaringClass the declaring class
 * @param methodName the method name
 * @param fileName the file name
 * @param lineNumber the line number
 */
@AllArgsConstructor
public class ThreadStackTraceInfo {

	/** The declaring class. */
	@Column 
	String declaringClass;
	
	/** The method name. */
	@Column 
     String methodName;
	
	/** The file name. */
	@Column 
     String fileName;
	
	/** The line number. */
	@Column 
     int    lineNumber;
}
