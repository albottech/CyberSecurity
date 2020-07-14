package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.util.Date;

import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


// TODO: Auto-generated Javadoc
/**
 * The Class HistogramEntity.
 */
public class HistogramEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Getter /**
  * Sets the name.
  *
  * @param name the new name
  */
 @Setter(AccessLevel.PUBLIC)
    private String name;
	
	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	@Getter /**
  * Sets the timestamp.
  *
  * @param timestamp the new timestamp
  */
 @Setter(AccessLevel.PUBLIC)
	private Date timestamp;
	
	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	@Getter 
 /**
  * Sets the count.
  *
  * @param count the new count
  */
 @Setter(AccessLevel.PUBLIC)
	private Long count;
	
	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	@Getter 
 /**
  * Sets the max.
  *
  * @param max the new max
  */
 @Setter(AccessLevel.PUBLIC)
    private Long max;
	
	/**
	 * Gets the mean.
	 *
	 * @return the mean
	 */
	@Getter 
 /**
  * Sets the mean.
  *
  * @param mean the new mean
  */
 @Setter(AccessLevel.PUBLIC)
    private Double mean;
	
	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	@Getter 
 /**
  * Sets the min.
  *
  * @param min the new min
  */
 @Setter(AccessLevel.PUBLIC)
    private Long min;
	
	/**
	 * Gets the std dev.
	 *
	 * @return the std dev
	 */
	@Getter 
 /**
  * Sets the std dev.
  *
  * @param stdDev the new std dev
  */
 @Setter(AccessLevel.PUBLIC)
    private Double stdDev;
	
	/**
	 * Gets the median.
	 *
	 * @return the median
	 */
	@Getter 
 /**
  * Sets the median.
  *
  * @param median the new median
  */
 @Setter(AccessLevel.PUBLIC)
    private Double median;
	
	/**
	 * Gets the p75.
	 *
	 * @return the p75
	 */
	@Getter 
 /**
  * Sets the p75.
  *
  * @param p75 the new p75
  */
 @Setter(AccessLevel.PUBLIC)
    private Double p75;
	
	/**
	 * Gets the p95.
	 *
	 * @return the p95
	 */
	@Getter 
 /**
  * Sets the p95.
  *
  * @param p95 the new p95
  */
 @Setter(AccessLevel.PUBLIC)
    private Double p95;
	
	/**
	 * Gets the p98.
	 *
	 * @return the p98
	 */
	@Getter 
 /**
  * Sets the p98.
  *
  * @param p98 the new p98
  */
 @Setter(AccessLevel.PUBLIC)
    private Double p98;
	
	/**
	 * Gets the p99.
	 *
	 * @return the p99
	 */
	@Getter 
 /**
  * Sets the p99.
  *
  * @param p99 the new p99
  */
 @Setter(AccessLevel.PUBLIC)
    private Double p99;
	
	/**
	 * Gets the p999.
	 *
	 * @return the p999
	 */
	@Getter 
 /**
  * Sets the p999.
  *
  * @param p999 the new p999
  */
 @Setter(AccessLevel.PUBLIC)
    private Double p999;

    /**
     * Instantiates a new histogram entity.
     *
     * @param snapshot the snapshot
     */
    public HistogramEntity(final Snapshot snapshot) {
        max = snapshot.getMax();
        mean = snapshot.getMean();
        min = snapshot.getMin();
        stdDev = snapshot.getStdDev();
        median = snapshot.getMedian();
        p75 = snapshot.get75thPercentile();
        p95 = snapshot.get95thPercentile();
        p98 = snapshot.get98thPercentile();
        p99 = snapshot.get99thPercentile();
        p999 = snapshot.get999thPercentile();
    }    
}