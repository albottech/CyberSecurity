package io.kryptoblocks.msa.network.stream.service.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import io.kryptoblocks.msa.network.stream.service.siem.McafeeSIEMDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SIEMUDPServer {
	
 
	private final static Logger LOGGER = LoggerFactory.getLogger(SIEMUDPServer.class);
	
	@Autowired 
	McafeeSIEMDecoder mcafeeSIEMDecoder;
	/**
	 * Handle message.
	 *
	 * @param message the message
	 */
	public void handleMessage(Message message) {				 
			
			final byte[] payload = (byte[]) message.getPayload();
			final ByteBuf buffer = Unpooled.wrappedBuffer(payload);
			
			LOGGER.info("buffer {}",buffer.toString());	
		  		
	  		int readableBytes = buffer.readableBytes();
	  		
 			String mcaffeeStr = "McAfee_SIEM:";
 		    String jsonStr = null;
 		    String jsonSubString = null;
 			
 		    try { 	
 				
	 			  ByteBuf buf = (ByteBuf) buffer;
	 		      byte[] bytes = new byte[buf.readableBytes()];
	 		      buf.readBytes(bytes);
	 		      jsonStr = new String(bytes);
	 		      LOGGER.info("json1 string {}",jsonStr);
	 		      
	 		      if(jsonStr.contains("McAfee_SIEM")) {
	 		    	
	 		    	int ind = jsonStr.indexOf(mcaffeeStr);
	 				if (ind > 0) {
	 					jsonSubString = jsonStr.substring(ind + mcaffeeStr.length() + 1);
	 				}
	 		    	 mcafeeSIEMDecoder.decode(jsonSubString);
	 		      }
	  	    }
	  	    catch(Exception e) {
	  	    	LOGGER.info("Exception in SIEMUDPListener",e);
	  	    }
	  		  
	  		
	}
}
