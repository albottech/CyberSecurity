package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class PerformanceEntity.
 */
@JsonIgnoreProperties( { "destination" })
public class PerformanceEntity implements Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	@Getter /**
  * Sets the destination.
  *
  * @param destination the new destination
  */
 @Setter(AccessLevel.PUBLIC)
	private String destination;

	/**
	 * Gets the reporting time.
	 *
	 * @return the reporting time
	 */
	@Getter /**
  * Sets the reporting time.
  *
  * @param reportingTime the new reporting time
  */
 @Setter(AccessLevel.PUBLIC)
	private Date reportingTime;
	
	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	@Getter /**
  * Sets the service name.
  *
  * @param serviceName the new service name
  */
 @Setter(AccessLevel.PUBLIC)
	private String serviceName;
	
	/**
	 * Gets the gauges values.
	 *
	 * @return the gauges values
	 */
	@Getter /**
  * Sets the gauges values.
  *
  * @param gaugesValues the gauges values
  */
 @Setter(AccessLevel.PUBLIC)
	private SortedMap<String, Object> gaugesValues;
	
	/**
	 * Gets the counters values.
	 *
	 * @return the counters values
	 */
	@Getter /**
  * Sets the counters values.
  *
  * @param countersValues the counters values
  */
 @Setter(AccessLevel.PUBLIC)
	private SortedMap<String, Object> countersValues;
	
	/**
	 * Gets the histograms values.
	 *
	 * @return the histograms values
	 */
	@Getter /**
  * Sets the histograms values.
  *
  * @param histogramsValues the histograms values
  */
 @Setter(AccessLevel.PUBLIC)
	private SortedMap<String, Object> histogramsValues;
	
	/**
	 * Gets the meters values.
	 *
	 * @return the meters values
	 */
	@Getter /**
  * Sets the meters values.
  *
  * @param metersValues the meters values
  */
 @Setter(AccessLevel.PUBLIC)
	private SortedMap<String, Object> metersValues;
	
	/**
	 * Gets the timers values.
	 *
	 * @return the timers values
	 */
	@Getter /**
  * Sets the timers values.
  *
  * @param timersValues the timers values
  */
 @Setter(AccessLevel.PUBLIC)
	private SortedMap<String, Object> timersValues;
	
	
	/**
	 * Instantiates a new performance entity.
	 */
	public PerformanceEntity() {
		
		setGaugesValues(new TreeMap<String, Object>());
		setCountersValues(new TreeMap<String, Object>());
		setHistogramsValues(new TreeMap<String, Object>());
		setMetersValues(new TreeMap<String, Object>());
		setTimersValues(new TreeMap<String, Object>());
	}

}
