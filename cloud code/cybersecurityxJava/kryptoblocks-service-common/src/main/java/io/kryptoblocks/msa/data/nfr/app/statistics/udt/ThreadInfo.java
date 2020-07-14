package io.kryptoblocks.msa.data.nfr.app.statistics.udt;

import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadInfo.
 */
@Embeddable

/**
 * Instantiates a new thread info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new thread info.
 *
 * @param threadName the thread name
 * @param threadId the thread id
 * @param blockedTime the blocked time
 * @param blockedCount the blocked count
 * @param waitedTime the waited time
 * @param waitedCount the waited count
 * @param lock the lock
 * @param lockName the lock name
 * @param lockOwnerId the lock owner id
 * @param lockOwnerName the lock owner name
 * @param inNative the in native
 * @param suspended the suspended
 * @param threadState the thread state
 * @param stackTraceListId the stack trace list id
 * @param monitorInfoListId the monitor info list id
 * @param lockInfoListId the lock info list id
 */
@AllArgsConstructor
public class ThreadInfo {

	/** The thread name. */
	@Column
	String threadName;

	/** The thread id. */
	@Column
	long threadId;

	/** The blocked time. */
	@Column
	long blockedTime;

	/** The blocked count. */
	@Column
	long blockedCount;

	/** The waited time. */
	@Column
	long waitedTime;

	/** The waited count. */
	@Column
	long waitedCount;

	/** The lock. */
	@Column
	LockInfo lock;
	
	/** The lock name. */
	@Column
	String lockName;

	/** The lock owner id. */
	@Column
	long lockOwnerId;

	/** The lock owner name. */
	@Column
	String lockOwnerName;

	/** The in native. */
	@Column
	boolean inNative;

	/** The suspended. */
	@Column
	boolean suspended;

	/** The thread state. */
	@Column
	String threadState;
	
	/** The stack trace list id. */
	@Column
	String stackTraceListId;
	
	/** The monitor info list id. */
	@Column
    String monitorInfoListId;   
	
	/** The lock info list id. */
	@Column
    String lockInfoListId;

}
