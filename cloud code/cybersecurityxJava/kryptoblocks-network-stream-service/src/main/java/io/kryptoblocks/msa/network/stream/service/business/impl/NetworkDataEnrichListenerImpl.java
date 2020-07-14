package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;

import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichListenerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataEnrichListenerImpl implements NetworkDataEnrichListener{
	
	@Autowired
	NetworkDataEnrichListenerService networkDataEnrichListenerService;
	
	/**
	 * Listen to network data enrichment stream topic.
	 *
	 * @param networkDataEnrichmentModel the network data enrichment model
	 */
	@Override
	@StreamListener(NetworkDatraStream.NETWORK_DATA_ENRICHMENT_STREAM_INPUT)
	public void listenToNetworkDataEnrichmentStreamTopic(NetworkDataEnrichmentModel networkDataEnrichmentModel) {
		try {
			//NetworkDataSourcingModel model = deriveNetworkDataSourcingModel(input);
			if(networkDataEnrichmentModel != null) {
				//From ENRICHMENT to SOR
				networkDataEnrichListenerService.sorNetworkData(networkDataEnrichmentModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
	}

}
