package io.kryptoblocks.msa.common.config;


import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.common.business.activity.BusinessActivityFilter;
import io.kryptoblocks.msa.data.nfr.business.activity.model.BusinessActivity;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.RequestDetails;
import io.kryptoblocks.msa.data.nfr.business.activity.udt.ResponseDetails;
 
 
// TODO: Auto-generated Javadoc
/**
 * The Class BusinessActivityConfig.
 */
@Configuration
public class BusinessActivityConfig {
	
	/**
	 * Business activity filter.
	 *
	 * @return the filter
	 */
	@Bean
	public Filter businessActivityFilter() {
		return new BusinessActivityFilter();
	}
	
	/**
	 * Filter registation bean.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean filterRegistationBean() {
		return new FilterRegistrationBean(businessActivityFilter());
	}
	
	/**
	 * Business activity.
	 *
	 * @return the business activity
	 */
	@Bean
	public BusinessActivity businessActivity() {
		return new BusinessActivity();		
	}
	
	/**
	 * Response details.
	 *
	 * @return the response details
	 */
	@Bean
	public ResponseDetails responseDetails() {
		return new ResponseDetails();		
	}
	
	/**
	 * Request details.
	 *
	 * @return the request details
	 */
	@Bean
	public RequestDetails requestDetails() {
		return new RequestDetails();		
	}
	
}
