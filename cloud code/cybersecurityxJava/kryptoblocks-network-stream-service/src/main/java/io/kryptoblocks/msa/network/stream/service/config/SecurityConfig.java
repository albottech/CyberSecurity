package io.kryptoblocks.msa.network.stream.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityConfig.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configure.
     *
     * @param httpSecurity the http security
     * @throws Exception the exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

            httpSecurity.authorizeRequests()
                        .anyRequest()
                        .permitAll()
                        .and().httpBasic().disable();

            httpSecurity.csrf().disable();              

    }
}