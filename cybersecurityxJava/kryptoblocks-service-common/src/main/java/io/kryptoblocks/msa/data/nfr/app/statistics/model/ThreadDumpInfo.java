package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.ThreadDumpInfoKey;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.ThreadInfo;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.ThreadLockTraceInfo;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.ThreadMonitorInfo;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.ThreadStackTraceInfo;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadDumpInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_THREAD_DUMP_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new thread dump info.
 */
@NoArgsConstructor

/**
 * Instantiates a new thread dump info.
 *
 * @param key the key
 * @param threadInfo the thread info
 * @param threadLockTraceInfo the thread lock trace info
 * @param threadMonitorInfo the thread monitor info
 * @param threadStackTraceInfo the thread stack trace info
 */
@AllArgsConstructor
public class ThreadDumpInfo {
	
	/** The key. */
	@EmbeddedId
	ThreadDumpInfoKey key;
	
	/** The thread info. */
	@ElementCollection 
	Map<String, ThreadInfo> threadInfo;
	
	/** The thread lock trace info. */
	@ElementCollection 
	Map<String, ThreadLockTraceInfo> threadLockTraceInfo;
	
	/** The thread monitor info. */
	@ElementCollection 
	Map<String, ThreadMonitorInfo> threadMonitorInfo;
	
	/** The thread stack trace info. */
	@ElementCollection 
	Map<String, ThreadStackTraceInfo> threadStackTraceInfo;
	
	 
	 
}
