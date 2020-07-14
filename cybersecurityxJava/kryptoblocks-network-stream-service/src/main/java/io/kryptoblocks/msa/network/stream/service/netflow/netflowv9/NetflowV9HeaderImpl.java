package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public final class NetflowV9HeaderImpl implements NetFlowV9Header{
	
	/** The version. */
	private final int version;

	/** The count. */
	private final int count;

	/** The uptime. */
	private final long sysUptime;

	/** The timestamp. */
	private final long unixSecs;

	/** The flow sequence. */
	private final long sequence;

	/** The source ID. */
	private final long sourceId;

	
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
	public long sequence() {
		return sequence;
	}

	@Override
	public long sourceId() {
		return sourceId;
	}

	/**
	 * @param version
	 * @param count
	 * @param sysUptime
	 * @param unixSecs
	 * @param sequence
	 * @param sourceId
	 */
	public NetflowV9HeaderImpl(int version, int count, long sysUptime, long unixSecs, long sequence, long sourceId) {
		super();
		this.version = version;
		this.count = count;
		this.sysUptime = sysUptime;
		this.unixSecs = unixSecs;
		this.sequence = sequence;
		this.sourceId = sourceId;
	}
	
	public String prettyHexDump() {
        final ByteBuf buffer = Unpooled.buffer(20);
        try {
            buffer.writeShort(version());
            buffer.writeShort(count());
            buffer.writeInt(Math.toIntExact(sysUptime()));
            buffer.writeInt(Math.toIntExact(unixSecs()));
            buffer.writeInt(Math.toIntExact(sequence()));
            buffer.writeInt(Math.toIntExact(sourceId()));

            return ByteBufUtil.prettyHexDump(buffer);
        } finally {
            ReferenceCountUtil.release(buffer);
        }
	}
	
		
}
