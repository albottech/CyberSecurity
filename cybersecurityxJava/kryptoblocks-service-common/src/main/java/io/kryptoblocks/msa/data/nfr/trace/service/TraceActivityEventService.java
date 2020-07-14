package io.kryptoblocks.msa.data.nfr.trace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Log;
import org.springframework.cloud.sleuth.Span;

import io.kryptoblocks.msa.common.model.StreamType;
import io.kryptoblocks.msa.common.stream.sender.TraceActivityStreamMessageSender;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import io.kryptoblocks.msa.data.nfr.trace.key.TraceKey;
import io.kryptoblocks.msa.data.nfr.trace.key.TraceSpankey;
import io.kryptoblocks.msa.data.nfr.trace.model.Trace;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceSpan;
import io.kryptoblocks.msa.data.nfr.trace.udt.TraceSpanLogInfo;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TraceActivityEventService.
 */
public class TraceActivityEventService {

	/** The Constant POLLER. */
	public static final String POLLER = "streamSpanReporterPoller";
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TraceActivityEventService.class);

	/** The cloud client host name. */
	@Value("${spring.cloud.client.hostname}")
	private String cloudClientHostName;

	/** The app name. */
	@Value("${spring.application.name}")
	private String appName;

	/** The app host port. */
	@Value("${server.port}")
	int appHostPort;

	/** The trace activity stream message sender. */
	@Autowired
	TraceActivityStreamMessageSender traceActivityStreamMessageSender;

	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService;

	/** The json util. */
	@Autowired
	JsonUtil jsonUtil;

	/** The logged. */
	boolean logged = false;

	/** The repo enabled. */
	@Value("${repository.enabled}")
	boolean repoEnabled;

	/*
	 * public TraceActivityEventService(HostLocator endpointLocator,
	 * SpanMetricReporter spanMetricReporter ) { this.endpointLocator =
	 * endpointLocator; this.spanMetricReporter = spanMetricReporter; }
	 */

	/**
	 * On start up.
	 */
	@PostConstruct
	public void onStartUp() {

	}

	/**
	 * Process trace.
	 *
	 * @param traceActivity the trace activity
	 */
	public void processTrace(TraceActivity traceActivity) {
		try {

			String streamString = traceActivity.getStreamType();
			StreamType streamType = StreamType.findByValue(streamString);
			String traceActivityAsJson = jsonUtil.getObjectAsJson(traceActivity);
			switch (streamType) {
			case LOG:
				postToLogFile(traceActivityAsJson, traceActivity);
				logged = true;
				break;
			case RABBITMQ:
				postToRabbitMQ(traceActivityAsJson);
				break;

			case KAFKA:
				postToKafka(traceActivityAsJson);
				break;

			default:
				if (!logged) {
					postToLogFile(traceActivityAsJson, traceActivity);
				}
				if (repoEnabled) {
					postTOCassandra(traceActivity);
				}
				break;
			}

		} catch (Exception e) {
			LOGGER.error("exception in trace event processing: {}", ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to kafka.
	 *
	 * @param businessActivity the business activity
	 */
	private void postToKafka(String businessActivity) {
		// TODO
	}

	/**
	 * Post to rabbit MQ.
	 *
	 * @param traceActivity the trace activity
	 */
	private void postToRabbitMQ(String traceActivity) {
		try {
			LOGGER.debug("sending trace activity message to the rabbit mq: {}", traceActivity);
			traceActivityStreamMessageSender.processMessage(traceActivity);
		} catch (Exception e) {
			LOGGER.error("exception in business activity post to the rabbit MQ method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Post to log file.
	 *
	 * @param traceString the trace string
	 * @param traceActivity the trace activity
	 */
	private void postToLogFile(String traceString, TraceActivity traceActivity) {
		try {
			Logger traceLogger = LoggerFactory.getLogger("TraceLogger");
			traceLogger.info(traceString);
			if (repoEnabled) {
				postTOCassandra(traceActivity);
			}
		} catch (Exception e) {
			LOGGER.error("exception in trace activity post to log file method: {}",
					ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * Post TO cassandra.
	 *
	 * @param traceActivity the trace activity
	 */
	private void postTOCassandra(TraceActivity traceActivity) {
		try {
			Object[] traceAndSpan = getTraceSpanFromSpan(traceActivity.getSpan());
			Trace trace = (Trace) traceAndSpan[0];
			@SuppressWarnings("unchecked")
			ArrayList<TraceSpan> traceSpans = (ArrayList<TraceSpan>) traceAndSpan[1];

			repositoryService.saveRepositoryObject(trace);
			for (TraceSpan traceSpan : traceSpans) {
				repositoryService.saveRepositoryObject(traceSpan);
			}
		} catch (Exception e) {
			LOGGER.error("exception in post to cassandra methos for trace activity service: {}",
					ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * Gets the trace span from span.
	 *
	 * @param span the span
	 * @return the trace span from span
	 */
	private Object[] getTraceSpanFromSpan(Span span) {
		Object[] returnValue = new Object[2];

		Trace trace = new Trace();
		TraceKey traceKey = new TraceKey();
		traceKey.setId(span.getTraceId());
		traceKey.setName(span.getName());
		trace.setKey(traceKey);
		trace.setHost(cloudClientHostName);
		trace.setServiceName(appName);
		trace.setPort(appHostPort);
		List<TraceSpan> traceSpanList = new ArrayList<TraceSpan>();
		List<String> spanIds = new ArrayList<String>();
		TraceSpan traceSpan = makeTraceSpan(span);
		spanIds.add(Long.toString(traceSpan.getKey().getTraceSpanId()));
		traceSpanList.add(makeTraceSpan(span));

		if (span.hasSavedSpan()) {
			// TODO
		}
		trace.setSpansId(spanIds);
		returnValue[0] = trace;
		returnValue[1] = traceSpanList;
		return returnValue;
	}

	/**
	 * Make trace span.
	 *
	 * @param span the span
	 * @return the trace span
	 */
	private TraceSpan makeTraceSpan(Span span) {

		TraceSpan traceSpan = new TraceSpan();
		TraceSpankey key = new TraceSpankey();
		key.setTraceSpanId(span.getSpanId());
		key.setId(UUID.randomUUID().toString());
		traceSpan.setName(span.getName());
		traceSpan.setTraceId(span.getTraceId());		
		traceSpan.setProcessId(span.getProcessId());
		traceSpan.setParents(span.getParents());
		traceSpan.setBegin(span.getBegin());
		traceSpan.setEnd(span.getEnd());
		traceSpan.setDurationMicros(span.getAccumulatedMicros());
		traceSpan.setRemote(span.isRemote());
		traceSpan.setBaggage(span.getBaggage());
		traceSpan.setKey(key);
		List<Log> logs = span.logs();
		List<TraceSpanLogInfo> traceSpanLogInfoList = new ArrayList<TraceSpanLogInfo>();
		for (Log log : logs) {
			TraceSpanLogInfo traceSpanLogInfo = new TraceSpanLogInfo();
			traceSpanLogInfo.setEvent(log.getEvent());
			traceSpanLogInfo.setTimestamp(log.getTimestamp());
			traceSpanLogInfoList.add(traceSpanLogInfo);
		}
		traceSpan.setLogs(traceSpanLogInfoList);
		traceSpan.setTags(span.tags());
		traceSpan.setTraceIdHigh(span.getTraceIdHigh());
		if (span.hasSavedSpan()) {
			Span savedSpan = span.getSavedSpan();
			traceSpan.setSavedSpanId(savedSpan.getSpanId());
		}
		return traceSpan;
	}
}
