/*
  
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

// TODO: Auto-generated Javadoc
/**
 * Properties related to Trace Stream.
 *
 * @author Associate-1
 * @since 1.0.0
 */
@ConfigurationProperties("spring.sleuth.stream")
public class TraceStreamProperties {
	
	/** The enabled. */
	private boolean enabled = true;
	
	/** The group. */
	private String group = TraceSink.INPUT;
	
	/** The poller. */
	private Poller poller = new Poller();

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return this.group;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Gets the poller.
	 *
	 * @return the poller
	 */
	public Poller getPoller() {
		return this.poller;
	}

	/**
	 * The Class Poller.
	 */
	public static class Poller {
		/**
		 * Fixed delay (ms). Default: 1000
		 */
		private long fixedDelay = 1000L;

		/**
		 * Max messages per poll. Default: -1 (unbounded)
		 */
		private int maxMessagesPerPoll = -1;

		/**
		 * Gets the fixed delay.
		 *
		 * @return the fixed delay
		 */
		public long getFixedDelay() {
			return this.fixedDelay;
		}

		/**
		 * Gets the max messages per poll.
		 *
		 * @return the max messages per poll
		 */
		public int getMaxMessagesPerPoll() {
			return this.maxMessagesPerPoll;
		}

		/**
		 * Sets the fixed delay.
		 *
		 * @param fixedDelay the new fixed delay
		 */
		public void setFixedDelay(long fixedDelay) {
			this.fixedDelay = fixedDelay;
		}

		/**
		 * Sets the max messages per poll.
		 *
		 * @param maxMessagesPerPoll the new max messages per poll
		 */
		public void setMaxMessagesPerPoll(int maxMessagesPerPoll) {
			this.maxMessagesPerPoll = maxMessagesPerPoll;
		}
	}
}
