package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.SystemInfoKey;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.GarbageCollection;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.Jvm;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.Memory;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.Threads;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class SystemInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_SYSTEM_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new system info.
 */
@NoArgsConstructor

/**
 * Instantiates a new system info.
 *
 * @param key the key
 * @param memory the memory
 * @param memoryFree the memory free
 * @param processor the processor
 * @param upTime the up time
 * @param jvm the jvm
 * @param threads the threads
 * @param classLoaded the class loaded
 * @param garbageCollection the garbage collection
 * @param heap the heap
 */
@AllArgsConstructor
public class SystemInfo {
	
	/** The key. */
	@EmbeddedId
	SystemInfoKey key;
		
	/** The memory. */
	@Column
	Long memory;
	
	/** The memory free. */
	@Column
	Long memoryFree;
	
	/** The processor. */
	@Column
	Integer processor;
	
	/** The up time. */
	@Column
	Long upTime;
	
	/** The jvm. */
	@Embedded
	Jvm jvm;
	
	/** The threads. */
	@Embedded
	Threads threads;
	
	/** The class loaded. */
	@Embedded
	ClassLoader classLoaded;
	
	/** The garbage collection. */
	@Embedded
	GarbageCollection garbageCollection;
	
	/** The heap. */
	@Embedded
	Memory heap;
	
	
	
	
}
