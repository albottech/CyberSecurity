package io.kryptoblocks.msa.siem.stream.service.business;

 
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.siem.repository.model.*;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataInjectInput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataInjectOutput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataProcessStatus;
import io.kryptoblocks.msa.siem.stream.service.model.SiemProcessInput;

 // TODO: Auto-generated Javadoc
/**
  * The Interface SiemDataProcessService.
  */
 public interface SiemDataProcessService {
	
	/**
	 * Source siem data.
	 *
	 * @param cce the cce
	 * @return the siem data sourcing model
	 * @throws BusinessException the business exception
	 */
	public SiemDataSourcingModel sourceSiemData(Object cce) throws BusinessException;
	
	/**
	 * Enrich siem data.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 * @return the siem data enrichment model
	 * @throws BusinessException the business exception
	 */
	public SiemDataEnrichmentModel enrichSiemData(SiemDataSourcingModel networkDataSourcingModel) throws BusinessException;
	
	/**
	 * Sor siem data.
	 *
	 * @param networkDataEnrichmentModel the network data enrichment model
	 * @return the siem data SOR model
	 * @throws BusinessException the business exception
	 */
	public SiemDataSORModel sorSiemData(SiemDataEnrichmentModel networkDataEnrichmentModel) throws BusinessException;
	
	/**
	 * Index siem data.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the siem data index model
	 * @throws BusinessException the business exception
	 */
	public SiemDataIndexModel indexSiemData(SiemDataSORModel networkDataSORModel) throws BusinessException;
	
	/**
	 * Ai siem data.
	 *
	 * @param networkDataSORModel the network data SOR model
	 * @return the siem data AI model
	 * @throws BusinessException the business exception
	 */
	public SiemDataAIModel aiSiemData(SiemDataSORModel networkDataSORModel) throws BusinessException;
	
	/**
	 * Gets the siem data process status.
	 *
	 * @param networkProcessInput the network process input
	 * @return the siem data process status
	 * @throws BusinessException the business exception
	 */
	public SiemDataProcessStatus getSiemDataProcessStatus(SiemProcessInput networkProcessInput) throws BusinessException;
	
	/**
	 * Put siem data.
	 *
	 * @param siemDataInjectInput the siem data inject input
	 * @return the siem data landing model
	 * @throws BusinessException the business exception
	 */
	public SiemDataLandingModel putSiemData(SiemDataInjectInput siemDataInjectInput) throws BusinessException;
	
}
