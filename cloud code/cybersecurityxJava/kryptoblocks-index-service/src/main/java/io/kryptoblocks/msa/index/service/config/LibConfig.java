package io.kryptoblocks.msa.index.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.kryptoblocks.msa.common.config.StreamSinkConfig;


/**
 * The Class LibConfig.
 */
@Configuration
@Import({StreamSinkConfig.class})
public class LibConfig {

}
