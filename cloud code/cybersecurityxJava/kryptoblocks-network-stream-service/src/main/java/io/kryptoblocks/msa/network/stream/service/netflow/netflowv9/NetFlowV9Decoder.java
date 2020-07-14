package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.network.stream.service.business.NetworkDataLandingSender;
import io.kryptoblocks.msa.network.stream.service.exceptions.InvalidFlowVersionException;
import io.netty.buffer.ByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class NetFlowV9Decoder.
 */
public class NetFlowV9Decoder {

	@Autowired
	NetworkDataLandingSender networkDataLandingSender;

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(NetFlowV9Decoder.class);

	/** The netflow factory. */
	final NetflowFactory netflowFactory;

	/**
	 * Instantiates a new net flow V 9 decoder.
	 *
	 * @param netflowFactory the netflow factory
	 */
	public NetFlowV9Decoder(NetflowFactory netflowFactory) {
		this.netflowFactory = netflowFactory;
	}

	/**
	 * Instantiates a new net flow V 9 decoder.
	 */
	public NetFlowV9Decoder() {
		this(new NetFlowFactoryImpl());
	}

	/**
	 * Decode.
	 *
	 * @param data the data
	 * @throws Exception the exception
	 */
	public void decode(ByteBuf buffer) {
				
		/*
		 * byte[] v9JournalEntry = new byte[buffer.readableBytes()];
		 * buffer.readBytes(v9JournalEntry); final NetFlowV9Journal.RawNetflowV9
		 * rawNetflowV9 = NetFlowV9Journal.RawNetflowV9.parseFrom(v9JournalEntry);
		 * 
		 * // parse all templates used in the packet final Map<Integer,
		 * NetFlowV9Template> templateMap = Maps.newHashMap();
		 * rawNetflowV9.getTemplatesMap().forEach((templateId, byteString) -> { final
		 * NetFlowV9Template netFlowV9Template = NetFlowV9Parser.parseTemplate(
		 * Unpooled.wrappedBuffer(byteString.toByteArray()), typeRegistry);
		 * templateMap.put(templateId, netFlowV9Template); }); final
		 * NetFlowV9OptionTemplate[] optionTemplate = {null};
		 * rawNetflowV9.getOptionTemplateMap().forEach((templateId, byteString) -> {
		 * optionTemplate[0] =
		 * NetFlowV9Parser.parseOptionTemplate(Unpooled.wrappedBuffer(byteString.
		 * toByteArray()), typeRegistry); });
		 * 
		 * return rawNetflowV9.getPacketsList().stream() .map(bytes ->
		 * Unpooled.wrappedBuffer(bytes.toByteArray())) .map(buf ->
		 * NetFlowV9Parser.parsePacket(buf, typeRegistry, templateMap,
		 * optionTemplate[0])) .collect(Collectors.toList());
		 */
		
		if (null == buffer || !buffer.isReadable()) {
			log.info("Message from {} was not usable.");
			return;
		}

		final NetFlowV9Header header = parseHeader(buffer);

		List<FlowSet> flowSets = new ArrayList<>();
		DataFlowSet dataFlowSet = null;
		//NetFlowV9OptionTemplate optTemplate = optionTemplate;
		
		while (buffer.readableBytes() > 0) {
			final short flowsetID = buffer.readShort();
			log.info("Processing flowset {}", flowsetID);

			if (flowsetID == 0 || flowsetID == 1) {
				
				TemplateFlowSet templateFlowSet = null;
				
				if 	(flowsetID == 0) {		
					templateFlowSet = decodeTemplate(buffer, flowsetID);
					flowSets.add(templateFlowSet);
					
				} else if (flowsetID == 1) {
					
				//	optTemplate = parseOptionTemplate();
				}
			} else if (flowsetID >= 4 && flowsetID <= 255) {
				// Reserved set, do not read any records
				break;
			}else {
				 dataFlowSet = decodeData(buffer, flowsetID);
				//flowSets.add(dataFlowSet);
			}

			log.info("Read {}. Available {}", buffer.readerIndex(), buffer.readableBytes());
		}

		NetFlowMessagev9 message = this.netflowFactory.netflowMessagev9(header, flowSets, dataFlowSet);

		if (message != null) {
			log.info("message is not null");

			networkDataLandingSender.sendToNetworkDataLandingStreamTopic(message);

		} 

	}
	
