package io.kryptoblocks.msa.network.stream.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;


 

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkDataStreamServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NetworkDataStreamServiceTest {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(NetworkDataStreamServiceTest.class);

	/** The rest template. */
	@Autowired
	private TestRestTemplate restTemplate;
	
	/** The network data stream service. */
	@Autowired
	NetworkDataLandingSender networkDataLandingSender;

	
	/** The udp port. */
	@Value("${udp.netflow.listner.port}")
	private int udpPort;
	
	/** The udp host. */
	@Value("${udp.listner.host}")
	private String udpHost;
	
	
	/**
	 * Stream test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void streamTest() throws Exception {
		
		networkDataLandingSender.sendToNetworkDataLandingStreamTopic("Hello");
		
	}
	
	
	 
	
	/**
	 * Udp test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void udpTest() throws Exception {
		
		UnicastSendingMessageHandler handler =
			      new UnicastSendingMessageHandler(udpHost, udpPort);

			String payload = "Hello world";
			handler.handleMessage(MessageBuilder.withPayload(payload).build());
			Thread.sleep(1000);
			 
		
	}


	 

}
