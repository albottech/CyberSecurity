package io.kryptoblocks.msa.common.model;

// TODO: Auto-generated Javadoc
/**
 * The Class TracePollerProperty.
 */
public class TracePollerProperty {
	
	/** The fixed delay. */
	public static long fixedDelay = 1000L;

	/**
	 * Max messages per poll. Default: -1 (unbounded)
	 */
	public static int maxMessagesPerPoll = -1;
	
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
