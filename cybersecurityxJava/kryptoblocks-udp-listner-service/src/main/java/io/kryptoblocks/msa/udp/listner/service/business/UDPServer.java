package io.kryptoblocks.msa.udp.listner.service.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;

public class UDPServer {
	
	

	private final static Logger LOGGER = LoggerFactory.getLogger(UDPServer.class);

	public void handleMessage(Message message) {
		String data = new String((byte[]) message.getPayload());
		LOGGER.info("UDP data received ...." + data);
	}
}