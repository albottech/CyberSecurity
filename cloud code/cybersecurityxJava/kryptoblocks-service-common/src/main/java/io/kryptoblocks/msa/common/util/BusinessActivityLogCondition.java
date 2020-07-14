package io.kryptoblocks.msa.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import io.kryptoblocks.msa.common.model.DestinationType;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityLogCondition.
 */
public class BusinessActivityLogCondition implements Condition {

	/** The service business activity destination. */
	@Value("${service.business.activity.destination}")
	private String serviceBusinessActivityDestination;

	/**
	 * Matches.
	 *
	 * @param context the context
	 * @param metadata the metadata
	 * @return true, if successful
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return serviceBusinessActivityDestination.equalsIgnoreCase(DestinationType.LOG.getValue());
	}
}
