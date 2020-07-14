package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NetFlowV5PacketImpl implements NetFlowV5Packet{
	
	NetflowV5Header headerv5;
	
	List<NetFlowV5Record> recordsv5;
	
	long dataLengthv5;
	
	@Override
	public NetflowV5Header headerv5() {
		// TODO Auto-generated method stub
		return headerv5;
	}

	@Override
	public ImmutableList<NetFlowV5Record> recordsv5() {
		// TODO Auto-generated method stub
		return (ImmutableList<NetFlowV5Record>) recordsv5;
	}

	@Override
	public long dataLengthv5() {
		// TODO Auto-generated method stub
		return dataLengthv5;
	}

	/**
	 * @param header
	 * @param records
	 * @param dataLength
	 */
	public NetFlowV5PacketImpl(NetflowV5Header headerv5, List<NetFlowV5Record> recordsv5, long dataLengthv5) {
		super();
		this.headerv5 = headerv5;
		this.recordsv5 = recordsv5;
		this.dataLengthv5 = dataLengthv5;
	}
	
	
}
