package io.kryptoblocks.msa.network.stream.service.netflow.ipfix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.DataFlowSet;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.FlowSet;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowFactoryImpl;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetflowFactory;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.TemplateField;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.TemplateFlowSet;
import io.netty.buffer.ByteBuf;

public class IPFIXDecoder {
	
	/** The Constant log. */
  	private static final Logger log = LoggerFactory.getLogger(IPFIXDecoder.class);
  	
	  /** The netflow factory. */
	final NetflowFactory netflowFactory;
	
	@Autowired
	NetworkDataLandingSender networkDataLandingSender;
	  /**
	 * Instantiates a new IPFIX V 10 decoder.
	 *
	 * @param netflowFactory the IPFIX factory
	 */
	public IPFIXDecoder(NetflowFactory netflowFactory) {
	    this.netflowFactory = netflowFactory;
	  }
	
	/**
	 * Instantiates a new net flow V 9 decoder.
	 */
	public IPFIXDecoder() {
		this(new NetFlowFactoryImpl());
	}
	  /**
  	 * Decode.
  	 *
  	 * @param data the data
  	 * @throws Exception the exception
  	 */
  	public void decode(Object data) {
	    ByteBuf input = (ByteBuf) data;
	   	    
		    if (null == input || !input.isReadable()) {
		      log.info("Message from {} was not usable.");
		      return;
		    }
	
		    final IPFIXHeader header = decodeHeader(input);
		    
		    log.info("header {}",header);
		    
		    log.info("Read {} for header. {} remaining", input.readerIndex(), input.readableBytes());
	
		   List<FlowSet> flowSets = new ArrayList<>();
		   DataFlowSet dataFlowSet =null;
		   
		    while (input.readableBytes() > 0) {
		      final short flowSetID = input.readShort();
		      log.info("Processing flowset {}", flowSetID);
	
		      if (2 == flowSetID) {
		        TemplateFlowSet templateFlowSet = decodeTemplate(input, flowSetID);
		        flowSets.add(templateFlowSet);
		      } else {
		         dataFlowSet = decodeData(input, flowSetID);
		       // flowSets.add(dataFlowSet);
		      }
	
		      log.info("Read {}. Available {}", input.readerIndex(), input.readableBytes());
		    }
	
		    IPFIXMessagev10 message = this.netflowFactory.iPFIXMessagev10(
		       header,
		        flowSets,
		        dataFlowSet
		    );
		
		   if(message != null) {
			   log.info("message is not null");
			    
			   networkDataLandingSender.sendToNetworkDataLandingStreamTopic(message);
			   
		   }
		   
		 }
  	

	/**
	 * Decode data.
	 *
	 * @param b         the b
	 * @param flowSetID the flow set ID
	 * @return the data flow set
	 */
	DataFlowSet decodeData(ByteBuf b, final short flowSetID) {
		final int length = b.readShort() - 4;
		log.info("readSlice({})", length);
		final ByteBuf input = b.readSlice(length);
		byte[] data = new byte[length];
		input.readBytes(data);
		return this.netflowFactory.dataFlowSet(flowSetID, data);
	}

    /**
  	 * Decode template.
  	 *
  	 * @param b the b
  	 * @param flowSetID the flow set ID
  	 * @return the template flow set
  	 */
  	TemplateFlowSet decodeTemplate(ByteBuf b, final short flowSetID) {
	    final int length = b.readShort() - 4;
	    log.info("readSlice({})", length);
	    final ByteBuf input = b.readSlice(length);

	    short templateID = input.readShort();
	    short fieldCount = input.readShort();
	    log.info("templateID = {} fieldCount = {}", templateID, fieldCount);
	    List<TemplateField> fields = new ArrayList<>(fieldCount);
	    for (short j = 1; j <= fieldCount; j++) {
	      short fieldType = input.readShort();
	      short fieldLength = input.readShort();
	      log.info("field({}/{}): type = {} length = {}", j, fieldCount, fieldType, fieldLength);

	      TemplateField templateField = this.netflowFactory.templateField(fieldType, fieldLength);
	      fields.add(templateField);
	    }
	    checkReadFully(input);
	    return this.netflowFactory.templateFlowSet(flowSetID, templateID, fields);
	  }

    /**
  	 * Decode header.
  	 *
  	 * @param b the b
  	 * @param sender the sender
  	 * @param recipient the recipient
  	 * @return the header
  	 */
  	IPFIXHeader decodeHeader(ByteBuf b) {
	    final ByteBuf input = b.readSlice(16);

	    short version = input.readShort();
	    short length = input.readShort();
	    int exportTime = input.readInt();
	    int flowSequence = input.readInt();
	    int observationDomainID = input.readInt();

	    log.info("version = {} length = {} exportTime = {} flowSequence = {} observationDomainID = {}",
	        version, length, exportTime, flowSequence, observationDomainID
	    );

	    checkReadFully(input);
	    return new IPFIXHeaderImpl(version, length, exportTime, flowSequence, observationDomainID);
	  }

	  /**
  	 * Check read fully.
  	 *
  	 * @param input the input
  	 */
  	private void checkReadFully(ByteBuf input) {
	    if (input.readableBytes() > 0) {
	      throw new IllegalStateException(
	          String.format("input has %s bytes remaining.", input.readableBytes())
	      );
	    }
	  }
  	

	 /**
	 * The Interface NetFlowMessage v10.
	 */
	public interface IPFIXMessagev10 {
	
			 IPFIXHeader header();
			
			 List<FlowSet> flowsets(); 
			 
			 DataFlowSet dataFlowSet();
		 
	}

}