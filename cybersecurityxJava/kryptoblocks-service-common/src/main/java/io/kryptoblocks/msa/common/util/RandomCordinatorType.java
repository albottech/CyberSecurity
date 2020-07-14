/*
 * The utility class for data formatting date requirements.
 * <p>
 * Copyright 2017-2017 MetLife Investment.
 *  
 
 */
package io.kryptoblocks.msa.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kryptoblocks.msa.common.model.DestinationType;

// TODO: Auto-generated Javadoc
/**
 * The Enum RandomCordinatorType.
 *
 * @author Smartride
 * @author Associate-1
 * @version %I%, %G%
 * @since 1.0
 */
public enum RandomCordinatorType {

	/** The bid coordinator. */
	BID_COORDINATOR("BID_COORDINATOR"), /** The ride coordinator. */
 RIDE_COORDINATOR("RIDE_COORDINATOR");

	/** The value. */
	// Date value
	private final String value;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomCordinatorType.class);

	/**
	 * Instantiates a new random cordinator type.
	 *
	 * @param value the value
	 */
	// Private constructor
	private RandomCordinatorType(String value) {
		this.value = value;
	}

	/**
	 * Method to find a Date value.
	 * 
	 * @return {@link String} value of the formatted date
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Find by value.
	 *
	 * @param value the value
	 * @return the random cordinator type
	 */
	public static RandomCordinatorType findByValue (String value) {
        if (value != null) {
            for (RandomCordinatorType RandomCordinatorType : RandomCordinatorType.values()) {
                if (value.equalsIgnoreCase(RandomCordinatorType.getValue())) {
                    return RandomCordinatorType;
                }
            }
        }

        return null;
    }
}
