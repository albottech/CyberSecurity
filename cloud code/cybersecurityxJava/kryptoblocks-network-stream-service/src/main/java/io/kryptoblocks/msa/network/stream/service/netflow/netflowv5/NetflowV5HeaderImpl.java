package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NetflowV5HeaderImpl implements NetflowV5Header{
	
	/** The version. */
	private int version;
    
	/** The count. */
	private int count;
    
	/** The sysUptime. */
	private long sysUptime;
    
	private long unixSecs;
	
	private long unixNsecs;
    
	/** The flow sequence. */
	private long flowSequence;
    
	/** The engine type. */
	private short engineType;
    
	/** The engine type. */
	private short engineId;
	
	private int samplingMode;
    
	/** The sampling interval. */
	private int samplingInterval;    


	@Override
	public int version() {
		return version;
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public long sysUptime() {
		return sysUptime;
	}

	@Override
	public long unixSecs() {
		return unixSecs;
	}

	@Override
	public long unixNsecs() {
		return unixNsecs;
	}

	@Override
	public long flowSequence() {
		return flowSequence;
	}

	@Override
	public short engineType() {
		return engineType;
	}

	@Override
	public short engineId() {
		return engineId;
	}

	@Override
	public int samplingMode() {
		return samplingMode;
	}

	@Override
	public int samplingInterval() {
		return samplingInterval;
	}

	/**
	 * @param version
	 * @param count
	 * @param sysUptime
	 * @param unixSecs
	 * @param unixNsecs
	 * @param flowSequence
	 * @param engineType
	 * @param engineID
	 * @param samplingMode
	 * @param samplingInterval
	 */
	public NetflowV5HeaderImpl(int version, int count, long sysUptime, long unixSecs, long unixNsecs, long flowSequence,
			short engineType, short engineId, int samplingMode, int samplingInterval) {
		super();
		this.version = version;
		this.count = count;
		this.sysUptime = sysUptime;
		this.unixSecs = unixSecs;
		this.unixNsecs = unixNsecs;
		this.flowSequence = flowSequence;
		this.engineType = engineType;
		this.engineId = engineId;
		this.samplingMode = samplingMode;
		this.samplingInterval = samplingInterval;
	}
	
	//dummy constructor
	public NetflowV5HeaderImpl() {
		
	}
	
	@Override
	public String toString() {
		return "NetflowV5HeaderImpl [version=" + version + ", count=" + count + ", sysUptime=" + sysUptime
				+ ", unixSecs=" + unixSecs + ", unixNsecs=" + unixNsecs + ", flowSequence=" + flowSequence
				+ ", engineType=" + engineType + ", engineId=" + engineId + ", samplingMode=" + samplingMode
				+ ", samplingInterval=" + samplingInterval + "]";
	}
	
	

}
