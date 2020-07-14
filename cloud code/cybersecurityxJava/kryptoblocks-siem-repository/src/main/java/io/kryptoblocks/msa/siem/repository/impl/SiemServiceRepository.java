package io.kryptoblocks.msa.siem.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.kryptoblocks.msa.siem.repository.model.SiemDataAIModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataEnrichmentModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataIndexModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataProcessActivityModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSORModel;
import io.kryptoblocks.msa.siem.repository.model.SiemDataSourcingModel;


// TODO: Auto-generated Javadoc
/**
 * The Class SiemServiceRepository.
 */
public class SiemServiceRepository {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SiemServiceRepository.class);

	/** The siem entity manager factory. */
	@Autowired
	@Qualifier("siemEntityManagerFactory")
	private EntityManagerFactory siemEntityManagerFactory;
	
	

	
	/**
	 * Save siem process activity.
	 *
	 * @param siemProcessActivity the siem process activity
	 */
	public void saveSiemProcessActivity(SiemDataProcessActivityModel siemProcessActivity) {
		EntityManager em = siemEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(siemProcessActivity);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save siem data sourcing model.
	 *
	 * @param siemDataSourcingModel the siem data sourcing model
	 */
	public void saveSiemDataSourcingModel(SiemDataSourcingModel siemDataSourcingModel) {
		EntityManager em = siemEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(siemDataSourcingModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save siem data index model.
	 *
	 * @param siemDataIndexModel the siem data index model
	 */
	public void saveSiemDataIndexModel(SiemDataIndexModel siemDataIndexModel) {
		EntityManager em = siemEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(siemDataIndexModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save siem data SOR model.
	 *
	 * @param siemDataSORModel the siem data SOR model
	 */
	public void saveSiemDataSORModel(SiemDataSORModel siemDataSORModel) {
		EntityManager em = siemEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(siemDataSORModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save siem data AI model.
	 *
	 * @param siemDataAIModel the siem data AI model
	 */
	public void saveSiemDataAIModel(SiemDataAIModel siemDataAIModel) {
		EntityManager em = siemEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(siemDataAIModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}

	
	/**
	 * Save siem data enrichment model.
	 *
	 * @param siemDataEnrichmentModel the siem data enrichment model
	 */
	public void saveSiemDataEnrichmentModel(SiemDataEnrichmentModel siemDataEnrichmentModel) {
		EntityManager em = siemEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(siemDataEnrichmentModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}


	
}
