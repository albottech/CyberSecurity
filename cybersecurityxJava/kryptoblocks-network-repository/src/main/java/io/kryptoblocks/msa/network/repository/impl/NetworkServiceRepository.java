package io.kryptoblocks.msa.network.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.kryptoblocks.msa.network.repository.model.NetworkDataAIModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataEnrichmentModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataIndexModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataProcessActivityModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSORModel;
import io.kryptoblocks.msa.network.repository.model.NetworkDataSourcingModel;


// TODO: Auto-generated Javadoc
/**
 * The Class NetworkServiceRepository.
 */
public class NetworkServiceRepository {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkServiceRepository.class);

	/** The network entity manager factory. */
	@Autowired
	@Qualifier("networkEntityManagerFactory")
	private EntityManagerFactory networkEntityManagerFactory;
	
	

	
	/**
	 * Save network process activity.
	 *
	 * @param networkProcessActivity the network process activity
	 */
	public void saveNetworkProcessActivity(NetworkDataProcessActivityModel networkProcessActivity) {
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
	public void saveNetworkDataSourcingModel(List<NetworkDataSourcingModel> networkDataSourcingModels) {
		EntityManager em = networkEntityManagerFactory.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT); // Necessary for flushing on
												// transaction commit, AUTO
												// option flushes automatically
		em.getTransaction().begin();
		if(networkDataSourcingModels!= null) {
			for (NetworkDataSourcingModel networkDataSourcing : networkDataSourcingModels) {
				em.persist(networkDataSourcing);
			}
		} 
		
		
		em.getTransaction().commit(); // Will write
		em.close();

	}
	
	/**
	 * Save network data index model.
	 *
	 * @param networkDataIndexModel the network data index model
	 */
	public void saveNetworkDataIndexModel(NetworkDataIndexModel networkDataIndexModel) {
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
	public void saveNetworkDataSORModel(NetworkDataSORModel networkDataSORModel) {
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
	public void saveNetworkDataAIModel(NetworkDataAIModel networkDataAIModel) {
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
	public void saveNetworkDataEnrichmentModel(NetworkDataEnrichmentModel networkDataEnrichmentModel) {
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
