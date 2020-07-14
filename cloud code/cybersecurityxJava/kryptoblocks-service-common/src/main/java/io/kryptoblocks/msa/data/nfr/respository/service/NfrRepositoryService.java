package io.kryptoblocks.msa.data.nfr.respository.service;

import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import io.kryptoblocks.msa.data.nfr.business.activity.key.BusinessActivityKey;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.RequestDetails;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.ResponseDetails;


// TODO: Auto-generated Javadoc
/**
 * The Class NfrRepositoryService.
 */
public class NfrRepositoryService {
	
	//@Autowired
	//EntityManager  nfrEntityManager;
	
	/** The nfr entity manager factory. */
	@Autowired
	@Qualifier("nfrEntityManagerFactory")
	EntityManagerFactory  nfrEntityManagerFactory;
	
	/**
	 * Save activity.
	 *
	 * @param activity the activity
	 */
	public void saveActivity(Object activity) {
		EntityManager  nfrEntityManager = nfrEntityManagerFactory.createEntityManager();
		nfrEntityManager.setFlushMode(FlushModeType.COMMIT); //Necessary for flushing on transaction commit, AUTO option flushes automatically
		nfrEntityManager.getTransaction().begin();
		nfrEntityManager.persist(activity);
		nfrEntityManager.getTransaction().commit();  //Will write 		 
		nfrEntityManager.close();		
	}
	
	/**
	 * Save repository object.
	 *
	 * @param activity the activity
	 */
	public void saveRepositoryObject(Object activity) {
		EntityManager  nfrEntityManager = nfrEntityManagerFactory.createEntityManager();
		nfrEntityManager.setFlushMode(FlushModeType.COMMIT); //Necessary for flushing on transaction commit, AUTO option flushes automatically
		nfrEntityManager.getTransaction().begin();
		nfrEntityManager.persist(activity);
		nfrEntityManager.getTransaction().commit();  //Will write 		
		nfrEntityManager.close();		
	}
	
	/**
	 * Save collection.
	 *
	 * @param objects the objects
	 */
	public void saveCollection(ArrayList<Object> objects) {
		EntityManager  nfrEntityManager = nfrEntityManagerFactory.createEntityManager();
		nfrEntityManager.setFlushMode(FlushModeType.COMMIT); //Necessary for flushing on transaction commit, AUTO option flushes automatically
		nfrEntityManager.getTransaction().begin();
		for(Object object: objects) {
			nfrEntityManager.persist(object);
		}		 	
		nfrEntityManager.getTransaction().commit();  //Will write 		
		nfrEntityManager.close();	
	}
		
	
}

