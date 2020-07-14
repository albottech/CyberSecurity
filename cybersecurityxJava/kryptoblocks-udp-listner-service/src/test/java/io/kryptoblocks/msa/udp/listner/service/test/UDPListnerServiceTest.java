package io.kryptoblocks.msa.udp.listner.service.test;

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

 
 

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UDPListnerServiceTest {

	private final static Logger LOGGER = LoggerFactory.getLogger(UDPListnerServiceTest.class);

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value("${udp.listner.port}")
	private int udpPort;
	
	@Value("${udp.listner.host}")
	private String udpHost;
	
	 
	
	@Test
	public void localSignup() throws Exception {
		
		UnicastSendingMessageHandler handler =
			      new UnicastSendingMessageHandler(udpHost, udpPort);

			String payload = "Hello world";
			handler.handleMessage(MessageBuilder.withPayload(payload).build());
			Thread.sleep(1000);
			 
		
	}

}
