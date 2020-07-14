package io.kryptoblocks.msa.common.stream.source;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


// TODO: Auto-generated Javadoc
/**
 * The Interface InfraContainerPerformanceStreamSource.
 */
public interface InfraContainerPerformanceStreamSource {
	
	/** The infra container performance output. */
	String INFRA_CONTAINER_PERFORMANCE_OUTPUT = "infraContainerPerformanceOutput";
	
	/**
	 * Infra container performance output.
	 *
	 * @return the message channel
	 */
	@Output(INFRA_CONTAINER_PERFORMANCE_OUTPUT)
	MessageChannel infraContainerPerformanceOutput();

}
