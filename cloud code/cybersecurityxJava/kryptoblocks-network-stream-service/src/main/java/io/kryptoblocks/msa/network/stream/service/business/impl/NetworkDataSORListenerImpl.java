package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;

import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListenerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;

public class NetworkDataSORListenerImpl implements NetworkDataSORListener{
	
	@Autowired
	NetworkDataSORListenerService networkDataSORListenerService;
	
	/**
	 * Listen to network data sor stream topic.
	 *
	 * @param networkDataSORModel the network data SOR model
	 */
	@Override
	@StreamListener(NetworkDatraStream.NETWORK_DATA_SOR_STREAM_INPUT)
	public void listenToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel) {
		
		try {
			 
			if(networkDataSORModel != null) {
				//From SOR to AI
				networkDataSORListenerService.aiNetworkData(networkDataSORModel);
				networkDataSORListenerService.indexNetworkData(networkDataSORModel);
			}
			
		}catch(Exception e) {
			//TODO
		}
	}
}
