package io.kryptoblocks.msa.gateway;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
 

// TODO: Auto-generated Javadoc
/**
 * The Class GatewayApplication.
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableZuulProxy
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class GatewayApplication {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(GatewayApplication.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		LOGGER.debug("Starting the gateway application context");
		SpringApplication.run(GatewayApplication.class, args);
		
		
	}
}
