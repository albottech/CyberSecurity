package io.kryptoblocks.msa.network.stream.service.netflow.netflowv5;

import java.net.InetAddress;

public interface NetFlowV5Record {
	 // bytes 0-3
    public abstract InetAddress srcAddr();

    // bytes 4-7
    public abstract InetAddress dstAddr();

    // bytes 8-11
    public abstract InetAddress nextHop();

    // bytes 12-13, snmp index of input interface
    public abstract int inputIface();

    // bytes 14-15, snmp index of output interface
    public abstract int outputIface();

    // bytes 16-19, packets in flow
    public abstract long packetCount();

    // bytes 20-23, total number of L3 bytes in the packets of the flow
    public abstract long octetCount();

    // bytes 24-27, sysuptime at start of flow
    public abstract long first();

    // bytes 28-31, sysuptime at the time the last packet of the flow was
    // received
    public abstract long last();

    // bytes 32-33
    public abstract int srcPort();

    // bytes 34-35
    public abstract int dstPort();

    // bytes 37
    public abstract short tcpFlags();

    // bytes 38, ip protocol type (e.g. tcp = 6, udp = 17)
    public abstract short protocol();

    // bytes 39, type of service
    public abstract short tos();

    // bytes 40-41, source AS number
    public abstract int srcAs();

    // bytes 42-43, destination AS number
    public abstract int dstAs();

    // bytes 44
    public abstract short srcMask();

    // bytes 45
    public abstract short dstMask();
}
