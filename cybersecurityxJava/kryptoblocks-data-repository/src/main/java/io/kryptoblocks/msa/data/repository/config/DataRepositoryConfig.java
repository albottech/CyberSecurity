package io.kryptoblocks.msa.data.repository.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;

import com.impetus.client.cassandra.common.CassandraConstants;

import io.kryptoblocks.msa.data.repository.impl.DataServiceRepository;
import io.kryptoblocks.msa.data.repository.name.ConfigName;

// TODO: Auto-generated Javadoc
/**
 * The Class DataRepositoryConfig.
 */
@Configuration
public class DataRepositoryConfig {
		
	/**
	 * Data entity manager factoty bean.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean dataEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean dataEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		dataEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.DATA_PERSISTENCE_UNIT_NAME);
		dataEntityManagerFactotyBean.setLoadTimeWeaver(dataLoadTimeWeaver());
		return dataEntityManagerFactotyBean;		
	}
	
	/**
	 * Data load time weaver.
	 *
	 * @return the instrumentation load time weaver
	 */
	@Bean
	public InstrumentationLoadTimeWeaver dataLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver dataLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return dataLoadTimeWeaver;
	}
	
	/**
	 * Data default persistence unit manager.
	 *
	 * @return the default persistence unit manager
	 */
	@Bean
	public DefaultPersistenceUnitManager dataDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager dataDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		dataDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.DATA_PERSISTENCE_XML_LOCATION);
		return dataDefaultPersistenceUnitManager;		
	}
	
	/**
	 * Data entity manager factory.
	 *
	 * @return the entity manager factory
	 */
	@Bean
	@Primary
	public EntityManagerFactory  dataEntityManagerFactory() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.DATA_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory;			
	}
	
	/**
	 * Data repository service.
	 *
	 * @return the data service repository
	 */
	@Bean
	public DataServiceRepository dataRepositoryService(){
		return new DataServiceRepository();
	}
	
}
