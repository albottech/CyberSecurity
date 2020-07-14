package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

public interface NetFlowV9ScopeDef {
	
	 	public static final int SYSTEM = 1;
	    public static final int INTERFACE = 2;
	    public static final int LINECARD = 3;
	    public static final int NETFLOW_CACHE = 4;
	    public static final int TEMPLATE = 5;

	    public abstract int type();

	    public abstract int length();

}
