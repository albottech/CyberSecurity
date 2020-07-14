package io.kryptoblocks.msa.network.stream.service.netflow.ipfix;

import java.util.List;

import javax.annotation.Nullable;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9OptionTemplate;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Record;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Template;

public interface IPFIXPacket {
	public abstract IPFIXHeader header();

    public abstract List<NetFlowV9Template> templates();

    @Nullable
    public abstract NetFlowV9OptionTemplate optionTemplate();

    public abstract List<NetFlowV9Record> records();

    public abstract long dataLength();
}
