/*
 *  
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

 
// TODO: Auto-generated Javadoc
/**
 * Defines a message channel for accepting and processing span data from remote,
 * instrumented applications. Span data comes into the channel in the form of
 * {@link TraceActivityEntity}, buffering multiple actual spans into a single payload.
 *
 * @author Associate-1
 * @see TraceSource
 * @since 1.0.0
 */
 
public interface TraceSink {

	/** The input. */
	String INPUT = "sleuth";

	/**
	 * Input.
	 *
	 * @return the subscribable channel
	 */
	@Input(TraceSink.INPUT)
	SubscribableChannel input();
}
