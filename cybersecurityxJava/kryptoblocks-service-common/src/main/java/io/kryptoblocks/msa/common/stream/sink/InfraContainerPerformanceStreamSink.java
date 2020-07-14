package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface InfraContainerPerformanceStreamSink.
 */
public interface InfraContainerPerformanceStreamSink {
	
	
	/** The infra container performance input. */
	String INFRA_CONTAINER_PERFORMANCE_INPUT = "infraContainerPerformanceInput";

	/**
	 * Infra container performance input.
	 *
	 * @return the subscribable channel
	 */
	@Input(InfraContainerPerformanceStreamSink.INFRA_CONTAINER_PERFORMANCE_INPUT)
	SubscribableChannel infraContainerPerformanceInput();

}