	/**
     * Flow Header Format
     *
     * <pre>
     * |  0-1  |     version      |                                                                                                                                                                                                                                                                                                                                                                                              NetFlow export format version number                                                                                                                                                                                                                                                                                                                                                                                               |
     * |-------|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
     * | 2-3   | count            | Number of flow sets exported in this packet, both template and data (1-30).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
     * | 4-7   | sys_uptime       | Current time in milliseconds since the export device booted.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
     * | 8-11  | unix_secs        | Current count of seconds since 0000 UTC 1970.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
     * | 12-15 | package_sequence | Sequence counter of all export packets sent by the export device. Note: This is a change from the Version 5 and Version 8 headers, where this number represented total flows.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
     * | 16-19 | source_id        | A 32-bit value that is used to guarantee uniqueness for all flows exported from a particular device. (The Source ID field is the equivalent of the engine type and engine ID fields found in the NetFlow Version 5 and Version 8 headers). The format of this field is vendor specific. In Cisco's implementation, the first two bytes are reserved for future expansion, and will always be zero. Byte 3 provides uniqueness with respect to the routing engine on the exporting device. Byte 4 provides uniqueness with respect to the particular line card or Versatile Interface Processor on the exporting device. Collector devices should use the combination of the source IP address plus the Source ID field to associate an incoming NetFlow export packet with a unique instance of NetFlow on a particular device. |
     * </pre>
     */
	
	public static NetFlowV9Header parseHeader(ByteBuf bb) {
      
		final int version = bb.readUnsignedShort();
        if (version != 9) {
            throw new InvalidFlowVersionException(version);
        }

        final int count = bb.readUnsignedShort();
        final long sysUptime = bb.readUnsignedInt();
        final long unixSecs = bb.readUnsignedInt();
        final long sequence = bb.readUnsignedInt();
        final long sourceId = bb.readUnsignedInt();

        return new NetflowV9HeaderImpl(version, count, sysUptime, unixSecs, sequence, sourceId);
    }
	  /**
     * Template FlowSet Format
     *
     * <pre>
     * |    FIELD     |                                                                                                                                                                                                                                                                DESCRIPTION                                                                                                                                                                                                                                                                |
     * |--------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
     * | flowset_id   | The flowset_id is used to distinguish template records from data records. A template record always has a flowset_id in the range of 0-255. Currently template record that describes flow fields has a flowset_id of zero and the template record that describes option fields (described below) has a flowset_id of 1. A data record always has a nonzero flowset_id greater than 255.                                                                                                                                                    |
     * | length       | Length refers to the total length of this FlowSet. Because an individual template FlowSet may contain multiple template IDs (as illustrated above), the length value should be used to determine the position of the next FlowSet record, which could be either a template or a data FlowSet. Length is expressed in type/length/value (TLV) format, meaning that the value includes the bytes used for the flowset_id and the length bytes themselves, as well as the combined lengths of all template records included in this FlowSet. |
     * | template_id  | As a router generates different template FlowSets to match the type of NetFlow data it will be exporting, each template is given a unique ID. This uniqueness is local to the router that generated the template_id. Templates that define data record formats begin numbering at 256 since 0-255 are reserved for FlowSet IDs.                                                                                                                                                                                                           |
     * | field_count  | This field gives the number of fields in this template record. Because a template FlowSet may contain multiple template records, this field allows the parser to determine the end of the current template record and the start of the next.                                                                                                                                                                                                                                                                                              |
     * | field_type   | This numeric value represents the type of the field. The possible values of the field type are vendor specific. Cisco supplied values are consistent across all platforms that support NetFlow Version 9. At the time of the initial release of the NetFlow Version 9 code (and after any subsequent changes that could add new field-type definitions), Cisco provides a file that defines the known field types and their lengths. The currently defined field types are detailed below.                                                |
     * | field_length | This number gives the length of the above-defined field, in bytes.                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
     * </pre>
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
	 * Check read fully.
	 *
	 * @param input the input
	 */
	private void checkReadFully(ByteBuf input) {
		if (input.readableBytes() > 0) {
			throw new IllegalStateException(String.format("input has %s bytes remaining.", input.readableBytes()));
		}
	}


