package io.kryptoblocks.msa.network.stream.service.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;
import io.kryptoblocks.msa.network.stream.service.business.impl.NetworkDataLandingSenderImpl;
import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXDecoder;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetFlowv5Decoder;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Decoder;
import io.kryptoblocks.msa.network.stream.service.siem.McafeeSIEMDecoder;
import io.kryptoblocks.msa.network.stream.service.udp.SIEMUDPServer;

@Configuration
@ConditionalOnExpression("${network.data.landing.output.enabled}")
public class DataLandingConfig {
		
		@Bean
		McafeeSIEMDecoder mcafeeSIEMDecoder() {
			return new McafeeSIEMDecoder();
		}
			
		@Bean
		NetFlowv5Decoder netFlowv5Decoder() {
			return new NetFlowv5Decoder();
		}
		
		@Bean
		NetFlowV9Decoder netFlowV9Decoder() {
			return new NetFlowV9Decoder();
		}
		
		@Bean
		IPFIXDecoder iPFIXDecoder() {
			return new IPFIXDecoder();
		}
		
		@Bean 
		NetworkDataLandingSender networkDataLandingSender() {
			return new NetworkDataLandingSenderImpl();
		}	
	
}
