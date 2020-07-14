package io.kryptoblocks.msa.data.nfr.business.activity.event;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import reactor.bus.Event;
import reactor.bus.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityEventSender.
 */
public class BusinessActivityEventSender {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessActivityEventSender.class);

	/** The service business activity stream. */
	@Value("${service.business.activity.stream}")
	String serviceBusinessActivityStream;

	/** The event bus. */
	@Autowired
	private EventBus eventBus;

	/**
	 * Send service business activity.
	 *
	 * @param businessActivity the business activity
	 */
	public void sendServiceBusinessActivity(BusinessActivity businessActivity) {

		try {
			LOGGER.debug("Notifying business activity");
			businessActivity.setReportedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			businessActivity.setStreamType(serviceBusinessActivityStream);
			eventBus.send(businessActivity, Event.wrap(businessActivity, businessActivity));
		} catch (Exception e) {
			LOGGER.error("exception in sendServiceBusinessActivity method: {} ", ExceptionUtils.getFullStackTrace(e));
		}

	}
}
