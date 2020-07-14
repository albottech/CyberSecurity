package io.kryptoblocks.msa.network.stream.service.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import io.kryptoblocks.msa.network.stream.service.exceptions.FlowException;
import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXDecoder;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetFlowv5Decoder;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Decoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NetflowUDPServer {
	
	@Autowired
	NetFlowV9Decoder netFlowV9Decoder;
	
	@Autowired
	NetFlowv5Decoder netFlowv5Decoder;
	
	@Autowired
	IPFIXDecoder iPFIXDecoder;
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(NetflowUDPServer.class);

	/**
	 * Handle message.
	 *
	 * @param message the message
	 */
	public void handleMessage(Message message) {				 
			
			final byte[] payload = (byte[]) message.getPayload();
			final ByteBuf buffer = Unpooled.wrappedBuffer(payload);
			
							
			try {
					short version = 0;
									    
					version = buffer.getShort(0);
				 				    		    	
		    	    switch (version) {
		    	    
				    case 5:	netFlowv5Decoder.decode(buffer);
				    		break;
				    			    		
				    case 9:	netFlowV9Decoder.decode(buffer);
				    		break;
				    		
				    case 10:iPFIXDecoder.decode(buffer);
				   		break;
		    	    }
		    	    
			}catch(FlowException e) { 
		    	LOGGER.error("Error parsing NetFlow packet received from <{}>", e);
		    }
	}
}
