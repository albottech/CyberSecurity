package io.kryptoblocks.msa.network.stream.service.business;

import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;

public interface NetworkDataSORListener {
	/**
	 * Listen to network data sor stream topic.
	 *
	 * @param networkDataSORModel the network data SOR model
	 */
	public void listenToNetworkDataSorStreamTopic(NetworkDataSORModel networkDataSORModel);
}
