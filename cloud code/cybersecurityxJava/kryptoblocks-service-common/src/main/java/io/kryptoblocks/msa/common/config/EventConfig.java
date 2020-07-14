package io.kryptoblocks.msa.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.data.nfr.app.statistics.event.AppContPerfAcivityEvtReceiver;
import io.kryptoblocks.msa.data.nfr.app.statistics.event.AppContPerfAcivityEvtSender;
import io.kryptoblocks.msa.data.nfr.app.statistics.model.AppContPerfActivity;
import io.kryptoblocks.msa.data.nfr.app.statistics.service.AppContainerClientService;
import io.kryptoblocks.msa.data.nfr.business.activity.event.BusinessActivityEventReceiver;
import io.kryptoblocks.msa.data.nfr.business.activity.event.BusinessActivityEventSender;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.business.activity.service.BusinessActivityEventService;
import io.kryptoblocks.msa.data.nfr.exception.event.ExceptionActivityEventReceiver;
import io.kryptoblocks.msa.data.nfr.exception.event.ExceptionActivityEventSender;
import io.kryptoblocks.msa.data.nfr.exception.model.ExceptionActivity;
import io.kryptoblocks.msa.data.nfr.exception.service.ExceptionActivityEventService;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.event.InfraContPerfAcivityEvntReceiver;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.event.InfraContPerfAcivityEvntSender;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.model.InfraContPerfActivity;
import io.kryptoblocks.msa.data.nfr.infra.container.statistics.service.InfraContainerClientService;
import io.kryptoblocks.msa.data.nfr.notification.event.NotificationActivityEventReceiver;
import io.kryptoblocks.msa.data.nfr.notification.event.NotificationActivityEventSender;
import io.kryptoblocks.msa.data.nfr.notification.model.NotificationActivity;
import io.kryptoblocks.msa.data.nfr.notification.service.NotificationActivityEventService;
import io.kryptoblocks.msa.data.nfr.security.event.SecurityActivityEventReceiver;
import io.kryptoblocks.msa.data.nfr.security.event.SecurityActivityEventSender;
import io.kryptoblocks.msa.data.nfr.security.model.SecurityActivity;
import io.kryptoblocks.msa.data.nfr.security.service.SecurityActivityEventService;
import io.kryptoblocks.msa.data.nfr.trace.event.TraceActivityEventReceiver;
import io.kryptoblocks.msa.data.nfr.trace.event.TraceActivityEventSender;
import io.kryptoblocks.msa.data.nfr.trace.model.TraceActivity;
import io.kryptoblocks.msa.data.nfr.trace.service.TraceActivityEventService;
import reactor.spring.context.config.EnableReactor;

// TODO: Auto-generated Javadoc
/**
 * The Class EventConfig.
 */
@Configuration
@EnableReactor
public class EventConfig {

	/**
	 * App cont perf entity.
	 *
	 * @return the app cont perf activity
	 */
	@Bean
	AppContPerfActivity appContPerfEntity() {
		return new AppContPerfActivity();
	}

	/**
	 * Business activity.
	 *
	 * @return the business activity
	 */
	@Bean
	BusinessActivity businessActivity() {
		return new BusinessActivity();
	}

	/**
	 * Exception activity.
	 *
	 * @return the exception activity
	 */
	@Bean
	ExceptionActivity exceptionActivity() {
		return new ExceptionActivity();
	}

	/**
	 * Infra cont perf activity.
	 *
	 * @return the infra cont perf activity
	 */
	@Bean
	InfraContPerfActivity infraContPerfActivity() {
		return new InfraContPerfActivity();
	}

	/**
	 * Notification activity.
	 *
	 * @return the notification activity
	 */
	@Bean
	NotificationActivity notificationActivity() {
		return new NotificationActivity();
	}

	/**
	 * Security activity.
	 *
	 * @return the security activity
	 */
	@Bean
	SecurityActivity SecurityActivity() {
		return new SecurityActivity();
	}

	/**
	 * Trace activity.
	 *
	 * @return the trace activity
	 */
	@Bean
	TraceActivity TraceActivity() {
		return new TraceActivity();
	}

	/**
	 * App cont perf acivity evt sender.
	 *
	 * @return the app cont perf acivity evt sender
	 */
	@Bean
	AppContPerfAcivityEvtSender appContPerfAcivityEvtSender() {
		return new AppContPerfAcivityEvtSender();
	}

	/**
	 * Business activity event sender.
	 *
	 * @return the business activity event sender
	 */
	@Bean
	BusinessActivityEventSender businessActivityEventSender() {
		return new BusinessActivityEventSender();
	}

