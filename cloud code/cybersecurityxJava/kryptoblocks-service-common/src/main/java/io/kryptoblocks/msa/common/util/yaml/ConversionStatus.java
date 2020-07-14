package io.kryptoblocks.msa.common.util.yaml;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ConversionStatus.
 */
public class ConversionStatus {

	/** The empty. */
	static ConversionStatus EMPTY = new ConversionStatus();

	/** The Constant OK. */
	public static final int OK = 0;
	
	/** The Constant WARNING. */
	public static final int WARNING = 1;
	
	/** The Constant ERROR. */
	public static final int ERROR = 2;

	/** The severity. */
	private int severity = 0;
	
	/** The entries. */
	private List<ConversionMessage> entries = new ArrayList<>();

	/**
	 * The Class ConversionMessage.
	 */
	static class ConversionMessage {

		/** The severity. */
		private int severity;
		
		/** The message. */
		private String message;

		/**
		 * Instantiates a new conversion message.
		 *
		 * @param severity the severity
		 * @param message the message
		 */
		public ConversionMessage(int severity, String message) {
			this.severity = severity;
			this.message = message;
		}

		/**
		 * Gets the severity.
		 *
		 * @return the severity
		 */
		public int getSeverity() {
			return severity;
		}

		/**
		 * Gets the message.
		 *
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
	}

	/**
	 * Adds the error.
	 *
	 * @param message the message
	 */
	void addError(String message) {
		entries.add(new ConversionMessage(ERROR, message));
		if (severity < ERROR) {
			severity = ERROR;
		}
	}

	/**
	 * Adds the warning.
	 *
	 * @param message the message
	 */
	void addWarning(String message) {
		entries.add(new ConversionMessage(WARNING, message));
		if (severity < WARNING) {
			severity = WARNING;
		}
	}

	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	public List<ConversionMessage> getEntries() {
		return entries;
	}

	/**
	 * Gets the severity.
	 *
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}

}