package io.kryptoblocks.msa.data.nfr.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;

import com.impetus.client.cassandra.common.CassandraConstants;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;


// TODO: Auto-generated Javadoc
/**
 * The Class RepositoryConfig.
 */
@Configuration 
public class RepositoryConfig {
	
	 
	
	
	/**
	 * Nfr entity manager factoty bean.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean nfrEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean nfrEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		nfrEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.NFR_PERSISTENCE_UNIT_NAME);
		nfrEntityManagerFactotyBean.setLoadTimeWeaver(nfrLoadTimeWeaver());
		return nfrEntityManagerFactotyBean;		
	}
	
	/**
	 * Nfr load time weaver.
	 *
	 * @return the instrumentation load time weaver
	 */
	@Bean
	public InstrumentationLoadTimeWeaver nfrLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver nfrLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return nfrLoadTimeWeaver;
	}
	
	/**
	 * Nfr persistence annotation bean post processor.
	 *
	 * @return the persistence annotation bean post processor
	 */
	@Bean
	public PersistenceAnnotationBeanPostProcessor nfrPersistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();		
	}
	
	/**
	 * Nfr default persistence unit manager.
	 *
	 * @return the default persistence unit manager
	 */
	@Bean
	public DefaultPersistenceUnitManager nfrDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager nfrDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		nfrDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.NFR_PERSISTENCE_XML_LOCATION);
		return nfrDefaultPersistenceUnitManager;		
	}
	
	/*
	@Bean
	EntityManagerFactory  nfrEntityManagerFactory() {
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        return Persistence.createEntityManagerFactory(CassandraConfigName.NFR_PERSISTENCE_UNIT_NAME, props);
    }
    */
	
	/**
	 * Nfr entity manager.
	 *
	 * @return the entity manager
	 */
	@Bean()	
	EntityManager  nfrEntityManager() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.NFR_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory.createEntityManager();			
	}
		
}