	/**
	 * Exception activity event sender.
	 *
	 * @return the exception activity event sender
	 */
	@Bean
	ExceptionActivityEventSender exceptionActivityEventSender() {
		return new ExceptionActivityEventSender();
	}

	/**
	 * Infra cont perf acivity evnt sender.
	 *
	 * @return the infra cont perf acivity evnt sender
	 */
	@Bean
	InfraContPerfAcivityEvntSender infraContPerfAcivityEvntSender() {
		return new InfraContPerfAcivityEvntSender();
	}

	/**
	 * Notification activity event sender.
	 *
	 * @return the notification activity event sender
	 */
	@Bean
	NotificationActivityEventSender notificationActivityEventSender() {
		return new NotificationActivityEventSender();
	}

	/**
	 * Securityn activity event sender.
	 *
	 * @return the security activity event sender
	 */
	@Bean
	SecurityActivityEventSender securitynActivityEventSender() {
		return new SecurityActivityEventSender();
	}

	/**
	 * Trace metric event provider.
	 *
	 * @return the trace activity event sender
	 */
	@Bean
	TraceActivityEventSender traceMetricEventProvider() {
		return new TraceActivityEventSender();
	}

	/**
	 * App cont perf acivity evt receiver.
	 *
	 * @return the app cont perf acivity evt receiver
	 */
	@Bean
	AppContPerfAcivityEvtReceiver appContPerfAcivityEvtReceiver() {
		return new AppContPerfAcivityEvtReceiver();
	}

	/**
	 * Business activity event receiver.
	 *
	 * @return the business activity event receiver
	 */
	@Bean
	BusinessActivityEventReceiver businessActivityEventReceiver() {
		return new BusinessActivityEventReceiver();
	}

	/**
	 * Exception event receiver.
	 *
	 * @return the exception activity event receiver
	 */
	@Bean
	ExceptionActivityEventReceiver exceptionEventReceiver() {
		return new ExceptionActivityEventReceiver();
	}

	/**
	 * Infra cont perf acivity evnt receiver.
	 *
	 * @return the infra cont perf acivity evnt receiver
	 */
	@Bean
	InfraContPerfAcivityEvntReceiver infraContPerfAcivityEvntReceiver() {
		return new InfraContPerfAcivityEvntReceiver();
	}

	/**
	 * Notification activity event receiver.
	 *
	 * @return the notification activity event receiver
	 */
	@Bean
	NotificationActivityEventReceiver notificationActivityEventReceiver() {
		return new NotificationActivityEventReceiver();
	}

	/**
	 * Security activity event receiver.
	 *
	 * @return the security activity event receiver
	 */
	@Bean
	SecurityActivityEventReceiver securityActivityEventReceiver() {
		return new SecurityActivityEventReceiver();
	}

	/**
	 * Trace activity event receiver.
	 *
	 * @return the trace activity event receiver
	 */
	@Bean
	TraceActivityEventReceiver traceActivityEventReceiver() {
		return new TraceActivityEventReceiver();
	}
	
	/**
	 * App container client service.
	 *
	 * @return the app container client service
	 */
	@Bean
	AppContainerClientService appContainerClientService() {
		return new AppContainerClientService();		
	}
	
	/**
	 * Business activity event service.
	 *
	 * @return the business activity event service
	 */
	@Bean
	BusinessActivityEventService businessActivityEventService() {
		return new BusinessActivityEventService();
	}
	
	/**
	 * Exception activity event service.
	 *
	 * @return the exception activity event service
	 */
	@Bean
	ExceptionActivityEventService exceptionActivityEventService() {
		return new ExceptionActivityEventService();
	}
	
	/**
	 * Infra container client service.
	 *
	 * @return the infra container client service
	 */
	@Bean
	InfraContainerClientService InfraContainerClientService() {
		return new InfraContainerClientService();
	}
	
	/**
	 * Notification activity event service.
	 *
	 * @return the notification activity event service
	 */
	@Bean
	NotificationActivityEventService notificationActivityEventService() {
		return new NotificationActivityEventService();
	}
	
	/**
	 * Security activity event service.
	 *
	 * @return the security activity event service
	 */
	@Bean
	SecurityActivityEventService securityActivityEventService() {
		return new SecurityActivityEventService();
	}
	
	/**
	 * Trace activity event service.
	 *
	 * @return the trace activity event service
	 */
	@Bean
	TraceActivityEventService traceActivityEventService() {
		return new TraceActivityEventService();
	}

}