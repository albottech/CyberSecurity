package io.kryptoblocks.msa.common.factory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import io.kryptoblocks.msa.common.exception.ConfigException;
import io.kryptoblocks.msa.common.names.NameCollection;

// TODO: Auto-generated Javadoc
/**
 * The Class MultiBeanFactoryPostProcessor.
 */
public class MultiBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiBeanFactoryPostProcessor.class);

	/**
	 * Post process bean factory.
	 *
	 * @param beanFactory the bean factory
	 * @throws BeansException the beans exception
	 */
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		LOGGER.debug("custom post processor executing");
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
		Map<String, MultiBeanFactory> factories = beanFactory.getBeansOfType(MultiBeanFactory.class);
		LOGGER.debug("multi factory bean map count: {}", factories.size() );
		Collection<String> names = null;
		MultiBeanFactory factoryBean;
		
		for (Map.Entry<String, MultiBeanFactory> entry : factories.entrySet()) {
			 factoryBean = entry.getValue();
			
			try {
				names = factoryBean.getNames();
				for (String name : names) {
					BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(factoryBean.getObjectType())
							.setScope(BeanDefinition.SCOPE_SINGLETON).setFactoryMethod("getObject")
							.addConstructorArgValue(name).getBeanDefinition();
					definition.setFactoryBeanName(entry.getKey());
					//String finalName = entry.getKey() + NameCollection.UNDERSCORE_CHARACTER + name;
					LOGGER.debug("multy bean factory post processor bean registration name: {}", name.toUpperCase());
					registry.registerBeanDefinition(name.toUpperCase(), definition);
				}
			} catch (ConfigException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.debug("multi factory bean names: {}", names.toString());
			
		}
		
	}
	
}