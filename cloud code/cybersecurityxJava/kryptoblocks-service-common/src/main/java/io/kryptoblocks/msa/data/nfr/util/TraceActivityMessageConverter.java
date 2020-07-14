package io.kryptoblocks.msa.data.nfr.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.sleuth.Log;
import org.springframework.cloud.sleuth.Span;

import io.kryptoblocks.msa.data.nfr.trace.key.TraceKey;
import io.kryptoblocks.msa.data.nfr.trace.key.TraceSpankey;
import io.kryptoblocks.msa.data.nfr.trace.model.Trace;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceSpan;
import io.kryptoblocks.msa.data.nfr.trace.udt.TraceSpanLogInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class TraceActivityMessageConverter.
 */
public class TraceActivityMessageConverter {
	
	
	
	/**
	 * Generated key and data.
	 *
	 * @param activity the activity
	 * @return the object[]
	 */
	public Object[] generatedKeyAndData(TraceActivity activity) {		
		Object[] returnValue = new Object[3];		
		return returnValue;
	}
	
	
	/**
	 * Gets the trace span from span.
	 *
	 * @param span the span
	 * @param appName the app name
	 * @param hostName the host name
	 * @param port the port
	 * @return the trace span from span
	 */
	public Object[] getTraceSpanFromSpan(Span span, String appName, String hostName, int port) {
		Object[] returnValue = new Object[2];

		Trace trace = new Trace();
		long traceId = span.getTraceId();
		TraceKey traceKey = new TraceKey();
		traceKey.setId(traceId);
		traceKey.setName(span.getName());
		trace.setKey(traceKey);
		trace.setHost(hostName);
		trace.setServiceName(appName);
		trace.setPort(port);
		List<TraceSpan> traceSpanList = new ArrayList<TraceSpan>();
		List<String> spanIds = new ArrayList<String>();
		TraceSpan traceSpan = makeTraceSpan(span, traceId);
		spanIds.add(traceSpan.getKey().getId());
		traceSpanList.add(traceSpan);

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
	 * @param traceID the trace ID
	 * @return the trace span
	 */
	public TraceSpan makeTraceSpan(Span span, long traceID) {

		TraceSpan traceSpan = new TraceSpan();
		TraceSpankey key = new TraceSpankey();
		traceSpan.setName(span.getName());
		traceSpan.setTraceId(traceID);
		key.setTraceSpanId(span.getSpanId());
		key.setId(UUID.randomUUID().toString());
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
