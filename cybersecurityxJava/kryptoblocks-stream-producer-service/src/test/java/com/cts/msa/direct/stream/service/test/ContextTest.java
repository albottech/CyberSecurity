package com.cts.msa.direct.stream.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import io.kryptoblocks.msa.common.stream.sender.DirectActivityStreamMessageSender;
//import io.kryptoblocks.msa.common.stream.sender.DirectActivityStreamMessagePollSender;


@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContextTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	DirectActivityStreamMessageSender directActivityStreamMessageSender;
	
	 

	@Test
	public void contextLoads() {
		for(int i =0; i <500000; i++) {
		directActivityStreamMessageSender.processMessage("Hello@@@@@@@@@@ ===" + i);
		}
	}
}
