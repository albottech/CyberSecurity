package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataSORSender;
import io.kryptoblocks.msa.network.stream.service.exceptions.CorruptFlowPacketException;
import io.kryptoblocks.msa.network.stream.service.exceptions.InvalidFlowVersionException;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetflowV5Header;
import io.kryptoblocks.msa.network.stream.service.netflow.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class NetFlowV5Decoder.
 */
public class NetFlowv5Decoder {

	 @Autowired
	 NetworkDataLandingSender networkDataLandingSender;	 

	 private static final int HEADER_LENGTH = 24;
	 private static final int RECORD_LENGTH = 48;
		  
	 /** The Constant log. */
	 private static final Logger log = LoggerFactory.getLogger(NetFlowv5Decoder.class);
	  	  	
	  
	  /**
  	 * Decode.
  	 *
  	 * @param data the data
  	 * @throws Exception the exception
  	 */  	
	public void decode(ByteBuf bb) {
	  		
	  			final int readableBytes = bb.readableBytes();
	  			
	  			final NetflowV5Header headerv5 = parseHeader(bb.slice(bb.readerIndex(), HEADER_LENGTH));	
	  			
	  			validate(headerv5);
	  			
	  			log.info("header{} count{}", headerv5.toString(),headerv5.count());
	  			
	  			
	  			final int packetLength = HEADER_LENGTH + headerv5.count() * RECORD_LENGTH;
	  				  				
	  			if (headerv5.count() <= 0 || readableBytes < packetLength) {
	  	            throw new CorruptFlowPacketException("Insufficient data (expected: " + packetLength + " bytes, actual: " + readableBytes + " bytes)");
	  	        }
			   	
	  			int offset = HEADER_LENGTH;
	  			
	  			final List<NetFlowV5Record> recordsv5 = new ArrayList<>();
	  			
	  			for (int i = 0; i < headerv5.count(); i++) {
	  				recordsv5.add(parseRecord(bb.slice(offset + bb.readerIndex(), RECORD_LENGTH)));
	  	            offset += RECORD_LENGTH;
	  	        }
			    
	  			NetFlowV5Packet packet =  new NetFlowV5PacketImpl(headerv5,recordsv5,offset);    		    
	  			
	  			log.info("packet {}", packet.toString());
	  			
			    if(packet != null) {			    	
			    	log.info("message is not null");
	  				networkDataLandingSender.sendToNetworkDataLandingStreamTopic(packet);			    	
			    }			    
	  		
	  }		 
		
	  	/*
	  	**
	     * <pre>
	     * | BYTES |     CONTENTS      |                                       DESCRIPTION                                        |
	     * |-------|-------------------|------------------------------------------------------------------------------------------|
	     * | 0-1   | version           | NetFlow export format version number                                                     |
	     * | 2-3   | count             | Number of flows exported in this packet (1-30)                                           |
	     * | 4-7   | sys_uptime        | Current time in milliseconds since the export device booted                              |
	     * | 8-11  | unix_secs         | Current count of seconds since 0000 UTC 1970                                             |
	     * | 12-15 | unix_nsecs        | Residual nanoseconds since 0000 UTC 1970                                                 |
	     * | 16-19 | flow_sequence     | Sequence counter of total flows seen                                                     |
	     * | 20    | engine_type       | Type of flow-switching engine                                                            |
	     * | 21    | engine_id         | Slot number of the flow-switching engine                                                 |
	     * | 22-23 | sampling_interval | First two bits hold the sampling mode; remaining 14 bits hold value of sampling interval |
	     * </pre>
	     */
	  private static NetflowV5Header parseHeader(ByteBuf bb) {
		    
	  		final int version = bb.readUnsignedShort();
	        if (version != 5) {
	            throw new InvalidFlowVersionException(version);
	        }

	        final int count = bb.readUnsignedShort();
	        final long sysUptime = bb.readUnsignedInt();	        
	        final long unixSecs = bb.readUnsignedInt();
	        final long unixNsecs = bb.readUnsignedInt();
	        final long flowSequence = bb.readUnsignedInt();
	        final short engineType = bb.readUnsignedByte();
	        final short engineId = bb.readUnsignedByte();
	        final short sampling = bb.readShort();
	        final int samplingMode = (sampling >> 14) & 3;
	        final int samplingInterval = sampling & 0x3fff;
	        		    
		    return new NetflowV5HeaderImpl(version, count, sysUptime, unixSecs,unixNsecs, flowSequence, engineType, engineId,samplingMode, samplingInterval);
		  }	  
	  
