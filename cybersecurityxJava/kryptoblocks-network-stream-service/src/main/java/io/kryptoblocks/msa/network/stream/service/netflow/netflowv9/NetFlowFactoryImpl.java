package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXHeader;
import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXDecoder.IPFIXMessagev10;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Decoder.NetFlowMessagev9;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// TODO: Auto-generated Javadoc
/**
 * The Class NetFlowFactoryImpl.
 */
public class NetFlowFactoryImpl implements NetflowFactory {

	  /**
  	 * Netflow message.
  	 *
  	 * @param version the version
  	 * @param count the count
  	 * @param uptime the uptime
  	 * @param timestamp the timestamp
  	 * @param flowSequence the flow sequence
  	 * @param sourceID the source ID
  	 * @param sender the sender
  	 * @param recipient the recipient
  	 * @param flowsets the flowsets
  	 * @return the net flow V 9 decoder. net flow message
  	 */
  	@Override
	  public NetFlowMessagev9 netflowMessagev9( NetFlowV9Header header, List<FlowSet> flowsets, DataFlowSet dataFlowSet) {
	    return new NetFlowMessagev9Impl(header, flowsets, dataFlowSet);
	  }
  	
	  /**
	 * Netflow message.
	 *
	 * @param version the version
	 * @param count the count
	 * @param uptime the uptime
	 * @param timestamp the timestamp
	 * @param flowSequence the flow sequence
	 * @param sourceID the source ID
	 * @param sender the sender
	 * @param recipient the recipient
	 * @param flowsets the flowsets
	 * @return the net flow V 9 decoder. net flow message
	 */
	@Override
	  public IPFIXMessagev10 iPFIXMessagev10(IPFIXHeader header,
				 List<FlowSet> flowsets,DataFlowSet dataFlowSet) {
		return new IPFIXMessagev10Impl(header, flowsets, dataFlowSet);
	  }
	  /**
  	 * Template field.
  	 *
  	 * @param type the type
  	 * @param length the length
  	 * @return the net flow V 9 decoder. template field
  	 */
  	@Override
	  public TemplateField templateField(short type, short length) {
	    return new TemplateFieldImpl(type, length);
	  }

	  /**
  	 * Template flow set.
  	 *
  	 * @param flowsetID the flowset ID
  	 * @param templateID the template ID
  	 * @param fields the fields
  	 * @return the net flow V 9 decoder. template flow set
  	 */
  	@Override
	  public TemplateFlowSet templateFlowSet(short flowsetID, short templateID, List<TemplateField> fields) {
	    return new TemplateFlowSetImpl(flowsetID, templateID, fields);
	  }

	  /**
  	 * Data flow set.
  	 *
  	 * @param flowsetID the flowset ID
  	 * @param data the data
  	 * @return the net flow V 9 decoder. data flow set
  	 */
  	@Override
	  public DataFlowSet dataFlowSet(short flowsetID, byte[] data) {
	    return new DataFlowSetImpl(flowsetID, data);
	  }

	  /**
  	 * The Class NetFlowMessageImpl.
  	 */
  	@Getter @Setter
  	static class NetFlowMessagev9Impl implements NetFlowMessagev9 {
  		
  		 final NetFlowV9Header header;
	    
    	/** The flowsets. */
    	final List<FlowSet> flowsets;
    	
    	/** The dataflowsets. */
    	final DataFlowSet dataFlowSet;
	    /**
    	 * Instantiates a new net flow message impl.
    	 *
    	 * @param version the version
    	 * @param count the count
    	 * @param uptime the uptime
    	 * @param timestamp the timestamp
    	 * @param flowSequence the flow sequence
    	 * @param sourceID the source ID
    	 * @param sender the sender
    	 * @param recipient the recipient
    	 * @param flowsets the flowsets
    	 */
    	NetFlowMessagev9Impl( NetFlowV9Header header, List<FlowSet> flowsets, DataFlowSet dataFlowSet) {
	      this.header = header;
	      this.flowsets = Collections.unmodifiableList(flowsets);
	      this.dataFlowSet = dataFlowSet;
	    }

    	@Override
    	  public NetFlowV9Header header() {
  	      return this.header;
  	    }
	  
    	@Override
	    public List<FlowSet> flowsets() {
	      return this.flowsets;
	    }
    	
