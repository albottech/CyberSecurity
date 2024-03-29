package io.kryptoblocks.msa.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import io.kryptoblocks.msa.common.model.DestinationType;

// TODO: Auto-generated Javadoc
/**
 * The Class PerformanceMetricLogCondition.
 */
public class PerformanceMetricLogCondition implements Condition {

	/** The service performance metric destination. */
	@Value("${service.performance.metric.destination}")
	private String servicePerformanceMetricDestination;

	/**
	 * Matches.
	 *
	 * @param context the context
	 * @param metadata the metadata
	 * @return true, if successful
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return servicePerformanceMetricDestination.equalsIgnoreCase(DestinationType.LOG.getValue());
	}
}
