package io.kryptoblocks.msa.data.nfr.trace.model;


import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.trace.key.TraceSpankey;
import io.kryptoblocks.msa.data.nfr.trace.udt.TraceSpanLogInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// TODO: Auto-generated Javadoc
/**
 * The Class TraceSpan.
 */
@Entity
@Table(name = ConfigName.TRACE_SPAN_ACTIVITY_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new trace span.
 */
@NoArgsConstructor

/**
 * Instantiates a new trace span.
 *
 * @param key the key
 * @param name the name
 * @param traceId the trace id
 * @param processId the process id
 * @param parents the parents
 * @param begin the begin
 * @param end the end
 * @param durationMicros the duration micros
 * @param remote the remote
 * @param baggage the baggage
 * @param logs the logs
 * @param tags the tags
 * @param traceIdHigh the trace id high
 * @param savedSpanId the saved span id
 */
@AllArgsConstructor
public class TraceSpan {
	
	/** The key. */
	@EmbeddedId
	TraceSpankey key;
	
	/** The name. */
	@Column
	String name;
	
	
	/** The trace id. */
	@Column
	long traceId;
	
	 
	/** The process id. */
	@Column
	String processId;
	
	/** The parents. */
	@Column
	List<Long> parents;
	
	/** The begin. */
	@Column
	long begin;
	
	/** The end. */
	@Column
	long end ;
	
	/** The duration micros. */
	@Column
	Long durationMicros; 
	
	/** The remote. */
	@Column
	boolean remote;
	
	/** The baggage. */
	@Column
	Map<String,String> baggage;
	
	/** The logs. */
	@ElementCollection
	List<TraceSpanLogInfo> logs;
	
	/** The tags. */
	@Column
	Map<String, String> tags;
	
	/** The trace id high. */
	@Column
	long traceIdHigh;
	
	
	/** The saved span id. */
	@Column
	long savedSpanId;
	
	
	
	
}
