package io.kryptoblocks.msa.siem.stream.service.business.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.StringEncryptionUtil;
import io.kryptoblocks.msa.siem.repository.impl.SiemServiceRepository;
import io.kryptoblocks.msa.siem.repository.key.SiemDataProcessActivityKey;
import io.kryptoblocks.msa.siem.repository.model.SiemDataAIModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataEnrichmentModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataIndexModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataLandingModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataProcessActivityModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSORModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSourcingModel;
import io.kryptoblocks.msa.siem.stream.service.audit.event.SiemDataProcessActivityEventService;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDataProcessService;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDataStreamService;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataInjectInput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataInjectOutput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataProcessStatus;
import io.kryptoblocks.msa.siem.stream.service.model.SiemProcessInput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemProcessServiceActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemDataProcessServiceImpl.
 */

public class SiemDataProcessServiceImpl implements SiemDataProcessService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SiemDataProcessServiceImpl.class);

	/** The siem service repository. */
	@Autowired
	private SiemServiceRepository siemServiceRepository;

	/** The string encryption util. */
	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	/** The siem data process activity event service. */
	@Autowired
	SiemDataProcessActivityEventService siemDataProcessActivityEventService;
	
	/** The siem data stream service. */
	@Autowired
	SiemDataStreamService siemDataStreamService;

	/** The method name. */
	private String methodName;

	/**
	 * Source siem data.
	 *
	 * @param siemSourceData the siem source data
	 * @return the siem data sourcing model
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataSourcingModel sourceSiemData(Object siemSourceData) throws BusinessException {

		SiemDataSourcingModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (siemSourceData != null) {
				SiemDataProcessActivityModel siemDataProcessActivityModel = createSiemDataProcessActivityModel();

				// TODO
				// convert the input data model to the finalizedSiemSourcingData using
				// mapping method.
				returnValue = formalizeSiemDataSourcingModel(siemSourceData);

				if (returnValue != null) {
					siemDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.Status.SUCCESS.getValue());
					// save the data to the database
					siemServiceRepository.saveSiemDataSourcingModel(returnValue);
				}

				SiemProcessServiceActivity siemProcessServiceActivity = new SiemProcessServiceActivity();
				siemProcessServiceActivity.setSiemDataProcessActivity(siemDataProcessActivityModel);
				siemProcessServiceActivity
						.setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_SOURCING.getValue());
				siemDataProcessActivityEventService.processSiemProcessActivity(siemProcessServiceActivity);

			}

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}

		return returnValue;
	}

	/**
	 * Enrich siem data.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 * @return the siem data enrichment model
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataEnrichmentModel enrichSiemData(SiemDataSourcingModel siemDataSourcingModel) throws BusinessException {
		
		SiemDataEnrichmentModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (siemDataSourcingModel != null) {
				SiemDataProcessActivityModel siemDataProcessActivityModel = createSiemDataProcessActivityModel();
				returnValue = deriveEnrichedSiemDataFromSourceModel(siemDataSourcingModel);
				if (returnValue != null) {
					siemDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.Status.SUCCESS.getValue());
					// save the enrichment data to the database
					siemServiceRepository.saveSiemDataEnrichmentModel(returnValue);
					siemDataStreamService.sendToSiemDataEnrichmentStreamTopic(returnValue);
				}
				SiemProcessServiceActivity siemProcessServiceActivity = new SiemProcessServiceActivity();
				siemProcessServiceActivity.setSiemDataProcessActivity(siemDataProcessActivityModel);
				siemProcessServiceActivity
						.setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_ENRICHMENT.getValue());
				siemDataProcessActivityEventService.processSiemProcessActivity(siemProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	
	/**
	 * Sor siem data.
	 *
	 * @param siemDataEnrichmentModel the siem data enrichment model
	 * @return the siem data SOR model
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataSORModel sorSiemData(SiemDataEnrichmentModel siemDataEnrichmentModel) throws BusinessException {
		SiemDataSORModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (siemDataEnrichmentModel != null) {
				SiemDataProcessActivityModel siemDataProcessActivityModel = createSiemDataProcessActivityModel();
				returnValue = deriveSoredSiemDataFromEnrichedModel(siemDataEnrichmentModel);
				if (returnValue != null) {
					siemDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					siemServiceRepository.saveSiemDataSORModel(returnValue);
					siemDataStreamService.sendToSiemDataSorStreamTopic(returnValue);
				}
				SiemProcessServiceActivity siemProcessServiceActivity = new SiemProcessServiceActivity();
				siemProcessServiceActivity.setSiemDataProcessActivity(siemDataProcessActivityModel);
				siemProcessServiceActivity
						.setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_SOR.getValue());
				siemDataProcessActivityEventService.processSiemProcessActivity(siemProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}
	
	/*
	 * @Override public Object postSORSiemData(Object postSORObject) throws
	 * BusinessException { // TODO Auto-generated method stub
	 * 
	 * 
	 * try { methodName = new Object() {
	 * }.getClass().getEnclosingMethod().getName();
	 * 
	 * String inputObjectClassName = postSORObject.getClass().getSimpleName();
	 * 
	 * if (postSORObject != null) { SiemDataProcessActivityModel
	 * siemDataProcessActivityModel = createSiemDataProcessActivityModel();
	 * 
	 * switch (inputObjectClassName){
	 * 
	 * case SiemDataAIModel.class.getSimpleName():
	 * deriveAIedSiemDataFromSORModel break;
	 * 
	 * case SiemDataIndexModel.class.getSimpleName(): break;
	 * 
	 * } }
	 * 
	 * 
	 * if (siemDataEnrichmentModel != null) { SiemDataProcessActivityModel
	 * siemDataProcessActivityModel = createSiemDataProcessActivityModel();
	 * returnValue =
	 * deriveSoredSiemDataFromEnrichedModel(siemDataEnrichmentModel); if
	 * (returnValue != null) { siemDataProcessActivityModel.setEndTime(DateType.
	 * DATE_FORMAT_WITH_NANO_SECONDS.getValue());
	 * siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.
	 * Status.SUCCESS.getValue()); // save the SOR data to the database
	 * siemServiceRepository.saveSiemDataSORModel(returnValue);
	 * siemDataStreamService.sendToSiemDataSorStreamTopic(returnValue); }
	 * SiemProcessServiceActivity siemProcessServiceActivity = new
	 * SiemProcessServiceActivity();
	 * siemProcessServiceActivity.setSiemDataProcessActivity(
	 * siemDataProcessActivityModel); siemProcessServiceActivity
	 * .setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_SOR.getValue());
	 * siemDataProcessActivityEventService.processSiemProcessActivity(
	 * siemProcessServiceActivity); } } catch (Exception e) {
	 * handleMethodException(e, methodName, null); } return returnValue; }
	 */

	/**
	 * Index siem data.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 * @return the siem data index model
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataIndexModel indexSiemData(SiemDataSORModel  siemDataSORModel) throws BusinessException {
		SiemDataIndexModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (siemDataSORModel != null) {
				SiemDataProcessActivityModel siemDataProcessActivityModel = createSiemDataProcessActivityModel();
				returnValue = deriveIndexedSiemDataFromSORModel(siemDataSORModel);
				if (returnValue != null) {
					siemDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					siemServiceRepository.saveSiemDataIndexModel(returnValue);
					siemDataStreamService.sendToSiemDataIndexingStreamTopic(returnValue);
				}
				SiemProcessServiceActivity siemProcessServiceActivity = new SiemProcessServiceActivity();
				siemProcessServiceActivity.setSiemDataProcessActivity(siemDataProcessActivityModel);
				siemProcessServiceActivity
						.setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_INDEX.getValue());
				siemDataProcessActivityEventService.processSiemProcessActivity(siemProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	/**
	 * Ai siem data.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 * @return the siem data AI model
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataAIModel aiSiemData(SiemDataSORModel siemDataSORModel) throws BusinessException {
		SiemDataAIModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (siemDataSORModel != null) {
				SiemDataProcessActivityModel siemDataProcessActivityModel = createSiemDataProcessActivityModel();
				returnValue = deriveAIedSiemDataFromSORModel(siemDataSORModel);
				if (returnValue != null) {
					siemDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.Status.SUCCESS.getValue());
					// save the SOR data to the database
					siemServiceRepository.saveSiemDataAIModel(returnValue);
					siemDataStreamService.sendToSiemDataAiStreamTopic(returnValue);
				}
				SiemProcessServiceActivity siemProcessServiceActivity = new SiemProcessServiceActivity();
				siemProcessServiceActivity.setSiemDataProcessActivity(siemDataProcessActivityModel);
				siemProcessServiceActivity
						.setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_AI.getValue());
				siemDataProcessActivityEventService.processSiemProcessActivity(siemProcessServiceActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	
	
	/**
	 * Gets the siem data process status.
	 *
	 * @param siemProcessInput the siem process input
	 * @return the siem data process status
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataProcessStatus getSiemDataProcessStatus(SiemProcessInput siemProcessInput)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Formalize siem data sourcing model.
	 *
	 * @param sourcingInput the sourcing input
	 * @return the siem data sourcing model
	 */
	private SiemDataSourcingModel formalizeSiemDataSourcingModel(Object sourcingInput) {
		SiemDataSourcingModel returnValue = new SiemDataSourcingModel();
		return returnValue;

	}
	
	/**
	 * Formalize siem data landing model.
	 *
	 * @param sourcingInput the sourcing input
	 * @return the siem data landing model
	 */
	private SiemDataLandingModel formalizeSiemDataLandingModel(Object sourcingInput) {
		SiemDataLandingModel returnValue = new SiemDataLandingModel();
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
	 * Derive enriched siem data from source model.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 * @return the siem data enrichment model
	 */
	private SiemDataEnrichmentModel deriveEnrichedSiemDataFromSourceModel(
			SiemDataSourcingModel siemDataSourcingModel) {

		SiemDataEnrichmentModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
	/**
	 * Derive sored siem data from enriched model.
	 *
	 * @param siemDataEnrichmentModel the siem data enrichment model
	 * @return the siem data SOR model
	 */
	private SiemDataSORModel deriveSoredSiemDataFromEnrichedModel(
			SiemDataEnrichmentModel siemDataEnrichmentModel) {

		SiemDataSORModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
	/**
	 * Derive A ied siem data from SOR model.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 * @return the siem data AI model
	 */
	private SiemDataAIModel deriveAIedSiemDataFromSORModel(
			SiemDataSORModel siemDataSORModel) {

		SiemDataAIModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}
	
	/**
	 * Derive indexed siem data from SOR model.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 * @return the siem data index model
	 */
	private SiemDataIndexModel deriveIndexedSiemDataFromSORModel(
			SiemDataSORModel siemDataSORModel) {

		SiemDataIndexModel returnValue = null;

		try {

		} catch (Exception e) {
			// TODO
		}
		return returnValue;

	}

	/**
	 * Creates the siem data process activity model.
	 *
	 * @return the siem data process activity model
	 */
	private SiemDataProcessActivityModel createSiemDataProcessActivityModel() {
		SiemDataProcessActivityModel returnValue = null;
		
		SiemDataProcessActivityKey processActivityKey = new SiemDataProcessActivityKey();

		// TODO
		// need to get the client from configured source
		processActivityKey.setClient("KRYPTOBLOCKS");
		processActivityKey.setDataCenter("KRYPTOBLOCKS_DATA_CENTER");
		processActivityKey.setRountingIdentifier("KRYPTOBLOCKS_ROUTER");
		processActivityKey.setTimeStamp(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());

		returnValue = new SiemDataProcessActivityModel();
		returnValue
				.setActivityType(SiemProcessServiceActivity.Type.SIEM_DATA_SOURCING.getValue());
		returnValue.setStartTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
		returnValue.setKey(processActivityKey);
		return returnValue;
		
	}

	/**
	 * Put siem data.
	 *
	 * @param siemDataInjectInput the siem data inject input
	 * @return the siem data landing model
	 * @throws BusinessException the business exception
	 */
	@Override
	public SiemDataLandingModel putSiemData(SiemDataInjectInput siemDataInjectInput) throws BusinessException {
		SiemDataLandingModel returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			if (siemDataInjectInput != null) {
				SiemDataProcessActivityModel siemDataProcessActivityModel = createSiemDataProcessActivityModel();

				// TODO
				// convert the input data model to the finalizedSiemSourcingData using
				// mapping method.
				returnValue = formalizeSiemDataLandingModel(siemDataInjectInput);

				if (returnValue != null) {
					siemDataProcessActivityModel.setEndTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getValue());
					siemDataProcessActivityModel.setStatus(SiemProcessServiceActivity.Status.SUCCESS.getValue());
					// send the data to KAFKA landing topic for siem data
					siemDataStreamService.sendToSiemDataLandingStreamTopic(returnValue);
				}

				SiemProcessServiceActivity siemProcessServiceActivity = new SiemProcessServiceActivity();
				 
				siemProcessServiceActivity.setSiemDataProcessActivity(siemDataProcessActivityModel);
				siemProcessServiceActivity
						.setActivity(SiemProcessServiceActivity.Type.SIEM_DATA_LANDING.getValue());
				siemDataProcessActivityEventService.processSiemProcessActivity(siemProcessServiceActivity);

			}

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}

		return returnValue;
	}

	

}
