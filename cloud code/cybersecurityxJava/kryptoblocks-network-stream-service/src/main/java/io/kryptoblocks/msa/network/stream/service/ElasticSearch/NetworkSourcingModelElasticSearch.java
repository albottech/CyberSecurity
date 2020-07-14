package io.kryptoblocks.msa.network.stream.service.ElasticSearch;

import org.springframework.beans.factory.annotation.Autowired;
import io.kryptoblocks.msa.network.stream.service.udp.SIEMRecievedJSONModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NetworkSourcingModelElasticSearch {
	
	/** The client. */	
	String client;
	
	/** The data center. */	
	String dataCenter;
	
	/** The rounting identifier. */	
	String rountingIdentifier;
	
	/** The collector type. */	
	String collectorType;
	
	/** The time stamp. */	
	String timeStamp;
	
	@Autowired
	SIEMRecievedJSONModel sIEMRecievedJSONModel;
	
	public NetworkSourcingModelElasticSearch() {
		
	}
	
	@Override
	public String toString() {
		return "NetworkSourcingModelElasticSearch [client=" + client + ", dataCenter=" + dataCenter
				+ ", rountingIdentifier=" + rountingIdentifier + ", collectorType=" + collectorType + ", timeStamp="
				+ timeStamp + ", sIEMRecievedJSONModel=" + sIEMRecievedJSONModel + "]";
	}

	public NetworkSourcingModelElasticSearch(String client, String dataCenter, String rountingIdentifier,
			String collectorType, String timeStamp, SIEMRecievedJSONModel sIEMRecievedJSONModel) {
		super();
		this.client = client;
		this.dataCenter = dataCenter;
		this.rountingIdentifier = rountingIdentifier;
		this.collectorType = collectorType;
		this.timeStamp = timeStamp;
		this.sIEMRecievedJSONModel = sIEMRecievedJSONModel;
	}
	
		
}
