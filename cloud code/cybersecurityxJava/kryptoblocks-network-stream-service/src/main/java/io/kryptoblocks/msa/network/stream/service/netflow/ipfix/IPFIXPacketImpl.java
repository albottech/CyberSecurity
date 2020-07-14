package io.kryptoblocks.msa.network.stream.service.netflow.ipfix;

import java.util.List;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9OptionTemplate;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Record;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Template;

public class IPFIXPacketImpl implements IPFIXPacket{
	
	private IPFIXHeader header;
	private List<NetFlowV9Template> templates;
	private NetFlowV9OptionTemplate optionTemplate;
	private List<NetFlowV9Record> records;
	private long dataLength;
	
	@Override
	public IPFIXHeader header() {
		return header;
	}

	@Override
	public List<NetFlowV9Template> templates() {
		return templates;
	}

	@Override
	public NetFlowV9OptionTemplate optionTemplate() {
		return optionTemplate;
	}

	@Override
	public List<NetFlowV9Record> records() {
		return records;
	}

	@Override
	public long dataLength() {
		return dataLength;
	}

	/**
	 * @param header
	 * @param templates
	 * @param optionTemplate
	 * @param records
	 * @param dataLength
	 */
	public IPFIXPacketImpl(IPFIXHeader header, List<NetFlowV9Template> templates,
			NetFlowV9OptionTemplate optionTemplate, List<NetFlowV9Record> records, long dataLength) {
		super();
		this.header = header;
		this.templates = templates;
		this.optionTemplate = optionTemplate;
		this.records = records;
		this.dataLength = dataLength;
	}

	@Override
	public String toString() {
		return "IPFIXPacketImpl [header=" + header + ", templates=" + templates + ", optionTemplate=" + optionTemplate
				+ ", records=" + records + ", dataLength=" + dataLength + "]";
	}
	
	

}
