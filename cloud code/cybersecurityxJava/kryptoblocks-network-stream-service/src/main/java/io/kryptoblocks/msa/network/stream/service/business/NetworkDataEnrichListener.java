package io.kryptoblocks.msa.network.stream.service.business;

import org.springframework.messaging.handler.annotation.Payload;

import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;

public interface NetworkDataEnrichListener {
	/**
	 * Listen to network data enrichment stream topic.
	 *
	 * @param input the input
	 */
	public void listenToNetworkDataEnrichmentStreamTopic(@Payload NetworkDataEnrichmentModel input);
}