	  	/**
	     * <pre>
	     * | BYTES | CONTENTS  |                            DESCRIPTION                             |
	     * |-------|-----------|--------------------------------------------------------------------|
	     * | 0-3   | srcaddr   | Source IP address                                                  |
	     * | 4-7   | dstaddr   | Destination IP address                                             |
	     * | 8-11  | nexthop   | IP address of next hop router                                      |
	     * | 12-13 | input     | SNMP index of input interface                                      |
	     * | 14-15 | output    | SNMP index of output interface                                     |
	     * | 16-19 | dPkts     | Packets in the flow                                                |
	     * | 20-23 | dOctets   | Total number of Layer 3 bytes in the packets of the flow           |
	     * | 24-27 | first     | SysUptime at start of flow                                         |
	     * | 28-31 | last      | SysUptime at the time the last packet of the flow was received     |
	     * | 32-33 | srcport   | TCP/UDP source port number or equivalent                           |
	     * | 34-35 | dstport   | TCP/UDP destination port number or equivalent                      |
	     * | 36    | pad1      | Unused (zero) bytes                                                |
	     * | 37    | tcp_flags | Cumulative OR of TCP flags                                         |
	     * | 38    | prot      | IP protocol type (for example, TCP = 6; UDP = 17)                  |
	     * | 39    | tos       | IP type of service (ToS)                                           |
	     * | 40-41 | src_as    | Autonomous system number of the source, either origin or peer      |
	     * | 42-43 | dst_as    | Autonomous system number of the destination, either origin or peer |
	     * | 44    | src_mask  | Source address prefix mask bits                                    |
	     * | 45    | dst_mask  | Destination address prefix mask bits                               |
	     * | 46-47 | pad2      | Unused (zero) bytes                                                |
	     * </pre>
	     */
	  private static NetFlowV5Record parseRecord(ByteBuf bb) {
		  
	        final InetAddress srcAddr = ByteBufUtils.readInetAddress(bb);
	        final InetAddress dstAddr = ByteBufUtils.readInetAddress(bb);
	        final InetAddress nextHop = ByteBufUtils.readInetAddress(bb);
	        final int inputIface = bb.readUnsignedShort();
	        final int outputIface = bb.readUnsignedShort();
	        final long packetCount = bb.readUnsignedInt();
	        final long octetCount = bb.readUnsignedInt();
	        final long first = bb.readUnsignedInt();
	        final long last = bb.readUnsignedInt();
	        final int srcPort = bb.readUnsignedShort();
	        final int dstPort = bb.readUnsignedShort();
	        bb.readByte(); // unused pad1
	        final short tcpFlags = bb.readUnsignedByte();
	        final short protocol = bb.readUnsignedByte();
	        final short tos = bb.readUnsignedByte();
	        final int srcAs = bb.readUnsignedShort();
	        final int dstAs = bb.readUnsignedShort();
	        final short srcMask = bb.readUnsignedByte();
	        final short dstMask = bb.readUnsignedByte();

	        return new NetFlowV5RecordImpl(srcAddr, dstAddr, nextHop, inputIface, outputIface, packetCount, octetCount, first, last, srcPort, dstPort, tcpFlags, protocol, tos, srcAs, dstAs, srcMask, dstMask);
	    }
	  
	  public void validate(NetflowV5Header header) {
		  if ( header.count()<1 || header.count()> 30) {
			  throw new CorruptFlowPacketException(" flow count out of bounds, expected: [1...30] received:" + header.count());
		  }
	  }
	  	  
}
