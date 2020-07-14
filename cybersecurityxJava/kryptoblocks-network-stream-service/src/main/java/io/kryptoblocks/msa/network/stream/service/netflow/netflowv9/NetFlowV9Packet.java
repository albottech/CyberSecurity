package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.List;
import javax.annotation.Nullable;;

public interface NetFlowV9Packet {
	
	public abstract NetFlowV9Header header();

    public abstract List<NetFlowV9Template> templates();

    @Nullable
    public abstract NetFlowV9OptionTemplate optionTemplate();

    public abstract List<NetFlowV9Record> records();

    public abstract long dataLength();
   
}
