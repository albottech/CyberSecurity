package io.kryptoblocks.msa.direct.stream.service.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import io.kryptoblocks.msa.common.stream.sender.DirectActivityStreamMessageSender;
import io.kryptoblocks.msa.common.util.DateType;
 

public class MessageProduceScheduler {
	
	@Autowired
	DirectActivityStreamMessageSender directActivityStreamMessageSender;
	
	@Scheduled(fixedRate = 20000)
	public void sendMessage() {
		directActivityStreamMessageSender.processMessage("test message at: " + DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		
	}

}
