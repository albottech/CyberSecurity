package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

 

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadMonitorInfo.
 */
@Embeddable

/**
 * Instantiates a new thread monitor info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new thread monitor info.
 *
 * @param stackDepth the stack depth
 * @param stackFrameDeclaringClass the stack frame declaring class
 * @param stackFrameMethodName the stack frame method name
 * @param stackFrameFileName the stack frame file name
 * @param stackFrameLineNumber the stack frame line number
 */
@AllArgsConstructor
public class ThreadMonitorInfo {
	
	/** The stack depth. */
	@Column
	int    stackDepth;
	
	 
	/** The stack frame declaring class. */
	@Column
	String stackFrameDeclaringClass;
	
	/** The stack frame method name. */
	@Column
    String stackFrameMethodName;
	
	/** The stack frame file name. */
	@Column
    String stackFrameFileName;
	
	/** The stack frame line number. */
	@Column
    int    stackFrameLineNumber;
	
}
