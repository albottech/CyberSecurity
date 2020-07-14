package io.kryptoblocks.msa.udp.listner.service.business;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UDPListnerStream {

	String NETWORK_DATA_LANDING_STREAM_INPUT = "network_data_landing_stream_input";
	String NETWORK_DATA_LANDING_STREAM_OUTPUT = "network_data_landing_stream_output";
	
	String NETWORK_DATA_SOURCING_STREAM_INPUT = "network_data_sourcing_stream_input";
	String NETWORK_DATA_SOURCING_STREAM_OUTPUT = "network_data_sourcing_stream_output";

	String NETWORK_DATA_ENRICHMENT_STREAM_INPUT = "network_data_enrichment_stream_input";
	String NETWORK_DATA_ENRICHMENT_STREAM_OUTPUT = "network_data_enrichment_stream_output";

	String NETWORK_DATA_AI_STREAM_INPUT = "network_data_ai_stream_input";
	String NETWORK_DATA_AI_STREAM_OUTPUT = "network_data_ai_stream_output";

	String NETWORK_DATA_SOR_STREAM_INPUT = "network_data_sor_stream_input";
	String NETWORK_DATA_SOR_STREAM_OUTPUT = "network_data_sor_stream_output";

	String NETWORK_DATA_INDEXING_STREAM_INPUT = "network_data_indexing_stream_input";
	String NETWORK_DATA_INDEXING_STREAM_OUTPUT = "network_data_indexing_stream_output";
	
	/* read network data LANDING records from LANDING topic */
	@Input(NETWORK_DATA_LANDING_STREAM_INPUT)
	SubscribableChannel networkLandingDataSubscribableChannel();

	/* write network data LANDING record to LANDING topic */
	@Output(NETWORK_DATA_LANDING_STREAM_OUTPUT)
	MessageChannel networkLandingMessageChannel();
	

	/* read network data SOURCING records from SOURCING topic */
	@Input(NETWORK_DATA_SOURCING_STREAM_INPUT)
	SubscribableChannel networkSourcingDataSubscribableChannel();

	/* write network data SOURCING record to SOURCING topic */
	@Output(NETWORK_DATA_SOURCING_STREAM_OUTPUT)
	MessageChannel networkSourcingMessageChannel();

	/* read network data ENRICHMENT records from ENRICHMENT topic */
	@Input(NETWORK_DATA_ENRICHMENT_STREAM_INPUT)
	SubscribableChannel networkEnrichmentDataSubscribableChannel();

	/* write network data ENRICHMENT record to ENRICHMENT topic */
	@Output(NETWORK_DATA_ENRICHMENT_STREAM_OUTPUT)
	MessageChannel networkEnrichmentMessageChannel();

	/* read network data AI records from AI topic */
	@Input(NETWORK_DATA_AI_STREAM_INPUT)
	SubscribableChannel networkAiDataSubscribableChannel();

	/* write network data AI record to AI topic */
	@Output(NETWORK_DATA_AI_STREAM_OUTPUT)
	MessageChannel networkAiMessageChannel();

	/* read network data SOR records from SOR topic */
	@Input(NETWORK_DATA_SOR_STREAM_INPUT)
	SubscribableChannel networkSorDataSubscribableChannel();

	/* write network data SOR record to SOR topic */
	@Output(NETWORK_DATA_SOR_STREAM_OUTPUT)
	MessageChannel networkSorMessageChannel();

	/* read network data INDEXING records from INDEXING topic */
	@Input(NETWORK_DATA_INDEXING_STREAM_INPUT)
	SubscribableChannel networkIndexingDataSubscribableChannel();

	/* write network data INDEXING record to SOR topic */
	@Output(NETWORK_DATA_INDEXING_STREAM_OUTPUT)
	MessageChannel networkIndexingMessageChannel();

}
