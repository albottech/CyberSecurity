package io.kryptoblocks.msa.siem.repository.config;

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

import io.kryptoblocks.msa.siem.repository.impl.SiemServiceRepository;
import io.kryptoblocks.msa.siem.repository.name.ConfigName;

// TODO: Auto-generated Javadoc
/**
 * The Class SiemRepositoryConfig.
 */
@Configuration
public class SiemRepositoryConfig {
		
	/**
	 * Siem entity manager factoty bean.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean siemEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean siemEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		siemEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.SIEM_PERSISTENCE_UNIT_NAME);
		siemEntityManagerFactotyBean.setLoadTimeWeaver(siemLoadTimeWeaver());
		return siemEntityManagerFactotyBean;		
	}
	
	/**
	 * Siem load time weaver.
	 *
	 * @return the instrumentation load time weaver
	 */
	@Bean
	public InstrumentationLoadTimeWeaver siemLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver siemLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return siemLoadTimeWeaver;
	}
	
	/**
	 * Siem default persistence unit manager.
	 *
	 * @return the default persistence unit manager
	 */
	@Bean
	public DefaultPersistenceUnitManager siemDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager siemDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		siemDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.SIEM_PERSISTENCE_XML_LOCATION);
		return siemDefaultPersistenceUnitManager;		
	}
	
	/**
	 * Siem entity manager factory.
	 *
	 * @return the entity manager factory
	 */
	@Bean
	@Primary
	public EntityManagerFactory  siemEntityManagerFactory() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.SIEM_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory;			
	}
	
	/**
	 * Siem repository service.
	 *
	 * @return the siem service repository
	 */
	@Bean
	public SiemServiceRepository siemRepositoryService(){
		return new SiemServiceRepository();
	}
	
}
