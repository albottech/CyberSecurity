package io.kryptoblocks.msa.network.stream.service.config;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import io.kryptoblocks.msa.network.stream.service.udp.NetflowUDPServer;

  
// TODO: Auto-generated Javadoc
/**
 * The Class NetflowUDPConfig.
 */
@Configuration 
@ConditionalOnExpression("${udp.netflow.listner.enabled}")
public class NetflowUDPConfig {
    
	/** The Constant LOGGER. */
	private final static Logger log = LoggerFactory.getLogger(NetflowUDPConfig.class);
	
	/** The udp port. */
	@Value("${udp.netflow.listner.port}")
	private int udpPort;
	

	/**
	 * UDP server.
	 *
	 * @return the UDP server
	 */
	@Bean
	public NetflowUDPServer NetflowUDPServer() {
		log.info("udp listner config is enabled @ port:{}", udpPort);
		return new NetflowUDPServer();
		
	}
	 
	/**
	 * Process uni cast udp message.
	 *
	 * @return the integration flow
	 */
	@Bean
	  public IntegrationFlow processUniCastUdpMessage() {
	    return IntegrationFlows
	      .from(new UnicastReceivingChannelAdapter(udpPort))
	      .handle("NetflowUDPServer", "handleMessage")
	      .get();
	}
	


}