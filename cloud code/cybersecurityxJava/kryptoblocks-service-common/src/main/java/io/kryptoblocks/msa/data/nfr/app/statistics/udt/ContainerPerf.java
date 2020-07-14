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
 * The Class ContainerPerf.
 */
@Embeddable

/**
 * Instantiates a new container perf.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new container perf.
 *
 * @param destination the destination
 * @param reportingTime the reporting time
 * @param serviceName the service name
 * @param gaugesValues the gauges values
 * @param countersValues the counters values
 * @param histogramsValues the histograms values
 * @param metersValues the meters values
 * @param timersValues the timers values
 */
@AllArgsConstructor
public class ContainerPerf implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The destination. */
	@Column
	String destination;

	/** The reporting time. */
	@Column
	String reportingTime;
	
	/** The service name. */
	@Column
	String serviceName;
	
	/** The gauges values. */
	@Column
	Map<String, String> gaugesValues;
	
	/** The counters values. */
	@Column
	Map<String, String> countersValues;
	
	/** The histograms values. */
	@Column
	Map<String, String> histogramsValues;
	
	/** The meters values. */
	@Column
	Map<String, String> metersValues;
	
	/** The timers values. */
	@Column
	Map<String, String> timersValues;	 

}
