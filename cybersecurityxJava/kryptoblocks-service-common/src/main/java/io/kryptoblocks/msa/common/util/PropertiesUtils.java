package io.kryptoblocks.msa.common.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesUtils.
 */
public class PropertiesUtils extends PropertyPlaceholderConfigurer implements Serializable {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
	 
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
	 
		/** The custom props. */
		private static CustomProperties customProps;
	 
		/** The data source name. */
		private String dataSourceName;
	 
		/**
		 * Post process bean factory.
		 *
		 * @param beanFactory the bean factory
		 * @throws BeansException the beans exception
		 */
		@Override
		public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
			DataSource dataSource = (DataSource) beanFactory.getBean(getDataSourceName());
			customProps = new CustomProperties(dataSource);
			setProperties(customProps);
			super.postProcessBeanFactory(beanFactory);
		}
	 
		/**
		 * Gets the data source name.
		 *
		 * @return the data source name
		 */
		public String getDataSourceName() {
			return dataSourceName;
		}
	 
		/**
		 * Sets the data source name.
		 *
		 * @param dataSourceName the new data source name
		 */
		public void setDataSourceName(String dataSourceName) {
			this.dataSourceName = dataSourceName;
		}
	 
		/**
		 * Gets the property.
		 *
		 * @param name the name
		 * @return the property
		 */
		public static String getProperty(String name) {
			return (null == customProps.get(name)) ? "" : customProps.get(name).toString();
		}
		
		/**
		 * The Class CustomProperties.
		 */
		public class CustomProperties extends Properties {
			 
			/** The Constant serialVersionUID. */
			//private static final Logger LOGGER = LoggerFactory.getLogger(CustomProperties.class);
			private static final long serialVersionUID = 1L;
		 
			/**
			 * Instantiates a new custom properties.
			 *
			 * @param dataSource the data source
			 */
			public CustomProperties(DataSource dataSource) {
				super();
				
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				List<Map<String, Object>> configs = jdbcTemplate
						.queryForList("select config_key, config_value from config_params");
				
				
				for (Map<String, Object> config : configs) {
					setProperty((config.get("config_key")).toString(), (config.get("config_value")).toString());
				}
			}
		}
	}