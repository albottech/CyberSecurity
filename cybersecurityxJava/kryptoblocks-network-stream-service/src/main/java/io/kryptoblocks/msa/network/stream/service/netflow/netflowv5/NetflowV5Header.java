package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

public interface NetflowV5Header {
	
		// bytes 0-1
	    public abstract int version();
	
	    // bytes 2-3
	    public abstract int count();
	
	    // bytes 4-7, milliseconds since device boot
	    public abstract long sysUptime();
	
	    // bytes 8-11, seconds since UTC 1970
	    public abstract long unixSecs();
	
	    // bytes 12-15, nanoseconds since UTC 1970
	    public abstract long unixNsecs();
	
	    // bytes 16-19, sequence counter of total flow seen
	    public abstract long flowSequence();
	
	    // bytes 20, type of flow switching engine
	    public abstract short engineType();
	
	    // bytes 21, slot number of the flow-switching engine
	    public abstract short engineId();
	
	    // bytes 22-23, first two bits hold the sampling mode, remaining 14 bits
	    // hold value of sampling interval
	    public abstract int samplingMode();
	
	    public abstract int samplingInterval();	
	   
}
