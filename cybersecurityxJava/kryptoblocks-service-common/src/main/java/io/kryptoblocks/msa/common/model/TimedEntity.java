package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.util.Date;

import com.codahale.metrics.Timer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class TimedEntity.
 */
public class TimedEntity implements Serializable  {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
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
	 * Gets the m 1 rate.
	 *
	 * @return the m 1 rate
	 */
	@Getter 
 /**
  * Sets the m 1 rate.
  *
  * @param m1Rate the new m 1 rate
  */
 @Setter(AccessLevel.PUBLIC)
    private Double m1Rate;
	
	/**
	 * Gets the m 5 rate.
	 *
	 * @return the m 5 rate
	 */
	@Getter 
 /**
  * Sets the m 5 rate.
  *
  * @param m5Rate the new m 5 rate
  */
 @Setter(AccessLevel.PUBLIC)
    private Double m5Rate;
	
	/**
	 * Gets the m 15 rate.
	 *
	 * @return the m 15 rate
	 */
	@Getter 
 /**
  * Sets the m 15 rate.
  *
  * @param m15Rate the new m 15 rate
  */
 @Setter(AccessLevel.PUBLIC)
    private Double m15Rate;
	
	/**
	 * Gets the mean rate.
	 *
	 * @return the mean rate
	 */
	@Getter 
 /**
  * Sets the mean rate.
  *
  * @param meanRate the new mean rate
  */
 @Setter(AccessLevel.PUBLIC)
    private Double meanRate;
	
	/**
	 * Gets the snapshot.
	 *
	 * @return the snapshot
	 */
	@Getter 
 /**
  * Sets the snapshot.
  *
  * @param snapshot the new snapshot
  */
 @Setter(AccessLevel.PUBLIC)
    private HistogramEntity snapshot;

    /**
     * Instantiates a new timed entity.
     *
     * @param timer the timer
     */
    public TimedEntity(final Timer timer) {
        count = timer.getCount();
        m1Rate = timer.getOneMinuteRate();
        m5Rate = timer.getFiveMinuteRate();
        m15Rate = timer.getFifteenMinuteRate();
        meanRate = timer.getMeanRate();
        snapshot = new HistogramEntity(timer.getSnapshot());        
    }
    
}