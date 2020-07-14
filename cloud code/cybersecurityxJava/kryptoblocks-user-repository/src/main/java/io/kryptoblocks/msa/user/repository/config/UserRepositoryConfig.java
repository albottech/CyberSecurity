package io.kryptoblocks.msa.user.repository.config;

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

import io.kryptoblocks.msa.user.repository.impl.UserServiceRepository;
import io.kryptoblocks.msa.user.repository.name.ConfigName;

@Configuration
public class UserRepositoryConfig {
		
	@Bean
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactotyBean() {
		LocalContainerEntityManagerFactoryBean userEntityManagerFactotyBean = new LocalContainerEntityManagerFactoryBean();
		userEntityManagerFactotyBean.setPersistenceUnitName(ConfigName.USER_PERSISTENCE_UNIT_NAME);
		userEntityManagerFactotyBean.setLoadTimeWeaver(userLoadTimeWeaver());
		return userEntityManagerFactotyBean;		
	}
	
	@Bean
	public InstrumentationLoadTimeWeaver userLoadTimeWeaver() {
		InstrumentationLoadTimeWeaver usrLoadTimeWeaver = new InstrumentationLoadTimeWeaver();
		return usrLoadTimeWeaver;
	}
	
	@Bean
	public DefaultPersistenceUnitManager usrDefaultPersistenceUnitManager() {
		DefaultPersistenceUnitManager usrDefaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		usrDefaultPersistenceUnitManager.setPersistenceXmlLocation(ConfigName.USER_PERSISTENCE_XML_LOCATION);
		return usrDefaultPersistenceUnitManager;		
	}
	
	@Bean
	@Primary
	public EntityManagerFactory  usrEntityManagerFactory() {	
		Map<String, String> props = new HashMap<>();
        props.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory(ConfigName.USER_PERSISTENCE_UNIT_NAME, props);
		return entityManagerFactory;			
	}
	
	@Bean
	public UserServiceRepository userRepositoryService(){
		return new UserServiceRepository();
	}
	
}
