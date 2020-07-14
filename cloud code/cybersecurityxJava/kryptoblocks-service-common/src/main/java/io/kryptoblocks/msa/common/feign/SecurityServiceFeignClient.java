package io.kryptoblocks.msa.common.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.HeaderMap;

// TODO: Auto-generated Javadoc
/**
 * The Interface SecurityServiceFeignClient.
 */
@FeignClient("http://dev.api.glider.metlife.com/securityService")
public interface SecurityServiceFeignClient {
	
	/**
	 * Authentication by get.
	 *
	 * @param headerMap the header map
	 * @return the string
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	String authenticationByGet(@HeaderMap Map<String, Object> headerMap);
	
	/**
	 * Authentication by post.
	 *
	 * @param headerMap the header map
	 * @return the string
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	String authenticationByPost(@HeaderMap Map<String, Object> headerMap);
	
	/**
	 * Verify authorization.
	 *
	 * @param headerMap the header map
	 * @return the string
	 */
	@RequestMapping(value = "/authorizationVerification", method = RequestMethod.GET)
	String verifyAuthorization(@HeaderMap Map<String, Object> headerMap);
	

}
