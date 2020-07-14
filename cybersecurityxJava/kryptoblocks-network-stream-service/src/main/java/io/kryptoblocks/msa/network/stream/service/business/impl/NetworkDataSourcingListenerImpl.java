package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSorcingListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSourcingListenerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataSourcingListenerImpl implements NetworkDataSorcingListener{
	
	@Autowired
	NetworkDataSourcingListenerService networkDataSourcingListenerService;
	
	/**
	 * Listen to network data sourcing stream topic.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @throws BusinessException the business exception
	 */
	@Override
	@StreamListener(NetworkDatraStream.NETWORK_DATA_SOURCING_STREAM_INPUT)
	public void listenToNetworkDataSourcingStreamTopic(NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException{
	
		
		try {
			//NetworkDataSourcingModel model = deriveNetworkDataSourcingModel(input);
			if(networkDataSourcingModel != null) {
				//From SOURCE TO ENRICHMENT
				networkDataSourcingListenerService.enrichNetworkData(networkDataSourcingModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
		
	}
}
