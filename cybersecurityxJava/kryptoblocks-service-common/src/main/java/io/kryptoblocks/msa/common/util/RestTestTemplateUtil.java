package io.kryptoblocks.msa.common.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data
public class RestTestTemplateUtil {
	
	
	/** The rest test template. */
	private TestRestTemplate restTestTemplate;	
	
	/**
	 * Instantiates a new rest test template util.
	 *
	 * @param restmplate the restmplate
	 */
	public RestTestTemplateUtil(TestRestTemplate restmplate) {
		this.setRestTestTemplate(restmplate);
		
	}
	
	/**
	 * Gets the api endpoint.
	 *
	 * @param api the api
	 * @return the api endpoint
	 */
	public String getApiEndpoint(String api) {
		StringBuilder builder = new StringBuilder();		
		UriTemplateHandler  uriTemplateHandler  = restTestTemplate.getRestTemplate().getUriTemplateHandler();
		builder.append(((RootUriTemplateHandler) uriTemplateHandler).getRootUri());
		builder.append(api);
		return builder.toString(); 
	}
	
	/**
	 * Gets the api endpoint with query param.
	 *
	 * @param uri the uri
	 * @param queryParams the query params
	 * @return the api endpoint with query param
	 */
	public String getApiEndpointWithQueryParam(String uri, Map<String, String> queryParams) {
		String finalAPIURL = getApiEndpoint(uri);		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(finalAPIURL);
		for(Map.Entry<String, String> queryParam: queryParams.entrySet()) {
			builder.queryParam(queryParam.getKey(), queryParam.getValue());
		}
		return builder.toUriString();
	}

}
