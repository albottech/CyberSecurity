/**
 * Service response check filter. This filter check for the quality of the service response returned from the gateway service
 * <p>
 *  
 * @author      Metlife
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class CheckPostFilter.
 */
public class CheckPostFilter extends ZuulFilter {

	/** The logger. */
	private static Logger LOGGER = LoggerFactory.getLogger(CheckPostFilter.class);
	
	 
	/**
	 * Override method to ensure that the filter is executed as a post chain filter.
	 *
	 * @return the string
	 */
	public String filterType() {
		return "post";
	}

	/**
	 * Override method to ensure that the filter is always executed.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean shouldFilter() {
	    return true;
	}
	
	/**
	 * Override method to ensure that the filter is executed at the last location of the post filter chain.
	 *
	 * @return the int
	 */

	@Override
	public int filterOrder() {
		return 999;
	}

	/**
	 * Override method of real filter logic.
	 *
	 * @return the object
	 */
	@Override
	public Object run(){

		/*try {
			RequestContext ctx = RequestContext.getCurrentContext();			
			//Map<String, String> requestHeaders = ctx.getZuulRequestHeaders();			
			List<Pair<String,String>>  responseHeaders = ctx.getZuulResponseHeaders();			
			for(Pair<String,String> responseHeader: responseHeaders ) {				
				LOGGER.info("Gateway response http header data: key :{} value : {}", responseHeader.first(), responseHeader.second());
			}
			
			
			Map<String, String> requestHeaders = ctx.getZuulRequestHeaders();
			for(Map.Entry<String, String> entry : requestHeaders.entrySet()) {
				LOGGER.info("Gateway request http header data: key :{} value : {}", entry.getKey(), entry.getValue()); 
			}
			
			 

		} catch (Exception e) {
			
		}*/
		return null;
	}

	
}
