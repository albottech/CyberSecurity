package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

import java.net.InetAddress;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NetFlowV5RecordImpl implements NetFlowV5Record{
	
		private InetAddress srcAddr;

		private InetAddress dstAddr;

		private InetAddress nextHop;

		private int inputIface;

		private int outputIface;

		private long packetCount;

		private long octetCount;

		private long first;

		private long last;

		private int srcPort;

		private int dstPort;

		private short tcpFlags;

		private short protocol;

		private short tos;

		private int srcAs;

		private int dstAs;

		private short srcMask;

		private short dstMask;

	
	@Override
	public InetAddress srcAddr() {
		// TODO Auto-generated method stub
		return srcAddr;
	}

	@Override
	public InetAddress dstAddr() {
		// TODO Auto-generated method stub
		return dstAddr;
	}

	@Override
	public InetAddress nextHop() {
		// TODO Auto-generated method stub
		return nextHop;
	}

	@Override
	public int inputIface() {
		// TODO Auto-generated method stub
		return inputIface;
	}

	@Override
	public int outputIface() {
		// TODO Auto-generated method stub
		return outputIface;
	}

	@Override
	public long packetCount() {
		// TODO Auto-generated method stub
		return packetCount;
	}

	@Override
	public long octetCount() {
		// TODO Auto-generated method stub
		return octetCount;
	}

	@Override
	public long first() {
		// TODO Auto-generated method stub
		return first;
	}

	@Override
	public long last() {
		// TODO Auto-generated method stub
		return last;
	}

	@Override
	public int srcPort() {
		// TODO Auto-generated method stub
		return srcPort;
	}

	@Override
	public int dstPort() {
		// TODO Auto-generated method stub
		return dstPort;
	}

	@Override
	public short tcpFlags() {
		// TODO Auto-generated method stub
		return tcpFlags;
	}

	@Override
	public short protocol() {
		// TODO Auto-generated method stub
		return protocol;
	}

	@Override
	public short tos() {
		// TODO Auto-generated method stub
		return tos;
	}

	@Override
	public int srcAs() {
		// TODO Auto-generated method stub
		return srcAs;
	}

	@Override
	public int dstAs() {
		// TODO Auto-generated method stub
		return dstAs;
	}

	@Override
	public short srcMask() {
		// TODO Auto-generated method stub
		return srcMask;
	}

	@Override
	public short dstMask() {
		// TODO Auto-generated method stub
		return dstMask;
	}

	/**
	 * @param srcAddr
	 * @param dstAddr
	 * @param nextHop
	 * @param inputIface
	 * @param outputIface
	 * @param packetCount
	 * @param octetCount
	 * @param first
	 * @param last
	 * @param srcPort
	 * @param dstPort
	 * @param tcpFlags
	 * @param protocol
	 * @param tos
	 * @param srcAs
	 * @param dstAs
	 * @param srcMask
	 * @param dstMask
	 */
	public NetFlowV5RecordImpl(InetAddress srcAddr, InetAddress dstAddr, InetAddress nextHop, int inputIface,
			int outputIface, long packetCount, long octetCount, long first, long last, int srcPort, int dstPort,
			short tcpFlags, short protocol, short tos, int srcAs, int dstAs, short srcMask, short dstMask) {
		super();
		this.srcAddr = srcAddr;
		this.dstAddr = dstAddr;
		this.nextHop = nextHop;
		this.inputIface = inputIface;
		this.outputIface = outputIface;
		this.packetCount = packetCount;
		this.octetCount = octetCount;
		this.first = first;
		this.last = last;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
		this.tcpFlags = tcpFlags;
		this.protocol = protocol;
		this.tos = tos;
		this.srcAs = srcAs;
		this.dstAs = dstAs;
		this.srcMask = srcMask;
		this.dstMask = dstMask;
	}
	
	public NetFlowV5RecordImpl() {
		
	}

	@Override
	public String toString() {
		return "NetFlowV5RecordImpl [srcAddr=" + srcAddr + ", dstAddr=" + dstAddr + ", nextHop=" + nextHop
				+ ", inputIface=" + inputIface + ", outputIface=" + outputIface + ", packetCount=" + packetCount
				+ ", octetCount=" + octetCount + ", first=" + first + ", last=" + last + ", srcPort=" + srcPort
				+ ", dstPort=" + dstPort + ", tcpFlags=" + tcpFlags + ", protocol=" + protocol + ", tos=" + tos
				+ ", srcAs=" + srcAs + ", dstAs=" + dstAs + ", srcMask=" + srcMask + ", dstMask=" + dstMask + "]";
	}
	
	

}
