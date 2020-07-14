package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.List;

public class NetFlowV9PacketImpl implements NetFlowV9Packet{
	
	private NetFlowV9Header header;
	private List<NetFlowV9Template> templates;
	private NetFlowV9OptionTemplate optionTemplate;
	private List<NetFlowV9Record> records;
	private long dataLength;
	
	@Override
	public NetFlowV9Header header() {
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
	public NetFlowV9PacketImpl(NetFlowV9Header header, List<NetFlowV9Template> templates,
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
		return "NetFlowV9PacketImpl [header=" + header + ", templates=" + templates + ", optionTemplate="
				+ optionTemplate + ", records=" + records + ", dataLength=" + dataLength + "]";
	}
	
	

}
