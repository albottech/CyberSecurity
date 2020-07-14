/*
 * 
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

 
// TODO: Auto-generated Javadoc
/**
 * Defines a message channel for instrumented applications to use to send span data to a
 * message broker. The channel accepts data in the form of {@link TraceActivityEntity} to buffer
 * multiple actual span instances in a single message. A client app may occasionally drop
 * spans, and if it does it should attempt to account for and report the number dropped.
 *
 * @author Associate1
 * @see TraceSink
 * @since 1.0.0
 */

public interface TraceSource {

	/** The output. */
	String OUTPUT = "sleuth";

	/**
	 * Output.
	 *
	 * @return the message channel
	 */
	@Output(TraceSource.OUTPUT)
	MessageChannel output();

}