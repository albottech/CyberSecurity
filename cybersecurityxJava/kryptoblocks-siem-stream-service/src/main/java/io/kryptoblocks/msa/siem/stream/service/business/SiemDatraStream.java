package io.kryptoblocks.msa.siem.stream.service.business;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

// TODO: Auto-generated Javadoc
/**
 * The Interface SiemDatraStream.
 */
public interface SiemDatraStream {

	/** The siem data landing stream input. */
	String SIEM_DATA_LANDING_STREAM_INPUT = "siem_data_landing_stream_input";
	
	/** The siem data landing stream output. */
	String SIEM_DATA_LANDING_STREAM_OUTPUT = "siem_data_landing_stream_output";
	
	/** The siem data sourcing stream input. */
	String SIEM_DATA_SOURCING_STREAM_INPUT = "siem_data_sourcing_stream_input";
	
	/** The siem data sourcing stream output. */
	String SIEM_DATA_SOURCING_STREAM_OUTPUT = "siem_data_sourcing_stream_output";

	/** The siem data enrichment stream input. */
	String SIEM_DATA_ENRICHMENT_STREAM_INPUT = "siem_data_enrichment_stream_input";
	
	/** The siem data enrichment stream output. */
	String SIEM_DATA_ENRICHMENT_STREAM_OUTPUT = "siem_data_enrichment_stream_output";

	/** The siem data ai stream input. */
	String SIEM_DATA_AI_STREAM_INPUT = "siem_data_ai_stream_input";
	
	/** The siem data ai stream output. */
	String SIEM_DATA_AI_STREAM_OUTPUT = "siem_data_ai_stream_output";

	/** The siem data sor stream input. */
	String SIEM_DATA_SOR_STREAM_INPUT = "siem_data_sor_stream_input";
	
	/** The siem data sor stream output. */
	String SIEM_DATA_SOR_STREAM_OUTPUT = "siem_data_sor_stream_output";

	/** The siem data indexing stream input. */
	String SIEM_DATA_INDEXING_STREAM_INPUT = "siem_data_indexing_stream_input";
	
	/** The siem data indexing stream output. */
	String SIEM_DATA_INDEXING_STREAM_OUTPUT = "siem_data_indexing_stream_output";
	
	/**
	 * Siem landing data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read siem data LANDING records from LANDING topic */
	@Input(SIEM_DATA_LANDING_STREAM_INPUT)
	SubscribableChannel siemLandingDataSubscribableChannel();

	/**
	 * Siem landing message channel.
	 *
	 * @return the message channel
	 */
	/* write siem data LANDING record to LANDING topic */
	@Output(SIEM_DATA_LANDING_STREAM_OUTPUT)
	MessageChannel siemLandingMessageChannel();
	

	/**
	 * Siem sourcing data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read siem data SOURCING records from SOURCING topic */
	@Input(SIEM_DATA_SOURCING_STREAM_INPUT)
	SubscribableChannel siemSourcingDataSubscribableChannel();

	/**
	 * Siem sourcing message channel.
	 *
	 * @return the message channel
	 */
	/* write siem data SOURCING record to SOURCING topic */
	@Output(SIEM_DATA_SOURCING_STREAM_OUTPUT)
	MessageChannel siemSourcingMessageChannel();

	/**
	 * Siem enrichment data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read siem data ENRICHMENT records from ENRICHMENT topic */
	@Input(SIEM_DATA_ENRICHMENT_STREAM_INPUT)
	SubscribableChannel siemEnrichmentDataSubscribableChannel();

	/**
	 * Siem enrichment message channel.
	 *
	 * @return the message channel
	 */
	/* write siem data ENRICHMENT record to ENRICHMENT topic */
	@Output(SIEM_DATA_ENRICHMENT_STREAM_OUTPUT)
	MessageChannel siemEnrichmentMessageChannel();

	/**
	 * Siem ai data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read siem data AI records from AI topic */
	@Input(SIEM_DATA_AI_STREAM_INPUT)
	SubscribableChannel siemAiDataSubscribableChannel();

	/**
	 * Siem ai message channel.
	 *
	 * @return the message channel
	 */
	/* write siem data AI record to AI topic */
	@Output(SIEM_DATA_AI_STREAM_OUTPUT)
	MessageChannel siemAiMessageChannel();

	/**
	 * Siem sor data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read siem data SOR records from SOR topic */
	@Input(SIEM_DATA_SOR_STREAM_INPUT)
	SubscribableChannel siemSorDataSubscribableChannel();

	/**
	 * Siem sor message channel.
	 *
	 * @return the message channel
	 */
	/* write siem data SOR record to SOR topic */
	@Output(SIEM_DATA_SOR_STREAM_OUTPUT)
	MessageChannel siemSorMessageChannel();

	/**
	 * Siem indexing data subscribable channel.
	 *
	 * @return the subscribable channel
	 */
	/* read siem data INDEXING records from INDEXING topic */
	@Input(SIEM_DATA_INDEXING_STREAM_INPUT)
	SubscribableChannel siemIndexingDataSubscribableChannel();

	/**
	 * Siem indexing message channel.
	 *
	 * @return the message channel
	 */
	/* write siem data INDEXING record to SOR topic */
	@Output(SIEM_DATA_INDEXING_STREAM_OUTPUT)
	MessageChannel siemIndexingMessageChannel();

}
