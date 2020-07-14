package io.kryptoblocks.msa.data.nfr.bean.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.bean.model.BeanPerformActivity;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import reactor.bus.Event;
import reactor.bus.EventBus;


// TODO: Auto-generated Javadoc
/**
 * The Class BeanPerfActivityEventSender.
 */
public class BeanPerfActivityEventSender {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanPerfActivityEventSender.class);
	
	

	/** The service S bean perf activity stream. */
	@Value("${service.bean.perf.activity.stream}")
	String serviceSBeanPerfActivityStream;
	
	/** The event bus. */
	@Autowired
	private EventBus eventBus;
	
	
	
	/**
	 * Send bean perf activity.
	 *
	 * @param beanPerformActivity the bean perform activity
	 */
	public void sendBeanPerfActivity(BeanPerformActivity beanPerformActivity) {
		try {
			beanPerformActivity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			beanPerformActivity.setStreamType(serviceSBeanPerfActivityStream);
		eventBus.send(beanPerformActivity, Event.wrap(beanPerformActivity, beanPerformActivity));
		}catch(Exception e) {
			LOGGER.error("exception in send bean perform activity method: {}", ExceptionUtils.getFullStackTrace(e));
			
		}
	}
}
