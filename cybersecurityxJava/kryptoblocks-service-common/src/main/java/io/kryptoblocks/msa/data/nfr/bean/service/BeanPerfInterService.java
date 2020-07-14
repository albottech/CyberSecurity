package io.kryptoblocks.msa.data.nfr.bean.service;

import com.jamonapi.MonKey;
import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.jamonapi.utils.Misc;

import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.data.nfr.bean.event.BeanPerfActivityEventSender;
import io.kryptoblocks.msa.data.nfr.bean.model.BeanPerformActivity;
import lombok.Getter;
import lombok.Setter;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanPerfInterService.
 */
public class BeanPerfInterService extends AbstractMonitoringInterceptor {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanPerfInterService.class);
	
	/**
	 * Sets the stream enabled.
	 *
	 * @param streamEnabled the new stream enabled
	 */
	@Setter /**
  * Checks if is stream enabled.
  *
  * @return true, if is stream enabled
  */
 @Getter
	private boolean streamEnabled;
	
	/** The bean perf activity event sender. */
	@Autowired
	BeanPerfActivityEventSender beanPerfActivityEventSender;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The track all invocations. */
	private boolean trackAllInvocations = false;


	/**
	 * Create a new BeanPerformanceMonitorInterceptor with a static logger.
	 */
	public BeanPerfInterService() {
	}

	/**
	 * Create a new BeanPerformanceMonitorInterceptor with a dynamic or static logger,
	 * according to the given flag.
	 * @param useDynamicLogger whether to use a dynamic logger or a static logger
	 * @see #setUseDynamicLogger
	 */
	public BeanPerfInterService(boolean useDynamicLogger) {
		setUseDynamicLogger(useDynamicLogger);
	}

	/**
	 * Create a new BeanPerformanceMonitorInterceptor with a dynamic or static logger,
	 * according to the given flag.
	 * @param useDynamicLogger whether to use a dynamic logger or a static logger
	 * @param trackAllInvocations whether to track all invocations that go through
	 * this interceptor, or just invocations with trace logging enabled
	 * @see #setUseDynamicLogger
	 */
	public BeanPerfInterService(boolean useDynamicLogger, boolean trackAllInvocations) {
		setUseDynamicLogger(useDynamicLogger);
		setTrackAllInvocations(trackAllInvocations);
	}


	/**
	 * Set whether to track all invocations that go through this interceptor,
	 * or just invocations with trace logging enabled.
	 * <p>Default is "false": Only invocations with trace logging enabled will
	 * be monitored. Specify "true" to let JAMon track all invocations,
	 * gathering statistics even when trace logging is disabled.
	 *
	 * @param trackAllInvocations the new track all invocations
	 */
	public void setTrackAllInvocations(boolean trackAllInvocations) {
		this.trackAllInvocations = trackAllInvocations;
	}


	/**
	 * Always applies the interceptor if the "trackAllInvocations" flag has been set;
	 * else just kicks in if the log is enabled.
	 *
	 * @param invocation the invocation
	 * @param logger the logger
	 * @return true, if is interceptor enabled
	 * @see #setTrackAllInvocations
	 * @see #isLogEnabled
	 */
	@Override
	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return (this.trackAllInvocations || isLogEnabled(logger));
	}

	/**
	 * Wraps the invocation with a JAMon Monitor and writes the current
	 * performance statistics to the log (if enabled).
	 *
	 * @param invocation the invocation
	 * @param logger the logger
	 * @return the object
	 * @throws Throwable the throwable
	 * @see com.jamonapi.MonitorFactory#start
	 * @see com.jamonapi.Monitor#stop
	 */
	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
		String name = createInvocationTraceName(invocation);
		MonKey key = new MonKeyImp(name, name, "ms.");

		Monitor monitor = MonitorFactory.start(key);
		try {
			return invocation.proceed();
		}
		catch (Throwable ex) {
			trackException(key, ex);
			throw ex;
		}
		finally {
			monitor.stop();
			beanPerfActivityEventSender.sendBeanPerfActivity(getBeanPerfActivity(monitor));
		}
	}

	/**
	 * Count the thrown exception and put the stack trace in the details portion of the key.
	 * This will allow the stack trace to be viewed in the JAMon web application.
	 *
	 * @param key the key
	 * @param ex the ex
	 */
	protected void trackException(MonKey key, Throwable ex) {
		String stackTrace = "stackTrace=" + Misc.getExceptionTrace(ex);
		key.setDetails(stackTrace);

		// Specific exception counter. Example: java.lang.RuntimeException
		MonitorFactory.add(new MonKeyImp(ex.getClass().getName(), stackTrace, "Exception"), 1);

		// General exception counter which is a total for all exceptions thrown
		MonitorFactory.add(new MonKeyImp(MonitorFactory.EXCEPTIONS_LABEL, stackTrace, "Exception"), 1);
	}
	
	/**
	 * Gets the bean perf activity.
	 *
	 * @param monitor the monitor
	 * @return the bean perf activity
	 */
	private BeanPerformActivity getBeanPerfActivity(Monitor monitor) {
		BeanPerformActivity returnValue = new BeanPerformActivity();
		returnValue.setAverage(monitor.getAvg());
		returnValue.setCollectedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		returnValue.setFirstAccess(monitor.getFirstAccess());
		returnValue.setHits(monitor.getHits());
		returnValue.setLastAccess(monitor.getLastAccess());
		returnValue.setLastValue(monitor.getLastValue());
		returnValue.setMax(monitor.getMax());
		returnValue.setMaxActive(monitor.getMaxActive());
		returnValue.setMin(monitor.getMin());
		returnValue.setTotal(monitor.getTotal());
		return returnValue;
	}

}

