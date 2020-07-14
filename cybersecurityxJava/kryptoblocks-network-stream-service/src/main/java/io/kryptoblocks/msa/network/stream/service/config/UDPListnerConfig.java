package io.kryptoblocks.msa.network.stream.service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The Class UDPListnerConfig.
 */

@Configuration
@Import({NetflowUDPConfig.class})
public class UDPListnerConfig {
		
}