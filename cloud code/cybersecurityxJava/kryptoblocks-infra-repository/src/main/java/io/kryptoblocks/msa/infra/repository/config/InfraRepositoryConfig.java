package io.kryptoblocks.msa.infra.repository.config;

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

import io.kryptoblocks.msa.infra.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.infra.repository.name.ConfigName;

// TODO: Auto-generated Javadoc
/**
 * The Class InfraRepositoryConfig.
 */
@Configuration
public class InfraRepositoryConfig {
		
	/**
	 * Infra entity manager factoty bean.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean infraEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean infraEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		infraEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.INFRA_PERSISTENCE_UNIT_NAME);
		infraEntityManagerFactotyBean.setLoadTimeWeaver(infraLoadTimeWeaver());
		return infraEntityManagerFactotyBean;		
	}
	
	/**
	 * Infra load time weaver.
	 *
	 * @return the instrumentation load time weaver
	 */
	@Bean
	public InstrumentationLoadTimeWeaver infraLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver infraLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return infraLoadTimeWeaver;
	}
	
	/**
	 * Infra default persistence unit manager.
	 *
	 * @return the default persistence unit manager
	 */
	@Bean
	public DefaultPersistenceUnitManager infraDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager infraDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		infraDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.INFRA_PERSISTENCE_XML_LOCATION);
		return infraDefaultPersistenceUnitManager;		
	}
	
	/**
	 * Infra entity manager factory.
	 *
	 * @return the entity manager factory
	 */
	@Bean
	@Primary
	public EntityManagerFactory  infraEntityManagerFactory() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.INFRA_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory;			
	}
	
	/**
	 * Infra repository service.
	 *
	 * @return the network service repository
	 */
	@Bean
	public NetworkServiceRepository infraRepositoryService(){
		return new NetworkServiceRepository();
	}
	
}
