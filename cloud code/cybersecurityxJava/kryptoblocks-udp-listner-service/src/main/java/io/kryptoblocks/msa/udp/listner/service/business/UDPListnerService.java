package io.kryptoblocks.msa.udp.listner.service.business;

 
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.model.*;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerStatus;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerInput;

 public interface UDPListnerService {
	
	public NetworkDataSourcingModel sourceNetworkData(Object cce) throws BusinessException;
	
	public NetworkDataEnrichmentModel enrichNetworkData(NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException;
	
	public NetworkDataSORModel sorNetworkData(NetworkDataEnrichmentModel networkDataEnrichmentModel) throws BusinessException;
	
	public NetworkDataIndexModel indexNetworkData(NetworkDataSORModel networkDataSORModel) throws BusinessException;
	
	public NetworkDataAIModel aiNetworkData(NetworkDataSORModel networkDataSORModel) throws BusinessException;
	
	public UDPListnerStatus getNetworkDataProcessStatus(UDPListnerInput networkProcessInput) throws BusinessException;
	
}
