package io.kryptoblocks.msa.user.service.test;

import java.util.List;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
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

import io.kryptoblocks.msa.user.repository.model.User;

import io.kryptoblocks.msa.user.service.model.AuthType;
import io.kryptoblocks.msa.user.service.model.SignUpInput;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserSignupTest {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserSignupTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${user.service.route.signup}")
	private String signupApiEndpoint;

	/*
	 * @Test public void localSignup() throws Exception { SignUpInput input = new
	 * SignUpInput(); input.setEmail("test@gmail.com"); input.setName("test");
	 * input.setPassword("password"); input.setRePassword("password");
	 * input.setProviderType(AuthType.SYSTEM); User output = postRequest(input);
	 * Thread.sleep(20000);
	 * //assertTrue(input.getEmail().equalsIgnoreCase(output.getEmail())); }
	 */
	@Test
	public void keepitUp() throws Exception {
		 
	}

	private User postRequest(SignUpInput input) throws Exception {

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonUserInput = null;
		jsonUserInput = objectMapper.writeValueAsString(input);
		HttpEntity<String> signupHttpEntity = new HttpEntity<>(jsonUserInput);
		ResponseEntity<String> signupPostResult = restTemplate.postForEntity(signupApiEndpoint, signupHttpEntity,
				String.class);

		HttpHeaders resultHeaders = signupPostResult.getHeaders();
		for (Entry<String, List<String>> entry : resultHeaders.entrySet()) {
			LOGGER.debug("header from signup post operation : " + entry.getKey() + "\n");
		}
		LOGGER.debug(signupPostResult.getBody());
		return null;
	}

}