    	@Override
	    public DataFlowSet dataFlowSet() {
	      return this.dataFlowSet;
	    }
	  }

	  /**
  	 * The Class TemplateFieldImpl.
  	 */
  	static class TemplateFieldImpl implements TemplateField {
	    
    	/** The type. */
    	final short type;
	    
    	/** The length. */
    	final short length;

		@Override
		public short type() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public short length() {
			// TODO Auto-generated method stub
			return 0;
		}

		/**
		 * @param type
		 * @param length
		 */
		TemplateFieldImpl(short type, short length) {
			super();
			this.type = type;
			this.length = length;
		}

		public short getType() {
			return type;
		}

		public short getLength() {
			return length;
		}
    	
	  }

	  /**
  	 * The Class TemplateFlowSetImpl.
  	 */
  	static class TemplateFlowSetImpl implements TemplateFlowSet {
	    
    	/** The flowset ID. */
    	final short flowsetID;
	    
    	/** The template ID. */
    	final short templateID;
	    
    	/** The fields. */
    	final List<TemplateField> fields;

	    /**
    	 * Instantiates a new template flow set impl.
    	 *
    	 * @param flowsetID the flowset ID
    	 * @param templateID the template ID
    	 * @param fields the fields
    	 */
    	TemplateFlowSetImpl(short flowsetID, short templateID, List<TemplateField> fields) {
	      this.flowsetID = flowsetID;
	      this.templateID = templateID;
	      this.fields = fields;
	    }

	    /**
    	 * Flowset ID.
    	 *
    	 * @return the short
    	 */
    	@Override
	    public short flowsetID() {
	      return this.flowsetID;
	    }

	    /**
    	 * Template ID.
    	 *
    	 * @return the short
    	 */
    	@Override
	    public short templateID() {
	      return this.templateID;
	    }

	    /**
    	 * Fields.
    	 *
    	 * @return the list
    	 */
    	@Override
	    public List<TemplateField> fields() {
	      return this.fields;
	    }

		public short getFlowsetID() {
			return flowsetID;
		}

		public short getTemplateID() {
			return templateID;
		}

		public List<TemplateField> getFields() {
			return fields;
		}
    	
	  }

	  /**
  	 * The Class DataFlowSetImpl.
  	 */
  	@Getter @Setter
  	static class DataFlowSetImpl implements DataFlowSet {
	    
    	/** The flowset ID. */
    	final short flowsetID;
	    
    	/** The data. */
    	final byte[] data;

	    /**
    	 * Instantiates a new data flow set impl.
    	 *
    	 * @param flowsetID the flowset ID
    	 * @param data the data
    	 */
    	DataFlowSetImpl(short flowsetID, byte[] data) {
	      this.flowsetID = flowsetID;
	      this.data = data;
	    }

		@Override
		public short flowsetID() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public byte[] data() {
			// TODO Auto-generated method stub
			return null;
		}
    	
	  }
  	
	  /**
	 * The Class NetFlowMessageImpl.
	 */
  	@Getter @Setter
	static class IPFIXMessagev10Impl implements IPFIXMessagev10 {
	    
  		final IPFIXHeader header;
		    		    
	  	/** The flowsets. */
	  	final List<FlowSet> flowsets;
	  	
	  	final DataFlowSet dataFlowSet;
		    /**
		 * @param version
		 * @param length
		 * @param exportTime
		 * @param flowSequence
		 * @param observationDomainID
		 * @param sender
		 * @param recipient
		 * @param flowsets
		 */
	  	IPFIXMessagev10Impl(IPFIXHeader header,
				 List<FlowSet> flowsets,DataFlowSet dataFlowSet) {
			super();
			this.header = header;
			this.flowsets = flowsets;
			this.dataFlowSet = dataFlowSet;
		}
	
		/**
	  	 * 
	  	 *
	  	 * @return the IPFIXHeader
	  	 */
	  	@Override
		    public IPFIXHeader header() {
		      return this.header;
		    }
		  	
	    /**
    	 * Flowsets.
    	 *
    	 * @return the list
    	 */
    	@Override
	    public List<FlowSet> flowsets() {
	      return this.flowsets;
	    }
    	
	  	@Override
	  	public DataFlowSet dataFlowSet() {
	  		return this.dataFlowSet;
	  	}
			  	
	}
}