package io.kryptoblocks.msa.network.stream.service.business.impl;

import java.util.List;

import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXHeader;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetFlowV5RecordImpl;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetflowV5HeaderImpl;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Header;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9OptionTemplate;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Record;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Template;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecievedJSONModel {

	private NetflowV5HeaderImpl headerv5;
	private List<NetFlowV5RecordImpl> recordsv5;
	private long dataLengthv5;
	private  NetFlowV9Header headerv9;
	private List<NetFlowV9Template> templates;
	private NetFlowV9OptionTemplate optionTemplate;
	private List<NetFlowV9Record> recordsv9;
	private long dataLength;
	private IPFIXHeader headeripfix;
	
	@Override
	public String toString() {
		return "RecievedJSONModel [headerv5=" + headerv5 + ", recordsv5=" + recordsv5 + ", dataLengthv5=" + dataLengthv5
				+ ", headerv9=" + headerv9 + ", templates=" + templates + ", optionTemplate=" + optionTemplate
				+ ", recordsv9=" + recordsv9 + ", dataLength=" + dataLength + ", headeripfix=" + headeripfix + "]";
	}

	@Getter
	@Setter
	static class HeadersKafka {

		private String spanTraceId;
		private String spanId;
		private int kafka_offset;
		private boolean messageSent;
		private String id;
		private int kafka_receivedPartitionId;
		private int spanSampled;
		private String contentType;
		private String kafka_receivedTopic;
		private String spanName;

		@Override
		public String toString() {
			return "HeadersKafka [spanTraceId=" + spanTraceId + ", spanId=" + spanId + ", kafka_offset=" + kafka_offset
					+ ", messageSent=" + messageSent + ", id=" + id + ", kafka_receivedPartitionId="
					+ kafka_receivedPartitionId + ", spanSampled=" + spanSampled + ", contentType=" + contentType
					+ ", kafka_receivedTopic=" + kafka_receivedTopic + ", spanName=" + spanName + "]";
		}
	}


}
