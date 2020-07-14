package com.smartride.msa.security.test;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.kryptoblocks.msa.common.stream.sender.BusinessActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.ExceptionActivityStreamMessageSender;
import io.kryptoblocks.msa.common.stream.sender.AppContainerPerformanceStreamMessageSender;

import io.kryptoblocks.msa.common.util.StringEncryptionUtil;

import io.kryptoblocks.msa.security.service.kerberos.Krb5Util;

import org.springframework.security.kerberos.client.KerberosRestTemplate;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServiceTest {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(ServiceTest.class);

	/** The rest template. */
	@Autowired
	private TestRestTemplate restTemplate;

	/** The krb 5 property initilizer. */
	@Autowired
	Krb5Util krb5PropertyInitilizer;

	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The service auth kerberos ticket header. */
	@Value("${service.auth.kerberos.ticker.header}")
	private String serviceAuthKerberosTicketHeader;

	 

	/** The service authentication API end point. */
	@Value("${service.route.authentication.path}")
	private String serviceAuthenticationAPIEndPoint;

	/** The service token verification API end point. */
	@Value("${service.route.authentication.path}")
	private String serviceTokenVerificationAPIEndPoint;

	/** The service root API end point. */
	@Value("${service.route.root.path}")
	private String serviceRootAPIEndPoint;

	/** The string encryption util. */
	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	 
	/** The performance metric stream message sender. */
	@Autowired
	AppContainerPerformanceStreamMessageSender performanceMetricStreamMessageSender;

	/** The business activity message sender. */
	@Autowired
	BusinessActivityStreamMessageSender businessActivityMessageSender;

	/** The exception message sender. */
	@Autowired
	ExceptionActivityStreamMessageSender exceptionMessageSender;
	
	/**
	 * Test encryption.
	 */
	@Test
	public void testEncryption() {
		
	}
	

	/*@Test
	public void testEncryption() {

		try {
			String encrypytedString = stringEncryptionUtil.encrypt("syk1Tuev");
			LOGGER.debug("encrupt input data : {}", "syk1Tuev");
			LOGGER.debug("encrupted data : {}", encrypytedString);
			String unEncryptedString = stringEncryptionUtil.decrypt(encrypytedString);
			LOGGER.debug("un encrupted data : {}", unEncryptedString);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Test
	public void testServiceAuthentication() {

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> loginHttpEntity = new HttpEntity<>(
				"{\"username\":\"" + serviceTestUserName + "\",\"password\":\"" + serviceTestUserPassword + "\"}",
				requestHeaders);
		for (int i = 0; i < 2; i++) {
			ResponseEntity<String> loginPostResult = restTemplate.postForEntity(serviceAuthenticationAPIEndPoint,
					loginHttpEntity, String.class);

			HttpHeaders resultHeaders = loginPostResult.getHeaders();
			for (Entry<String, List<String>> entry : resultHeaders.entrySet()) {
				System.out.println("header from server request : " + entry.getKey() + "\n");
			}
			LOGGER.debug("body from server request: " + loginPostResult.getBody());
			LOGGER.debug("status from server request: " + loginPostResult.getStatusCodeValue());

		}
	}

	@Test
	public void testRestClient() {
		try {
			KerberosRestTemplate restTemplate = new KerberosRestTemplate(null, "-");
			String response = restTemplate
					.getForObject("http://dev.api.glider.metlife.com/securityService/authentication", String.class);
			LOGGER.debug("rest client test response: {}", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

	
}
