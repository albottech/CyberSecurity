/**
 * Authorization filer class. This class enable a pre filter in the gateway service to handle token availability on HTTP request header for service API endpoints except the security service
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.gateway.filter;

import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import io.kryptoblocks.msa.gateway.exception.TokenCheckException;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;



// TODO: Auto-generated Javadoc
/**
 * The Class AuthPreFilter.
 */
public class AuthPreFilter extends ZuulFilter {

	/** The logger. */
	private static Logger LOGGER = LoggerFactory.getLogger(AuthPreFilter.class);
	
	/** Metadata driven token name for the JWT auth token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;
	
	
	/** The service auth filter exception msg. */
	private String serviceAuthFilterExceptionMsg = "no token request";
	 
	
	/**
	 * Method to override the filter type as a pre filter.
	 *
	 * @return the string
	 */
	@Override
	public String filterType() {		
		return "pre";
	}
	
	/**
	 * Method to override the filter execution strategy. This will make sure that the auth filter is bypassed, in case if the HTTP request is for security service API end-points
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean shouldFilter() {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		//add a auto generated random service request id to the request header
		ctx.addZuulRequestHeader("service_name",
				ctx.get("proxy").toString());	
		
		ctx.addZuulRequestHeader("service_request_id",
				UUID.randomUUID().toString());	
		
	    if ((ctx.get("proxy") != null) && ctx.get("proxy").equals("security-service")) {
	    	LOGGER.info("Security service call bypassing the token filter");
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Method to override to set the filter order. This method will ensure that the current filter is executed at the right order in filter chain so that, it will have the proxy service ID available to make the bypass decision on the security service API
	 *
	 * @return the int
	 */
	@Override
	public int filterOrder() {
		return 999;
	}
	
	/**
	 * The original override method for filter logic. This logic will ensure that if the service request other than security API carry a http header with JWT token based on the metadata configured auth token name
	 *
	 * @return the object
	 */
	@Override
	public Object run(){

		/*try {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			LOGGER.info("Current Proxy name : {}", ctx.get("proxy"));
			LOGGER.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
			Map<String, String> requestHeaders = ctx.getZuulRequestHeaders();								
			// check on valid service authorization token
			if (requestHeaders.get(serviceAuthJwtToken) == null) {
				throw new TokenCheckException(serviceAuthFilterExceptionMsg);				
			}
		} catch (Exception e) {
			rethrowRuntimeException(e);
		}*/
		return null;
	}
		
}
