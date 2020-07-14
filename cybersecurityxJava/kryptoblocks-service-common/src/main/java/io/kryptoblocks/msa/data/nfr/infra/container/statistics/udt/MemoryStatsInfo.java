package io.kryptoblocks.msa.data.nfr.infra.container.statistics.udt;

import java.math.BigInteger;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.UserDefinedType;
import com.datastax.driver.core.DataType;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class MemoryStatsInfo.
 */
@Embeddable

/**
 * Instantiates a new memory stats info.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new memory stats info.
 *
 * @param maxUsage the max usage
 * @param usage the usage
 * @param failcnt the failcnt
 * @param limit the limit
 * @param activeFile the active file
 * @param totalActiveFile the total active file
 * @param inactiveFile the inactive file
 * @param totalInactiveFile the total inactive file
 * @param cache the cache
 * @param totalCache the total cache
 * @param activeAnon the active anon
 * @param totalActiveAnon the total active anon
 * @param inactiveAnon the inactive anon
 * @param totalInactiveAnon the total inactive anon
 * @param hierarchicalMemoryLimit the hierarchical memory limit
 * @param mappedFile the mapped file
 * @param totalMappedFile the total mapped file
 * @param pgmajfault the pgmajfault
 * @param totalPgmajfault the total pgmajfault
 * @param pgpgin the pgpgin
 * @param totalPgpgin the total pgpgin
 * @param pgpgout the pgpgout
 * @param totalPgpgout the total pgpgout
 * @param pgfault the pgfault
 * @param totalPgfault the total pgfault
 * @param rss the rss
 * @param totalRss the total rss
 * @param rssHuge the rss huge
 * @param totalRssHuge the total rss huge
 * @param unevictable the unevictable
 * @param totalUnevictable the total unevictable
 * @param totalWriteback the total writeback
 * @param writeback the writeback
 */
@AllArgsConstructor 
public class MemoryStatsInfo {
	

	
	/** The max usage. */
	@Column
	Long maxUsage;
	
	/** The usage. */
	@Column
	Long usage;
	
	/** The failcnt. */
	@Column
	Long failcnt;
	
	/** The limit. */
	@Column
	Long limit;
	
	/** The active file. */
	@Column
    Long activeFile;
	
	/** The total active file. */
	@Column
    Long totalActiveFile;
    
	/** The inactive file. */
	@Column
	Long inactiveFile;
	
	/** The total inactive file. */
	@Column
    Long totalInactiveFile;
	
	/** The cache. */
	@Column
    Long cache;
	
	/** The total cache. */
	@Column
    Long totalCache;
	
	/** The active anon. */
	@Column
    Long activeAnon;
	
	/** The total active anon. */
	@Column
    Long totalActiveAnon;
	
	/** The inactive anon. */
	@Column
    Long inactiveAnon;
	
	/** The total inactive anon. */
	@Column
    Long totalInactiveAnon;
	
	/** The hierarchical memory limit. */
	@Column
    BigInteger hierarchicalMemoryLimit;
	
	/** The mapped file. */
	@Column
    Long mappedFile;
	
	/** The total mapped file. */
	@Column
    Long totalMappedFile;
	
	/** The pgmajfault. */
	@Column
    Long pgmajfault;
	
	/** The total pgmajfault. */
	@Column
    Long totalPgmajfault;
	
	/** The pgpgin. */
	@Column
    Long pgpgin;
	
	/** The total pgpgin. */
	@Column
    Long totalPgpgin;
	
	/** The pgpgout. */
	@Column
    Long pgpgout;
	
	/** The total pgpgout. */
	@Column
    Long totalPgpgout;
	
	/** The pgfault. */
	@Column
    Long pgfault;
	
	/** The total pgfault. */
	@Column
    Long totalPgfault;
	
	/** The rss. */
	@Column
    Long rss;
	
	/** The total rss. */
	@Column
    Long totalRss;
	
	/** The rss huge. */
	@Column
    Long rssHuge;
	
	/** The total rss huge. */
	@Column
	Long totalRssHuge;
	
	/** The unevictable. */
	@Column
    Long unevictable;
	
	/** The total unevictable. */
	@Column
    Long totalUnevictable;
	
	/** The total writeback. */
	@Column
    Long totalWriteback;
	
	/** The writeback. */
	@Column
    Long writeback;

}
