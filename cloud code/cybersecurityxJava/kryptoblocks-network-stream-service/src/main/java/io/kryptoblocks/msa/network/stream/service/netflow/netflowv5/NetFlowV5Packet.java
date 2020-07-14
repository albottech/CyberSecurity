package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import lombok.Setter;

public interface NetFlowV5Packet {
	
	public abstract NetflowV5Header headerv5();

    public abstract ImmutableList<NetFlowV5Record> recordsv5();

    public abstract long dataLengthv5();
    
    
}
