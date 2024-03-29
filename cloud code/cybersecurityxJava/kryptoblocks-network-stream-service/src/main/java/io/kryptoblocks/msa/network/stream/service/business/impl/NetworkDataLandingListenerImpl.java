package io.kryptoblocks.msa.network.stream.service.business.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.repository.key.NetworkDataSourcingKey;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.ElasticSearch.NetworkSourcingModelElasticSearch;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingListener;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingListnerService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDatraStream;
import io.kryptoblocks.msa.network.stream.service.business.NetworkSaveDataElasticSearch;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetFlowV5RecordImpl;


//network landing topic can be any data, so the input is generic object
//we need to transform this to SOURCING format, so that we can source it for our processing.
//Elastiflow(Netflow), ELK common schema, Splunk schema, Industry specific schema all gets converted to one excel sheet and to elastic search index format
//save json to java class and then save it to Elastic search indexing format and send it to indexing server
//convert json to AI(SOR) format and send it to AI Index builder 
public class NetworkDataLandingListenerImpl implements NetworkDataLandingListener{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataLandingListenerImpl.class);
	
	@Autowired
	NetworkDataLandingListnerService networkDataLandingListnerService;
	
	@Autowired
	RecievedJSONModel recievedJSONModel;
	
	@Autowired
	NetworkSaveDataElasticSearch networkSaveDataElasticSearch;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired 
	MappingJSONCommonModel mappingJSONCommonModel;
	
	@Autowired
	NetworkDataSourcingModel networkDataSourcingModel;
	
	@Autowired
	NetworkDataSourcingKey networkDataSourcingKey;
	
	/** The netflow factory. */
	//final NetflowFactory netflowFactory;
	List<MappingJSONCommonModel> mappingJSONCommonModelList = new ArrayList<MappingJSONCommonModel>();
	List<NetworkDataSourcingModel> networkDataSourcingModelList = new ArrayList<NetworkDataSourcingModel>();
	/**
	 * Listen to network data landing stream topic.
	 *
	 * @param input the input
	 * @throws BusinessException the business exception
	 */
	
	@Override
	@StreamListener(NetworkDatraStream.NETWORK_DATA_LANDING_STREAM_INPUT)
	public void listenToNetworkDataLandingStreamTopic(@Payload String input){
		
			try{
				
				if(input != null && input.contains("netflow")) {
					
					recievedJSONModel = (RecievedJSONModel) objectMapper.readValue(input, RecievedJSONModel.class);
					
					LOGGER.info("recievedJSONModel {}", recievedJSONModel.toString());
													
					mappingJSONCommonModelList = deriveMappingJSONCommonModel(recievedJSONModel);
					 
					LOGGER.info("mappingJSONCommonModel {}", mappingJSONCommonModel.toString());
					
					//Derive the SOURCING model
					networkDataSourcingModelList = deriveNetworkDataSourcingModel(mappingJSONCommonModelList);
					
					LOGGER.info("networkDataSourcingModelList {}", networkDataSourcingModelList.toString());
					
					//save data to Elastic Search
					networkSaveDataElasticSearch.saveNetflowDatatoElasticSearch(networkDataSourcingModelList);
					
					//From LANDING TO SOR 
					networkDataLandingListnerService.sourceNetworkData(networkDataSourcingModelList); 
				}
			}catch (Exception e) {
					LOGGER.info("In listenToNetworkDataLandingStreamTopic exception" + e);
			}
						
	}
	
	
	/**
	 * Derive network data sourcing model.
	 *
	 * @param input the input
	 * @return the network data sourcing model
	 */
	private List<NetworkDataSourcingModel> deriveNetworkDataSourcingModel(List<MappingJSONCommonModel> mappingJSONCommonModelList){
		List<NetworkDataSourcingModel> networkDataSourcingModelList = new ArrayList<NetworkDataSourcingModel>(); 
		byte[]  networkDataSourcingBytes;
		try {
			
			int i = 0;
			
			while (i < mappingJSONCommonModelList.size()) {				
			
				networkDataSourcingKey.setClient("QATAR_CLIENT");
				networkDataSourcingKey.setDataCenter("KRYPTOBLOCKS_DATA_CENTER");
				networkDataSourcingKey.setRountingIdentifier("KRYPTOBLOCKS_ROUTER");
				networkDataSourcingKey.setCollectorType(mappingJSONCommonModel.getType());
				
				
				long timeInMilliSeconds = mappingJSONCommonModel.getSysUptime();
						 			       
			    //creating Date from millisecond				
				
				Date date = new Date(timeInMilliSeconds); 
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
				formatter.setTimeZone(TimeZone.getTimeZone("ETc/UTC"));
				// Pass date object
				String timeStamp = formatter.format(date);
				System.out.println("Result: " + timeStamp);
	           			
				LOGGER.info("timeStamp {}",timeStamp);
				
				networkDataSourcingKey.setTimeStamp(timeStamp);
				
				networkDataSourcingModel.setKey(networkDataSourcingKey);
				networkDataSourcingBytes = objectMapper.writeValueAsBytes(mappingJSONCommonModel);
				networkDataSourcingModel.setNetworkDataSourcingBytes(networkDataSourcingBytes);				
				networkDataSourcingModelList.add(networkDataSourcingModel);
				i++;
			}
			
		}catch(Exception e) {
			LOGGER.info("In deriveNetworkDataSourcingModel throwing exception" + e);
		}
		
		return networkDataSourcingModelList;
	}
	

	
	public List<MappingJSONCommonModel> deriveMappingJSONCommonModel(RecievedJSONModel recievedJSONModel) {

		int version = 0; 
		int protocolType = 0; 
		String protocolName = null;  
		List<MappingJSONCommonModel> mappingJSONCommonModelList = new ArrayList<MappingJSONCommonModel>();

		try { 
			
			version = recievedJSONModel.getHeaderv5().getVersion();
				
				if (version == 5) {
					
					LOGGER.info("In version 5 for jsonmodel");
					
					List<NetFlowV5RecordImpl> records;
					records = recievedJSONModel.getRecordsv5();
					
					int i = 0; 
					while (i < records.size()) {
											
						protocolType = records.get(i).getProtocol();
			
						switch (protocolType){
			
						case 1:
							    protocolName = "ICMP"; 
								break;
						case 6: 
								protocolName = "TCP"; 
								break;
						case 14:
								protocolName = "Telnet"; 
								break;
						case 17:
								protocolName ="UDP"; 
								break;
						default:
								protocolName="No input"; 
								break;
						}
							
						mappingJSONCommonModel.setProtocolName(protocolName);
						mappingJSONCommonModel.setDuration(recievedJSONModel.getHeaderv5().getUnixSecs()-recievedJSONModel.getHeaderv5().getSysUptime() + records.get(i).getFirst());
						mappingJSONCommonModel.setSrcAddr(records.get(i).getSrcAddr());
						mappingJSONCommonModel.setSrcPort(records.get(i).getSrcPort());
						mappingJSONCommonModel.setDstAddr(records.get(i).getDstAddr());
						mappingJSONCommonModel.setDstPort(records.get(i).getDstPort());
						mappingJSONCommonModel.setSrc_bytes(records.get(i).getOctetCount());
						mappingJSONCommonModel.setPacketCount(records.get(i).getPacketCount());
						mappingJSONCommonModel.setTcpFlags(records.get(i).getTcpFlags());
						mappingJSONCommonModel.setService(records.get(i).getTos());
						
						//Additional attribute for Elastic Search
						mappingJSONCommonModel.setType("netflow version 5");
						//mappingJSONCommonModel.setTimeStamp(recievedJSONModel.getHeaderv5().getSysUptime());
						
						//Netflow v5 Header
						mappingJSONCommonModel.setCount(recievedJSONModel.getHeaderv5().getCount());
						mappingJSONCommonModel.setSysUptime(recievedJSONModel.getHeaderv5().getSysUptime());
						mappingJSONCommonModel.setUnixSecs(recievedJSONModel.getHeaderv5().getUnixSecs());
						mappingJSONCommonModel.setUnixNsecs(recievedJSONModel.getHeaderv5().getUnixNsecs());
						mappingJSONCommonModel.setFlowSequence(recievedJSONModel.getHeaderv5().getFlowSequence());
						mappingJSONCommonModel.setEngineId(recievedJSONModel.getHeaderv5().getEngineId());
						mappingJSONCommonModel.setEngineType(recievedJSONModel.getHeaderv5().getEngineType());
						mappingJSONCommonModel.setSamplingInterval(recievedJSONModel.getHeaderv5().getSamplingInterval());
						mappingJSONCommonModel.setSamplingMode(recievedJSONModel.getHeaderv5().getSamplingMode());
						mappingJSONCommonModel.setSamplingInterval(recievedJSONModel.getHeaderv5().getSamplingInterval());
						//Netflow v5 Record
						mappingJSONCommonModel.setNextHop(records.get(i).getNextHop());
						mappingJSONCommonModel.setInputIface(records.get(i).getInputIface());
						mappingJSONCommonModel.setOutputIface(records.get(i).getOutputIface());
						mappingJSONCommonModel.setOctetCount(records.get(i).getOctetCount());
						mappingJSONCommonModel.setFirst(records.get(i).getFirst());
						mappingJSONCommonModel.setLast(records.get(i).getLast());
						mappingJSONCommonModel.setProtocol(records.get(i).getProtocol());
						mappingJSONCommonModel.setSrcAs(records.get(i).getSrcAs());
						mappingJSONCommonModel.setDstAs(records.get(i).getDstAs());
						mappingJSONCommonModel.setSrcMask(records.get(i).getSrcMask());
						mappingJSONCommonModel.setDstMask(records.get(i).getDstMask());
						
						LOGGER.info("mappingJSONCommonModel {}",mappingJSONCommonModel);
						
						mappingJSONCommonModelList.add(mappingJSONCommonModel);
						i++;
					}
		
				} else if(version == 9) {
					
					LOGGER.info("In version 9 for jsonmodel");
						
					/*mappingJSONCommonModel.setProtocolName(protocolName);
					mappingJSONCommonModel.setType("netflow");
					//mappingJSONCommonModel.setDuration(recievedJSONModel.getTimestamp() -
					recievedJSONModel.getUptime() + recievedJSONModel.get
					mappingJSONCommonModel.setSourceID(recievedJSONModel.getSourceID());
					mappingJSONCommonModel.setFlowsets(recievedJSONModel.getFlowsets());
					mappingJSONCommonModel.setDataFlowSet(recievedJSONModel.getDataFlowSet());*/
			
				}else if (version == 10) {
					
					LOGGER.info("In IPFIX for jsonmodel");
		
				/*
				 * flowsets = recievedJSONModel.getFlowsets();
				 * 
				 * int i = 0; while (i < flowsets.size()) {
				 * 
				 * flowsets.get(i);
				 * 
				 * // systemInitTimeMilliseconds = get() }
				 * 
				 * mappingJSONCommonModel.setProtocolName(protocolName);
				 * mappingJSONCommonModel.setType("netflow");
				 * 
				 * mappingJSONCommonModel.setExportTime(recievedJSONModel.getExportTime());
				 * mappingJSONCommonModel.setObservationDomainID(recievedJSONModel.
				 * getObservationDomainID());
				 * mappingJSONCommonModel.setFlowsets(recievedJSONModel.getFlowsets());
				 * mappingJSONCommonModel.setDataFlowSet(recievedJSONModel.getDataFlowSet());
				 */
		
		
				}else { LOGGER.info("Netflow version is not supported"); 
				
				}
		
						

		}catch(Exception e) {
			LOGGER.info("In listenToNetworkDataLandingStreamTopic exception deriveMappingJSONCommonModel" + e);
		}

		return mappingJSONCommonModelList; 
		
	}	
		
		

	
	
}
