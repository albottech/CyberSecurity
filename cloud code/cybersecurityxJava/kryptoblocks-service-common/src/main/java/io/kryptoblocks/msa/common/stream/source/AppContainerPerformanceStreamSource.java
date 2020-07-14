package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface AppContainerPerformanceStreamSource.
 */
public interface AppContainerPerformanceStreamSource {
	
	/** The app container performance output. */
	String APP_CONTAINER_PERFORMANCE_OUTPUT = "appContainerPerformanceOutput";
	
	/**
	 * App container performance output.
	 *
	 * @return the message channel
	 */
	@Output(APP_CONTAINER_PERFORMANCE_OUTPUT)
	MessageChannel appContainerPerformanceOutput();

}
