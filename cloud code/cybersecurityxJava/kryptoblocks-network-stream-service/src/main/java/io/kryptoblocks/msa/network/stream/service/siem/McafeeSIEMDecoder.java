package io.kryptoblocks.msa.network.stream.service.siem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;
import io.kryptoblocks.msa.network.stream.service.udp.SIEMRecievedJSONModel;



public class McafeeSIEMDecoder {

@Autowired
NetworkDataLandingSender networkDataLandingSender;

ObjectMapper objectMapper = new ObjectMapper();

private final static Logger LOGGER = LoggerFactory.getLogger(McafeeSIEMDecoder.class);


	public void decode(String jsonStr) {
		
		try {			
			
			SIEMRecievedJSONModel sIEMRecievedJSONModel = objectMapper.readValue(jsonStr, SIEMRecievedJSONModel.class);
					
			LOGGER.info("sIEMRecievedJSONModel {}, dataproperties {}",sIEMRecievedJSONModel.toString(),sIEMRecievedJSONModel.getData().getDataProperties());		
			
			sIEMRecievedJSONModel.setType("Mcafee SIEM");
			
			networkDataLandingSender.sendToNetworkDataLandingStreamTopic(sIEMRecievedJSONModel);
		}
		catch(Exception e) {
			LOGGER.info("in McafeeSIEMDecoder",e);
		}
	}
}
