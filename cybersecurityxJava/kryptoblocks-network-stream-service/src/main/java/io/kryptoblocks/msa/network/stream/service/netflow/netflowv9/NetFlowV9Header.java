package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

public interface NetFlowV9Header {
	 // 2bytes, 9
    public abstract int version();

    // 2bytes, both template and flow count
    public abstract int count();

    // 4bytes
    public abstract long sysUptime();

    // 4bytes, seconds since 0000 Coordinated Universal Time (UTC) 1970
    public abstract long unixSecs();

    // 4bytes, Incremental sequence counter of all export packets sent by this
    // export device(); this value is cumulative, and it can be used to identify
    // whether any export packets have been missed
    public abstract long sequence();

    // 4bytes
    public abstract long sourceId();

	public abstract String  prettyHexDump();
}
