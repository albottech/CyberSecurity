package io.kryptoblocks.msa.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.kryptoblocks.msa.data.nfr.bean.service.BeanPerfInterService;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectPerformConfig.
 */
@Configuration
public class ObjectPerformConfig {
	
	/**
	 * Bean performance monitor interceptor.
	 *
	 * @return the bean perf inter service
	 */
	@Bean
	BeanPerfInterService beanPerformanceMonitorInterceptor() {
		BeanPerfInterService returnValue = new BeanPerfInterService();
		returnValue.setTrackAllInvocations(true);		
		return returnValue;
	}
	
	/**
	 * Bean name auto proxy creator.
	 *
	 * @return the bean name auto proxy creator
	 */
	@Bean
	BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		BeanNameAutoProxyCreator returnValue = new BeanNameAutoProxyCreator();
		returnValue.setInterceptorNames("beanPerformanceMonitorInterceptor");
		//returnValue.setBeanNames(beanNames);
		return returnValue;		
	}

}
