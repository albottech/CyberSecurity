package io.kryptoblocks.msa.network.repository.config;

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

import io.kryptoblocks.msa.network.repository.impl.NetworkServiceRepository;
import io.kryptoblocks.msa.network.repository.name.ConfigName;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkRepositoryConfig.
 */
@Configuration
public class NetworkRepositoryConfig {
		
	/**
	 * Network entity manager factoty bean.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean networkEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean networkEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		networkEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.NETWORK_PERSISTENCE_UNIT_NAME);
		networkEntityManagerFactotyBean.setLoadTimeWeaver(networkLoadTimeWeaver());
		return networkEntityManagerFactotyBean;		
	}
	
	/**
	 * Network load time weaver.
	 *
	 * @return the instrumentation load time weaver
	 */
	@Bean
	public InstrumentationLoadTimeWeaver networkLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver networkLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return networkLoadTimeWeaver;
	}
	
	/**
	 * Network default persistence unit manager.
	 *
	 * @return the default persistence unit manager
	 */
	@Bean
	public DefaultPersistenceUnitManager networkDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager networkDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		networkDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.NETWORK_PERSISTENCE_XML_LOCATION);
		return networkDefaultPersistenceUnitManager;		
	}
	
	/**
	 * Network entity manager factory.
	 *
	 * @return the entity manager factory
	 */
	@Bean
	@Primary
	public EntityManagerFactory  networkEntityManagerFactory() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.NETWORK_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory;			
	}
	
	/**
	 * Network repository service.
	 *
	 * @return the network service repository
	 */
	@Bean
	public NetworkServiceRepository networkRepositoryService(){
		return new NetworkServiceRepository();
	}
	
}
