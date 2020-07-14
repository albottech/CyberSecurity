package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.util.Date;

import com.codahale.metrics.Metered;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class MeteredEntity.
 */
public class MeteredEntity  implements Serializable {
	
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
     * Instantiates a new metered entity.
     *
     * @param meter the meter
     */
    public MeteredEntity(final Metered meter) {
        count = meter.getCount();
        m1Rate = meter.getOneMinuteRate();
        m5Rate = meter.getFiveMinuteRate();
        m15Rate = meter.getFifteenMinuteRate();
        meanRate = meter.getMeanRate();
        
    }
}