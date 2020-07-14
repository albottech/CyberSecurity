package io.kryptoblocks.msa.app.repository.config;

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

import io.kryptoblocks.msa.app.repository.impl.AppServiceRepository;
import io.kryptoblocks.msa.app.repository.name.ConfigName;

// TODO: Auto-generated Javadoc
/**
 * The Class AppRepositoryConfig.
 */
@Configuration
public class AppRepositoryConfig {
		
	/**
	 * App entity manager factoty bean.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean appEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean appEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		appEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.APP_PERSISTENCE_UNIT_NAME);
		appEntityManagerFactotyBean.setLoadTimeWeaver(appLoadTimeWeaver());
		return appEntityManagerFactotyBean;		
	}
	
	/**
	 * App load time weaver.
	 *
	 * @return the instrumentation load time weaver
	 */
	@Bean
	public InstrumentationLoadTimeWeaver appLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver appLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return appLoadTimeWeaver;
	}
	
	/**
	 * App default persistence unit manager.
	 *
	 * @return the default persistence unit manager
	 */
	@Bean
	public DefaultPersistenceUnitManager appDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager appDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		appDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.APP_PERSISTENCE_XML_LOCATION);
		return appDefaultPersistenceUnitManager;		
	}
	
	/**
	 * App entity manager factory.
	 *
	 * @return the entity manager factory
	 */
	@Bean
	@Primary
	public EntityManagerFactory  appEntityManagerFactory() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.APP_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory;			
	}
	
	/**
	 * App repository service.
	 *
	 * @return the app service repository
	 */
	@Bean
	public AppServiceRepository appRepositoryService(){
		return new AppServiceRepository();
	}
	
}
