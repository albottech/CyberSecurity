package io.kryptoblocks.msa.siem.stream.service.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
 

// TODO: Auto-generated Javadoc
/**
 * The Class APIConfig.
 */
@Configuration 
public class APIConfig {
	
	/** The service API root path. */
	@Value("${network.stream.service.route.root}")
	private String serviceAPIRootPath;
		 
	
	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiInfo())
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any()) 
          
          .build();                                           
    }
	/*
	 * @Bean public Docket networkDataStreamApi() { return new
	 * Docket(DocumentationType.SWAGGER_2) .groupName("network-data-stream-api")
	 * .apiInfo(apiInfo()) .select() .paths(loginPaths()) .build(); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private Predicate<String> loginPaths() { return
	 * regex(serviceAPIRootPath+"/.*"); }
	 * 
	 */
	     
	    
	    /**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("Kryptoblocks platform network data stream process API")
	                .description("API to obtain network data processing status")
	                .termsOfServiceUrl("http://kryptoblocks.io")
	                .contact("Kryptoblocks platform network data stream process API")
	                .license("Kryptoblocks License Version 1.0")
	                .licenseUrl("https://kryptoblocks.io/LICENSE")
	                .version("1.0")
	                .build();
	    }
}
