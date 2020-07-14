package io.kryptoblocks.msa.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Logger;
import io.kryptoblocks.msa.common.names.NameCollection;
import io.kryptoblocks.msa.common.util.BusinessActivityLogCondition;
import io.kryptoblocks.msa.common.util.ExceptionLogCondition;
import io.kryptoblocks.msa.common.util.LoggerUtil;
import io.kryptoblocks.msa.common.util.PerformanceMetricLogCondition;
import io.kryptoblocks.msa.common.util.TraceMetricLogCondition;


// TODO: Auto-generated Javadoc
/**
 * The Class BaseLogConfig.
 */
public class BaseLogConfig {

	/** The service name. */
	@Value("${spring.application.name}")
	private String serviceName;

	 
	/**
	 * Business activity logger.
	 *
	 * @return the logger
	 */
	public Logger businessActivityLogger() {
		String name = getName(NameCollection.BUSINESS_ACTIVITY_LOGGER_SUFFIX);
		return LoggerUtil.createLoggerFor(name, name + ".log");
	}
	
	 
	/**
	 * Exception logger.
	 *
	 * @return the logger
	 */
	public Logger exceptionLogger() {
		String name = getName(NameCollection.EXCEPTION_LOGGER_SUFFIX);
		return LoggerUtil.createLoggerFor(name, name + ".log");
	}
	
	 
	/**
	 * Performance metric logger.
	 *
	 * @return the logger
	 */
	public Logger performanceMetricLogger() {
		String name = getName(NameCollection.PERFORMANCE_METRIC_LOGGER_SUFFIX);
		return LoggerUtil.createLoggerFor(name, name + ".log");
	}
	
	 
	/**
	 * Trace metric logger.
	 *
	 * @return the logger
	 */
	public Logger traceMetricLogger() {
		String name = getName(NameCollection.TRACE_METRIC_LOGGER_SUFFIX);
		return LoggerUtil.createLoggerFor(name, name + ".log");
	}
	
	/**
	 * Gets the name.
	 *
	 * @param suffix the suffix
	 * @return the name
	 */
	private String getName(String suffix) {
		return serviceName + "_" + suffix;
	}

}
