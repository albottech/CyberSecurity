/*
 * 
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.instrument.messaging.TraceMessageHeaders;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;

// TODO: Auto-generated Javadoc
/**
 * {@link org.springframework.messaging.support.ChannelInterceptor} that doesn't
 * trace the tracer.
 *
 * @author Associate-1
 */
public class TracerIgnoringChannelInterceptor extends ChannelInterceptorAdapter {

	/** The span metric reporter. */
	private final SpanMetricReporter spanMetricReporter;

	/**
	 * Instantiates a new tracer ignoring channel interceptor.
	 *
	 * @param spanMetricReporter the span metric reporter
	 */
	public TracerIgnoringChannelInterceptor(SpanMetricReporter spanMetricReporter) {
		this.spanMetricReporter = spanMetricReporter;
	}

	/**
	 * Don't trace the tracer (suppress spans originating from our own source).
	 *
	 * @param message the message
	 * @param channel the channel
	 * @return the message
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		return MessageBuilder.fromMessage(message).setHeader(TraceMessageHeaders.SAMPLED_NAME, Span.SPAN_NOT_SAMPLED)
				.build();
	}

	/**
	 * After send completion.
	 *
	 * @param message the message
	 * @param channel the channel
	 * @param sent the sent
	 * @param ex the ex
	 */
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		if (!(message.getPayload() instanceof TraceActivity)) {
			return;
		}
		// TraceActivity spans = (TraceActivity) message.getPayload();
		// int spanNumber = spans.getSpans().size();
		if (sent) {
			this.spanMetricReporter.incrementAcceptedSpans(1);
		} else {
			this.spanMetricReporter.incrementDroppedSpans(1);
		}
	}
}
