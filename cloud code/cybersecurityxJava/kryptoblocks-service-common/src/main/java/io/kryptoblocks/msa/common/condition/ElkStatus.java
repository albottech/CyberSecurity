package io.kryptoblocks.msa.common.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

// TODO: Auto-generated Javadoc
/**
 * The Class ElkStatus.
 */
public class ElkStatus implements Condition{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ElkStatus.class);

	/**
	 * Matches.
	 *
	 * @param context the context
	 * @param metadata the metadata
	 * @return true, if successful
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String serviceElkEnabledProperty = context.getEnvironment().getProperty("elk.enabled");
		LOGGER.debug("service elk enabled property value from environment: {}", serviceElkEnabledProperty);
		if(serviceElkEnabledProperty != null) {
			return new Boolean(serviceElkEnabledProperty).booleanValue();
		}else {
			return false;
		}		
	}
}
