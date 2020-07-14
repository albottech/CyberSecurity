package io.kryptoblocks.msa.udp.listner.service.business.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.StringEncryptionUtil;
import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.repository.key.NetworkDataProcessActivityKey;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataProcessActivityModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.udp.listner.service.audit.event.UDPListnerActivityEventService;
import io.kryptoblocks.msa.udp.listner.service.business.UDPListnerService;
import io.kryptoblocks.msa.udp.listner.service.business.UDPListnerStreamService;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerStatus;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerInput;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerServiceActivity;

/**
 
 *
 */

public class UDPListnerServiceImpl implements UDPListnerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UDPListnerServiceImpl.class);

	@Autowired
	private NetworkServiceRepository networkServiceRepository;

	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	@Autowired
	UDPListnerActivityEventService networkDataProcessActivityEventService;
	
	@Autowired
	UDPListnerStreamService networkDataStreamService;

	private String methodName;

	@Override
	public NetworkDataSourcingModel sourceNetworkData(Object networkSourceData) throws BusinessException {

		NetworkDataSourcingModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (networkSourceData != null) {
				NetworkDataProcessActivityModel networkDataProcessActivityModel = createNetworkDataProcessActivityModel();

				// TODO
				// convert the input data model to the finalizedNetworkSourcingData using
				// mapping method.
				returnValue = formalizeNetworkDataSourcingModel(networkSourceData);

				if (returnValue != null) {
					networkDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					networkDataProcessActivityModel.setStatus(UDPListnerServiceActivity.Status.SUCCESS.getValue());
					// save the data to the database
					networkServiceRepository.saveNetworkDataSourcingModel(returnValue);
				}

				UDPListnerServiceActivity networkProcessServiceActivity = new UDPListnerServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(UDPListnerServiceActivity.Type.NETWORK_DATA_SOURCING.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);

			}

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}

		return returnValue;
	}

	@Override
	public NetworkDataEnrichmentModel enrichNetworkData(NetworkDataSourcingModel networkDataSourcingModel) throws BusinessException {
		
		NetworkDataEnrichmentModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (networkDataSourcingModel != null) {
				NetworkDataProcessActivityModel networkDataProcessActivityModel = createNetworkDataProcessActivityModel();
				returnValue = deriveEnrichedNetworkDataFromSourceModel(networkDataSourcingModel);
				if (returnValue != null) {
					networkDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					networkDataProcessActivityModel.setStatus(UDPListnerServiceActivity.Status.SUCCESS.getValue());
					// save the enrichment data to the database
					networkServiceRepository.saveNetworkDataEnrichmentModel(returnValue);
					networkDataStreamService.sendToNetworkDataEnrichmentStreamTopic(returnValue);
				}
				UDPListnerServiceActivity networkProcessServiceActivity = new UDPListnerServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(UDPListnerServiceActivity.Type.NETWORK_DATA_ENRICHMENT.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	
	@Override
	public NetworkDataSORModel sorNetworkData(NetworkDataEnrichmentModel networkDataEnrichmentModel) throws BusinessException {
		NetworkDataSORModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (networkDataEnrichmentModel != null) {
				NetworkDataProcessActivityModel networkDataProcessActivityModel = createNetworkDataProcessActivityModel();
				returnValue = deriveSoredNetworkDataFromEnrichedModel(networkDataEnrichmentModel);
				if (returnValue != null) {
					networkDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					networkDataProcessActivityModel.setStatus(UDPListnerServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					networkServiceRepository.saveNetworkDataSORModel(returnValue);
					networkDataStreamService.sendToNetworkDataSorStreamTopic(returnValue);
				}
				UDPListnerServiceActivity networkProcessServiceActivity = new UDPListnerServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(UDPListnerServiceActivity.Type.NETWORK_DATA_SOR.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}
	
	/*
	 * @Override public Object postSORNetworkData(Object postSORObject) throws
	 * BusinessException { // TODO Auto-generated method stub
	 * 
	 * 
	 * try { methodName = new Object() {
	 * }.getClass().getEnclosingMethod().getName();
	 * 
	 * String inputObjectClassName = postSORObject.getClass().getSimpleName();
	 * 
	 * if (postSORObject != null) { NetworkDataProcessActivityModel
	 * networkDataProcessActivityModel = createNetworkDataProcessActivityModel();
	 * 
	 * switch (inputObjectClassName){
	 * 
	 * case NetworkDataAIModel.class.getSimpleName():
	 * deriveAIedNetworkDataFromSORModel break;
	 * 
	 * case NetworkDataIndexModel.class.getSimpleName(): break;
	 * 
	 * } }
	 * 
	 * 
	 * if (networkDataEnrichmentModel != null) { NetworkDataProcessActivityModel
	 * networkDataProcessActivityModel = createNetworkDataProcessActivityModel();
	 * returnValue =
	 * deriveSoredNetworkDataFromEnrichedModel(networkDataEnrichmentModel); if
	 * (returnValue != null) { networkDataProcessActivityModel.setEndTime(DateType.
	 * DATE_FORMAT_WITH_NANO_SECONDS.getValue());
	 * networkDataProcessActivityModel.setStatus(NetworkProcessServiceActivity.
	 * Status.SUCCESS.getValue()); // save the SOR data to the database
	 * networkServiceRepository.saveNetworkDataSORModel(returnValue);
	 * networkDataStreamService.sendToNetworkDataSorStreamTopic(returnValue); }
	 * NetworkProcessServiceActivity networkProcessServiceActivity = new
	 * NetworkProcessServiceActivity();
	 * networkProcessServiceActivity.setNetworkDataProcessActivity(
	 * networkDataProcessActivityModel); networkProcessServiceActivity
	 * .setActivity(NetworkProcessServiceActivity.Type.NETWORK_DATA_SOR.getValue());
	 * networkDataProcessActivityEventService.processNetworkProcessActivity(
	 * networkProcessServiceActivity); } } catch (Exception e) {
	 * handleMethodException(e, methodName, null); } return returnValue; }
	 */

	@Override
	public NetworkDataIndexModel indexNetworkData(NetworkDataSORModel  networkDataSORModel) throws BusinessException {
		NetworkDataIndexModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (networkDataSORModel != null) {
				NetworkDataProcessActivityModel networkDataProcessActivityModel = createNetworkDataProcessActivityModel();
				returnValue = deriveIndexedNetworkDataFromSORModel(networkDataSORModel);
				if (returnValue != null) {
					networkDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					networkDataProcessActivityModel.setStatus(UDPListnerServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					networkServiceRepository.saveNetworkDataIndexModel(returnValue);
					networkDataStreamService.sendToNetworkDataIndexingStreamTopic(returnValue);
				}
				UDPListnerServiceActivity networkProcessServiceActivity = new UDPListnerServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(UDPListnerServiceActivity.Type.NETWORK_DATA_INDEX.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	@Override
	public NetworkDataAIModel aiNetworkData(NetworkDataSORModel networkDataSORModel) throws BusinessException {
		NetworkDataAIModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (networkDataSORModel != null) {
				NetworkDataProcessActivityModel networkDataProcessActivityModel = createNetworkDataProcessActivityModel();
				returnValue = deriveAIedNetworkDataFromSORModel(networkDataSORModel);
				if (returnValue != null) {
					networkDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					networkDataProcessActivityModel.setStatus(UDPListnerServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					networkServiceRepository.saveNetworkDataAIModel(returnValue);
					networkDataStreamService.sendToNetworkDataAiStreamTopic(returnValue);
				}
				UDPListnerServiceActivity networkProcessServiceActivity = new UDPListnerServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(UDPListnerServiceActivity.Type.NETWORK_DATA_AI.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	
	
	@Override
	public UDPListnerStatus getNetworkDataProcessStatus(UDPListnerInput networkProcessInput)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	private NetworkDataSourcingModel formalizeNetworkDataSourcingModel(Object sourcingInput) {
		NetworkDataSourcingModel returnValue = new NetworkDataSourcingModel();
		return returnValue;

	}

	private void handleMethodException(Exception e, String methodName, String message) throws BusinessException {
		String exceptionMsg = ExceptionUtils.getFullStackTrace(e);
		LOGGER.debug("exception in {} business method: exception details are: {}", methodName, exceptionMsg);
		throw new BusinessException(exceptionMsg);
	}
	
	
	private NetworkDataEnrichmentModel deriveEnrichedNetworkDataFromSourceModel(
			NetworkDataSourcingModel networkDataSourcingModel) {

		NetworkDataEnrichmentModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
	private NetworkDataSORModel deriveSoredNetworkDataFromEnrichedModel(
			NetworkDataEnrichmentModel networkDataEnrichmentModel) {

		NetworkDataSORModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
	private NetworkDataAIModel deriveAIedNetworkDataFromSORModel(
			NetworkDataSORModel networkDataSORModel) {

		NetworkDataAIModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
	private NetworkDataIndexModel deriveIndexedNetworkDataFromSORModel(
			NetworkDataSORModel networkDataSORModel) {

		NetworkDataIndexModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}

	private NetworkDataProcessActivityModel createNetworkDataProcessActivityModel() {
		NetworkDataProcessActivityModel returnValue = null;
		
		NetworkDataProcessActivityKey processActivityKey = new NetworkDataProcessActivityKey();

		// TODO
		// need to get the client from configured source
		processActivityKey.setClient("KRYPTOBLOCKS");
		processActivityKey.setDataCenter("KRYPTOBLOCKS_DATA_CENTER");
		processActivityKey.setRountingIdentifier("KRYPTOBLOCKS_ROUTER");
		processActivityKey.setTimeStamp(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());

		returnValue = new NetworkDataProcessActivityModel();
		returnValue
				.setActivityType(UDPListnerServiceActivity.Type.NETWORK_DATA_SOURCING.getValue());
		returnValue.setStartTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
		returnValue.setKey(processActivityKey);
		return returnValue;
		
	}
	

}
