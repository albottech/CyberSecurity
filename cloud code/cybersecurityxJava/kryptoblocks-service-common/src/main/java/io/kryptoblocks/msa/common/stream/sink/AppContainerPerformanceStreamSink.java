package io.kryptoblocks.msa.common.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface AppContainerPerformanceStreamSink.
 */
public interface AppContainerPerformanceStreamSink {
	
	
	/** The app container performance input. */
	String APP_CONTAINER_PERFORMANCE_INPUT = "appContainerPerformanceInput";

	/**
	 * App container performance input.
	 *
	 * @return the subscribable channel
	 */
	@Input(AppContainerPerformanceStreamSink.APP_CONTAINER_PERFORMANCE_INPUT)
	SubscribableChannel appContainerPerformanceInput();

}