	 /**
     * Data FlowSet Format
     *
     * <pre>
     * |      FIELD       |                                                                                                                                        DESCRIPTION                                                                                                                                        |
     * |------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
     * | flowset_id       | A FlowSet ID precedes each group of records within a NetFlow Version 9 data FlowSet. The FlowSet ID maps to a (previously received) template_id. The collector and display applications should use the flowset_id to map the appropriate type and length to any field values that follow. |
     * | length           | This field gives the length of the data FlowSet. Length is expressed in TLV format, meaning that the value includes the bytes used for the flowset_id and the length bytes themselves, as well as the combined lengths of any included data records.                                      |
     * | record_N—field_M | The remainder of the Version 9 data FlowSet is a collection of field values. The type and length of the fields have been previously defined in the template record referenced by the flowset_id/template_id.                                                                              |
     * | padding          | Padding should be inserted to align the end of the FlowSet on a 32 bit boundary. Pay attention that the length field will include those padding bits.                                                                                                                                     |
     * </pre>
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
     * Options Template Format
     *
     * <pre>
     * |         FIELD         |                                                                                                                                                                                                                                          DESCRIPTION                                                                                                                                                                                                                                           |
     * |-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
     * | flowset_id = 1        | The flowset_id is used to distinguish template records from data records. A template record always has a flowset_id of 1. A data record always has a nonzero flowset_id which is greater than 255.                                                                                                                                                                                                                                                                                             |
     * | length                | This field gives the total length of this FlowSet. Because an individual template FlowSet may contain multiple template IDs, the length value should be used to determine the position of the next FlowSet record, which could be either a template or a data FlowSet. Length is expressed in TLV format, meaning that the value includes the bytes used for the flowset_id and the length bytes themselves, as well as the combined lengths of all template records included in this FlowSet. |
     * | template_id           | As a router generates different template FlowSets to match the type of NetFlow data it will be exporting, each template is given a unique ID. This uniqueness is local to the router that generated the template_id. The template_id is greater than 255. Template IDs inferior to 255 are reserved.                                                                                                                                                                                           |
     * | option_scope_length   | This field gives the length in bytes of any scope fields contained in this options template (the use of scope is described below).                                                                                                                                                                                                                                                                                                                                                             |
     * | options_length        | This field gives the length (in bytes) of any Options field definitions contained in this options template.                                                                                                                                                                                                                                                                                                                                                                                    |
     * | scope_field_N_type    | This field gives the relevant portion of the NetFlow process to which the options record refers. Currently defined values follow:                                                                                                                                                                                                                                                                                                                                                              |
     * |                       | * 0x0001 System                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
     * |                       | * 0x0002 Interface                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
     * |                       | * 0x0003 Line Card                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
     * |                       | * 0x0004 NetFlow Cache                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
     * |                       | * 0x0005 Template                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
     * |                       |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
     * |                       | For example, sampled NetFlow can be implemented on a per-interface basis, so if the options record were reporting on how sampling is configured, the scope for the report would be 0x0002 (interface).                                                                                                                                                                                                                                                                                         |
     * | scope_field_N_length  | This field gives the length (in bytes) of the Scope field, as it would appear in an options record.                                                                                                                                                                                                                                                                                                                                                                                            |
     * | option_field_N_type   | This numeric value represents the type of the field that appears in the options record. Possible values are detailed in template flow set format (above).                                                                                                                                                                                                                                                                                                                                      |
     * | option_field_N_length | This number is the length (in bytes) of the field, as it would appear in an options record.                                                                                                                                                                                                                                                                                                                                                                                                    |
     * | padding               | Padding should be inserted to align the end of the FlowSet on a 32 bit boundary. Pay attention that the length field will include those padding bits.                                                                                                                                                                                                                                                                                                                                          |
     * </pre>
     */
 /*   public static NetFlowV9OptionTemplate parseOptionTemplate(ByteBuf bb, NetFlowV9FieldTypeRegistry typeRegistry) {
        int length = bb.readUnsignedShort();
        final int templateId = bb.readUnsignedShort();

        int optionScopeLength = bb.readUnsignedShort();
        int optionLength = bb.readUnsignedShort();

        int p = bb.readerIndex();
        int endOfScope = p + optionScopeLength;
        int endOfOption = endOfScope + optionLength;
        int endOfTemplate = p - 10 + length;

        final ImmutableList.Builder<NetFlowV9ScopeDef> scopeDefs = ImmutableList.builder();
        while (bb.readerIndex() < endOfScope) {
            int scopeType = bb.readUnsignedShort();
            int scopeLength = bb.readUnsignedShort();
            scopeDefs.add(new NetFlowV9ScopeDefImpl(scopeType, scopeLength));
        }*/
   


		/**
		 * The Interface NetFlowMessage.
		 */
		public interface NetFlowMessagev9 {
	  
			 NetFlowV9Header header();
			
			 List<FlowSet> flowsets(); 
				
			 DataFlowSet dataFlowSet();
					  
		}
		
					 

	
}