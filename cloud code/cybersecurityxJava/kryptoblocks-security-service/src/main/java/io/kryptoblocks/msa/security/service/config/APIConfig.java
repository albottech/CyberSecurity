package io.kryptoblocks.msa.security.service.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// TODO: Auto-generated Javadoc
/**
 * The Class APIConfig.
 */
@Configuration 
@EnableSwagger2
public class APIConfig {
		  	    
	    /**
    	 * Login api.
    	 *
    	 * @return the docket
    	 */
    	@Bean
	    public Docket loginApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .groupName("login-api")
	                .apiInfo(apiInfo())
	                .select()
	                .paths(loginPaths())
	                .build();
	    }
	    
	    /**
    	 * Verify api.
    	 *
    	 * @return the docket
    	 */
    	@Bean
	    public Docket verifyApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .groupName("verify-api")
	                .apiInfo(apiInfo())
	                .select()
	                .paths(verifyPaths())
	                .build();
	    }
	    
	         
	    
	    /**
    	 * Login paths.
    	 *
    	 * @return the predicate
    	 */
    	private Predicate<String> loginPaths() {
	        return regex("/login.*");
	    }
	    
    	/**
    	 * Verify paths.
    	 *
    	 * @return the predicate
    	 */
    	private Predicate<String> verifyPaths() {
	        return regex("/verify.*");
	    }
	    
	     
	    
	    /**
    	 * Api info.
    	 *
    	 * @return the api info
    	 */
    	private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("Spring boot MSA security API")
	                .description("Spring boot MSA security API")
	                .termsOfServiceUrl("http://metlife.io")
	                .contact("Spring boot MSA security API")
	                .license("Apache License Version 2.0")
	                .licenseUrl("https://metelife.io/LICENSE")
	                .version("1.0")
	                .build();
	    }
}
