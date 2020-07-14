package io.kryptoblocks.msa.app.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.kryptoblocks.msa.app.repository.model.AppDataAIModel;
import io.kryptoblocks.msa.app.repository.model.AppDataEnrichmentModel;
import io.kryptoblocks.msa.app.repository.model.AppDataIndexModel;
import io.kryptoblocks.msa.app.repository.model.AppDataProcessActivityModel;
import io.kryptoblocks.msa.app.repository.model.AppDataSORModel;
import io.kryptoblocks.msa.app.repository.model.AppDataSourcingModel;


// TODO: Auto-generated Javadoc
/**
 * The Class AppServiceRepository.
 */
public class AppServiceRepository {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppServiceRepository.class);

	/** The network entity manager factory. */
	@Autowired
	@Qualifier("networkEntityManagerFactory")
	private EntityManagerFactory networkEntityManagerFactory;
	
	

	
	/**
	 * Save network process activity.
	 *
	 * @param networkProcessActivity the network process activity
	 */
	public void saveNetworkProcessActivity(AppDataProcessActivityModel networkProcessActivity) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(networkProcessActivity);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save network data sourcing model.
	 *
	 * @param networkDataSourcingModel the network data sourcing model
	 */
	public void saveNetworkDataSourcingModel(AppDataSourcingModel networkDataSourcingModel) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(networkDataSourcingModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save network data index model.
	 *
	 * @param networkDataIndexModel the network data index model
	 */
	public void saveNetworkDataIndexModel(AppDataIndexModel networkDataIndexModel) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(networkDataIndexModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save network data SOR model.
	 *
	 * @param networkDataSORModel the network data SOR model
	 */
	public void saveNetworkDataSORModel(AppDataSORModel networkDataSORModel) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(networkDataSORModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save network data AI model.
	 *
	 * @param networkDataAIModel the network data AI model
	 */
	public void saveNetworkDataAIModel(AppDataAIModel networkDataAIModel) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(networkDataAIModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}

	
	/**
	 * Save network data enrichment model.
	 *
	 * @param networkDataEnrichmentModel the network data enrichment model
	 */
	public void saveNetworkDataEnrichmentModel(AppDataEnrichmentModel networkDataEnrichmentModel) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		
		em.persist(networkDataEnrichmentModel);
		em.getTransaction().commit(); // Will write
		em.close();

	}


	
}
