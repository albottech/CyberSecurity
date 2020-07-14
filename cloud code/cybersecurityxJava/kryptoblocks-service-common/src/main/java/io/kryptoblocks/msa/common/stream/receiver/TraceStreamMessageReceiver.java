package io.kryptoblocks.msa.common.stream.receiver;

import java.util.ArrayList;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.kryptoblocks.msa.common.elastic.SecurityActivityIndexBuilder;
import io.kryptoblocks.msa.common.elastic.TraceActivityIndexBuilder;
import io.kryptoblocks.msa.common.stream.sink.SecurityActivityStreamSink;
import io.kryptoblocks.msa.common.stream.sink.TraceActivityStreamSink;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.respository.service.NfrRepositoryService;
import io.kryptoblocks.msa.data.nfr.security.model.SecurityActivity;
import io.kryptoblocks.msa.data.nfr.trace.model.Trace;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceSpan;
import io.kryptoblocks.msa.data.nfr.util.TraceActivityMessageConverter;;

// TODO: Auto-generated Javadoc
/**
 * The Class TraceStreamMessageReceiver.
 */
public class TraceStreamMessageReceiver {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TraceStreamMessageReceiver.class);

	/** The trace activity index builder. */
	@Autowired(required = false)
	TraceActivityIndexBuilder traceActivityIndexBuilder;

	
	/** The db storage. */
	@Value("${service.trace.activity.db.storage}")
	boolean dbStorage;
	
	/** The elk enabled. */
	@Value("${elk.enabled}")
	boolean elkEnabled;

	/** The elk storage. */
	@Value("${service.trace.activity.elk.storage}")
	boolean elkStorage;

	/** The json util. */
	@Autowired
	JsonUtil jsonUtil;

	/** The repository service. */
	@Autowired
	NfrRepositoryService repositoryService;
	
	/** The trace activity message converter. */
	@Autowired
	TraceActivityMessageConverter traceActivityMessageConverter;

	/**
	 * Process message.
	 *
	 * @param message the message
	 */
	@ServiceActivator(inputChannel = TraceActivityStreamSink.TRACE_ACTIVITY_INPUT)
	public void processMessage(Message<?> message) {
		String payloadAsString = (String) message.getPayload();
		LOGGER.debug("trace sink message receiver msg: {}", payloadAsString);
		if(dbStorage) {
			dbStorage(payloadAsString);
		}
		if(elkEnabled && elkStorage) {
			elkStorage(payloadAsString);
		}
	}
	
	/**
	 * Elk storage.
	 *
	 * @param message the message
	 */
	private void elkStorage(String message) {
		try {
			ObjectMapper objMapper = jsonUtil.getObjectMapper();
			TraceActivity traceActivity = objMapper.readValue(message, TraceActivity.class);
			String indexType = traceActivity.getCollectionType();			
			IndexRequestBuilder iBuilder = traceActivityIndexBuilder.getTraceActivityIndexBuilder(indexType);
			iBuilder.setSource(message);
			IndexResponse response = iBuilder.get();
			LOGGER.debug("elk storage activity reponse for trace activity: {}", response);
		}catch(Exception e) {
			LOGGER.error("exception in elk storage activity for trace activity: {}", ExceptionUtils.getFullStackTrace(e));			
		}
	}
	
	/**
	 * Db storage.
	 *
	 * @param message the message
	 */
	private void dbStorage(String message) {
		try {
			//TODO	
			ObjectMapper objMapper = jsonUtil.getObjectMapper();
			TraceActivity traceActivity = objMapper.readValue(message, TraceActivity.class);
			Object[] traceAndSpan = traceActivityMessageConverter.getTraceSpanFromSpan(traceActivity.getSpan(), traceActivity.getAppName(), traceActivity.getCloudClientHostName(), traceActivity.getAppHostPort());
			Trace trace = (Trace) traceAndSpan[0];
			ArrayList<Object> persistables = new ArrayList<Object>();
			persistables.add(trace);			
			@SuppressWarnings("unchecked")
			ArrayList<TraceSpan> traceSpans = (ArrayList<TraceSpan>) traceAndSpan[1];
			//repositoryService.saveRepositoryObject(trace);
			for (TraceSpan traceSpan : traceSpans) {
				persistables.add(traceSpan);
				//repositoryService.saveRepositoryObject(traceSpan);
			}		
			repositoryService.saveCollection(persistables);
			}catch(Exception e) {
				LOGGER.error("exception in trace activity post to cassandra repository method: {}", ExceptionUtils.getFullStackTrace(e));
			}		
	}
}
