/*
 * 
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.cloud.sleuth.Log;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.MessageEndpoint;

import io.kryptoblocks.msa.data.nfr.trace.event.TraceActivityEventSender;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;

// TODO: Auto-generated Javadoc
/**
 * A message source for spans. Also handles RPC flavored annotations.
 *
 * @author Ass-1
 * @since 1.0.0
 */
@MessageEndpoint
public class TraceSpanStreamReporter implements SpanReporter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/** The Constant RPC_EVENTS. */
	private static final List<String> RPC_EVENTS = Arrays.asList(Span.CLIENT_RECV, Span.CLIENT_SEND, Span.SERVER_RECV,
			Span.SERVER_SEND);

	/**
	 * Bean name for the
	 * {@link org.springframework.integration.scheduling.PollerMetadata
	 * PollerMetadata}
	 */
	public static final String POLLER = "streamSpanReporterPoller";

	/** The environment. */
	private final Environment environment;
	
	/** The span adjusters. */
	private final List<SpanAdjuster> spanAdjusters;

	/** The trace activity event sender. */
	@Autowired
	TraceActivityEventSender traceActivityEventSender;	
	
	/** The trace activity. */
	@Autowired
	TraceActivity traceActivity ;


	/**
	 * Instantiates a new trace span stream reporter.
	 *
	 * @param environment the environment
	 * @param spanAdjusters the span adjusters
	 */
	public TraceSpanStreamReporter(Environment environment, List<SpanAdjuster> spanAdjusters) {
		this.environment = environment;
		this.spanAdjusters = spanAdjusters;
	}

	/**
	 * Report.
	 *
	 * @param span the span
	 */
	@Override
	public void report(Span span) {
		Span spanToReport = span;
		LOGGER.debug("span to report input: {}", spanToReport);
		if (spanToReport.isExportable()) {
			LOGGER.debug("span is exportable");
			try {
				if (this.environment != null) {
					processLogs(spanToReport);
				}
				for (SpanAdjuster adjuster : this.spanAdjusters) {
					spanToReport = adjuster.adjust(spanToReport);
				}
				LOGGER.debug("adjusted span :{}", spanToReport);				 
				traceActivity.setSpan(spanToReport);
				traceActivityEventSender.sendServiceTrace(traceActivity);
			} catch (Exception e) {

			}
		} else {
			LOGGER.debug("span is not exportable");

		}
	}

	/**
	 * Process logs.
	 *
	 * @param span the span
	 */
	private void processLogs(Span span) {
		for (Log spanLog : span.logs()) {
			if (RPC_EVENTS.contains(spanLog.getEvent())) {
				span.tag(Span.INSTANCEID, IdUtils.getDefaultInstanceId(this.environment));
			}
		}
	}

}
