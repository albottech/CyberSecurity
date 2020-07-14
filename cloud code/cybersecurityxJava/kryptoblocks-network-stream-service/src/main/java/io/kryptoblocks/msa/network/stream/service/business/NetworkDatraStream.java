package io.kryptoblocks.msa.network.stream.service.business;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface NetworkDatraStream.
 */
public interface NetworkDatraStream {

	/** The network data landing stream input. */
	String NETWORK_DATA_LANDING_STREAM_INPUT = "network_data_landing_stream_input";
	
	/** The network data landing stream output. */
	String NETWORK_DATA_LANDING_STREAM_OUTPUT = "network_data_landing_stream_output";
	
	/** The network data sourcing stream input. */
	String NETWORK_DATA_SOURCING_STREAM_INPUT = "network_data_sourcing_stream_input";
	
	/** The network data sourcing stream output. */
	String NETWORK_DATA_SOURCING_STREAM_OUTPUT = "network_data_sourcing_stream_output";

	/** The network data enrichment stream input. */
	String NETWORK_DATA_ENRICHMENT_STREAM_INPUT = "network_data_enrichment_stream_input";
	
	/** The network data enrichment stream output. */
	String NETWORK_DATA_ENRICHMENT_STREAM_OUTPUT = "network_data_enrichment_stream_output";

	/** The network data ai stream input. */
	String NETWORK_DATA_AI_STREAM_INPUT = "network_data_ai_stream_input";
	
	/** The network data ai stream output. */
	String NETWORK_DATA_AI_STREAM_OUTPUT = "network_data_ai_stream_output";

	/** The network data sor stream input. */
	String NETWORK_DATA_SOR_STREAM_INPUT = "network_data_sor_stream_input";
	
	/** The network data sor stream output. */
	String NETWORK_DATA_SOR_STREAM_OUTPUT = "network_data_sor_stream_output";

	/** The network data indexing stream input. */
	String NETWORK_DATA_INDEXING_STREAM_INPUT = "network_data_indexing_stream_input";
	
	/** The network data indexing stream output. */
	String NETWORK_DATA_INDEXING_STREAM_OUTPUT = "network_data_indexing_stream_output";
	
	/**
	 * Network landing data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read network data LANDING records from LANDING topic */
	@Input(NETWORK_DATA_LANDING_STREAM_INPUT)
	SubscribableChannel networkLandingDataSubscribableChannel();

	/**
	 * Network landing message channel.
	 *
	 * @return the message channel
	 */
	/* write network data LANDING record to LANDING topic */
	@Output(NETWORK_DATA_LANDING_STREAM_OUTPUT)
	MessageChannel networkLandingMessageChannel();
	

	/**
	 * Network sourcing data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read network data SOURCING records from SOURCING topic */
	@Input(NETWORK_DATA_SOURCING_STREAM_INPUT)
	SubscribableChannel networkSourcingDataSubscribableChannel();

	/**
	 * Network sourcing message channel.
	 *
	 * @return the message channel
	 */
	/* write network data SOURCING record to SOURCING topic */
	@Output(NETWORK_DATA_SOURCING_STREAM_OUTPUT)
	MessageChannel networkSourcingMessageChannel();

	/**
	 * Network enrichment data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read network data ENRICHMENT records from ENRICHMENT topic */
	@Input(NETWORK_DATA_ENRICHMENT_STREAM_INPUT)
	SubscribableChannel networkEnrichmentDataSubscribableChannel();

	/**
	 * Network enrichment message channel.
	 *
	 * @return the message channel
	 */
	/* write network data ENRICHMENT record to ENRICHMENT topic */
	@Output(NETWORK_DATA_ENRICHMENT_STREAM_OUTPUT)
	MessageChannel networkEnrichmentMessageChannel();

	/**
	 * Network ai data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read network data AI records from AI topic */
	@Input(NETWORK_DATA_AI_STREAM_INPUT)
	SubscribableChannel networkAiDataSubscribableChannel();

	/**
	 * Network ai message channel.
	 *
	 * @return the message channel
	 */
	/* write network data AI record to AI topic */
	@Output(NETWORK_DATA_AI_STREAM_OUTPUT)
	MessageChannel networkAiMessageChannel();

	/**
	 * Network sor data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read network data SOR records from SOR topic */
	@Input(NETWORK_DATA_SOR_STREAM_INPUT)
	SubscribableChannel networkSorDataSubscribableChannel();

	/**
	 * Network sor message channel.
	 *
	 * @return the message channel
	 */
	/* write network data SOR record to SOR topic */
	@Output(NETWORK_DATA_SOR_STREAM_OUTPUT)
	MessageChannel networkSorMessageChannel();

	/**
	 * Network indexing data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read network data INDEXING records from INDEXING topic */
	@Input(NETWORK_DATA_INDEXING_STREAM_INPUT)
	SubscribableChannel networkIndexingDataSubscribableChannel();

	/**
	 * Network indexing message channel.
	 *
	 * @return the message channel
	 */
	/* write network data INDEXING record to SOR topic */
	@Output(NETWORK_DATA_INDEXING_STREAM_OUTPUT)
	MessageChannel networkIndexingMessageChannel();

}
