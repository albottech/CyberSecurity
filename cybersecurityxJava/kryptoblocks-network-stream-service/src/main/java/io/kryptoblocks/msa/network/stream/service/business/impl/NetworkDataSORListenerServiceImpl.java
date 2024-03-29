package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.repository.key.NetworkDataProcessActivityKey;
import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataProcessActivityModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataAISender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataIndexingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORListenerService;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessServiceActivity;

public class NetworkDataSORListenerServiceImpl implements NetworkDataSORListenerService{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataSORListenerServiceImpl.class);
	
	/** The method name. */
	private String methodName;
	
	@Autowired
	NetworkDataProcessActivityEventService networkDataProcessActivityEventService;
	
	@Autowired
	NetworkDataAISender networkDataAISender;
	
	@Autowired
	NetworkDataIndexingSender networkDataIndexingSender;
	
	/** The network service repository. */
	@Autowired
	private NetworkServiceRepository networkServiceRepository;
	
	/**
	 * Ai network data.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the network data AI model
	 * @throws BusinessException the business exception
	 */
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
					networkDataProcessActivityModel.setStatus(NetworkProcessServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					networkServiceRepository.saveNetworkDataAIModel(returnValue);
					networkDataAISender.sendToNetworkDataAiStreamTopic(returnValue);
				}
				NetworkProcessServiceActivity networkProcessServiceActivity = new NetworkProcessServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(NetworkProcessServiceActivity.Type.NETWORK_DATA_AI.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}
	

	/**
	 * Index network data.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the network data index model
	 * @throws BusinessException the business exception
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
					networkDataProcessActivityModel.setStatus(NetworkProcessServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					networkServiceRepository.saveNetworkDataIndexModel(returnValue);
					networkDataIndexingSender.sendToNetworkDataIndexingStreamTopic(returnValue);
				}
				NetworkProcessServiceActivity networkProcessServiceActivity = new NetworkProcessServiceActivity();
				networkProcessServiceActivity.setNetworkDataProcessActivity(networkDataProcessActivityModel);
				networkProcessServiceActivity
						.setActivity(NetworkProcessServiceActivity.Type.NETWORK_DATA_INDEX.getValue());
				networkDataProcessActivityEventService.processNetworkProcessActivity(networkProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}
	
	/**
	 * Derive indexed network data from SOR model.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the network data index model
	 */
	private NetworkDataIndexModel deriveIndexedNetworkDataFromSORModel(
			NetworkDataSORModel networkDataSORModel) {

		NetworkDataIndexModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	/**
	 * Creates the network data process activity model.
	 *
	 * @return the network data process activity model
	 */
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
				.setActivityType(NetworkProcessServiceActivity.Type.NETWORK_DATA_SOURCING.getValue());
		returnValue.setStartTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
		returnValue.setKey(processActivityKey);
		return returnValue;
		
	}
	
	/**
	 * Handle method exception.
	 *
	 * @param e the e
	 * @param methodName the method name
	 * @param message the message
	 * @throws BusinessException the business exception
	 */
	private void handleMethodException(Exception e, String methodName, String message) throws BusinessException {
		String exceptionMsg = ExceptionUtils.getFullStackTrace(e);
		LOGGER.debug("exception in {} business method: exception details are: {}", methodName, exceptionMsg);
		throw new BusinessException(exceptionMsg);
	}
	
	/**
	 * Derive A ied network data from SOR model.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the network data AI model
	 */
	private NetworkDataAIModel deriveAIedNetworkDataFromSORModel(
			NetworkDataSORModel networkDataSORModel) {

		NetworkDataAIModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
}
