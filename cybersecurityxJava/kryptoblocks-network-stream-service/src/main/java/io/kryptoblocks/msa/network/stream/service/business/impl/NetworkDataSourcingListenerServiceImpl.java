package io.kryptoblocks.msa.network.stream.service.business.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.repository.key.NetworkDataProcessActivityKey;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataProcessActivityModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;
import io.kryptoblocks.msa.network.stream.service.audit.event.NetworkDataProcessActivityEventService;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataEnrichSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSourcingListenerService;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessServiceActivity;

public class NetworkDataSourcingListenerServiceImpl implements NetworkDataSourcingListenerService{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataSourcingListenerServiceImpl.class);

	/** The network service repository. */
	@Autowired
	private NetworkServiceRepository networkServiceRepository;
	
	/** The network data process activity event service. */
	@Autowired
	NetworkDataProcessActivityEventService networkDataProcessActivityEventService;
	
	@Autowired
	NetworkDataEnrichSender networkDataEnrichSender;
	
	/** The method name. */
	private String methodName;
	/**
	 * Enrich network data.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @return the network data enrichment model
	 * @throws BusinessException the business exception
	 */
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
					networkDataProcessActivityModel.setStatus(NetworkProcessServiceActivity.Status.SUCCESS.getValue());
					// save the enrichment data to the database
					networkServiceRepository.saveNetworkDataEnrichmentModel(returnValue);
					
				}
				/*
				 * NetworkProcessServiceActivity networkProcessServiceActivity = new
				 * NetworkProcessServiceActivity();
				 * networkProcessServiceActivity.setNetworkDataProcessActivity(
				 * networkDataProcessActivityModel); networkProcessServiceActivity
				 * .setActivity(NetworkProcessServiceActivity.Type.NETWORK_DATA_ENRICHMENT.
				 * getValue());
				 * networkDataProcessActivityEventService.processNetworkProcessActivity(
				 * networkProcessServiceActivity);
				 */
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
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
	 * Derive enriched network data from source model.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @return the network data enrichment model
	 */
	private NetworkDataEnrichmentModel deriveEnrichedNetworkDataFromSourceModel(
			NetworkDataSourcingModel networkDataSourcingModel) {

		NetworkDataEnrichmentModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
}
