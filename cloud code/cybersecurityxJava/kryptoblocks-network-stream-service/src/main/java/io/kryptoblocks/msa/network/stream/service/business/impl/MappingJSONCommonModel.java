package io.kryptoblocks.msa.network.stream.service.business.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXHeader;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetFlowV5RecordImpl;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv5.NetflowV5HeaderImpl;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Header;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9OptionTemplate;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Record;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Template;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
public class MappingJSONCommonModel {

	/* AI Usecases and attributes needed by them
	 * 

############Anomoly
input
----------------------
Src IP 			Source IP Address   //srcAddr
Src Port		Source Port 		//srcPort
Dest IP			Destination IP Address	//dstAddr
Dest Port		Destination Port		//dstPort
Proto			Protocol (e.g. ICMP, TCP, or UDP) //protocolName
Duration		Duration of the flow		//duration
Bytes			Number of transmitted bytes		//src_bytes should be recieved not transmitted
Packets			Number of transmitted packets	//packetCount  should be recieved not transmitted
Flags			OR concatenation of all TCP Flags  //tcpFlags
Tos				Type of service		//service
Flows			Unidirectional Netflow data	//not needed by vibha

output
---------------------------------
Class			Class label (normal, attacker, victim, suspicious or unknown)  
AttackType		Type of Attack (portScan, dos, bruteForce, |)
AttackID		Unique attack id. All ows which belong to the same attack carry the same attack id.
AttackDescription	Provides additional information about the set attack parameters (e.g. the number of attempted password guesses for SSH-Brute-Force attacks)
#######Cyber Attacks
input
---------------------
event_id	Unique Id of the events //event_id
src_bytes	Number of data bytes from source to destination	//src_bytes
dst_bytes	number of data bytes from destination to source	//not needed
duration	length (number of seconds) of the connection	//duration
flag	Normal or error status of  the connection. Possible status: SF,S0 -3 ,OTH, REJ, RSTO etc //TCP port connection status not needed
service	network service on the destination, e.g., http, telnet, etc. //service
protocol_type	type of the protocol, e.g. tcp, udp, etc.	//protocol_type
land	1 if connection is from/to the same host/port; 0 otherwise // not needed	
wrong_fragment	Sum of bad checksum packets in a connection	//not needed
urgent	Number of urgent packets. Urgent packets are packets with the urgent bit activated//not needed
	
output 
---------------
Class_attack	Which type of connections is each instance having : normal or which attack like syslog, teardrop, warez, warezclient, warezmaster, pod, back, ip- sweep, neptune, nmap, portsweep, satan, smurf  etc
	 */
		private InetAddress srcAddr;
		private int srcPort;
		private InetAddress dstAddr;
		private int dstPort;
		private String protocolName;
		private long duration;
		private long src_bytes;
		private long packetCount;
		private int tcpFlags;
		private int service;
		
		private int version;
	    private String type;
	    
		//Network Data for  Netflow 5
		//Netflow v5 header		
		private int count;
		private long sysUptime;
		private long unixSecs;		
		private long unixNsecs;
		private long flowSequence;
	    private short engineType;
	    private short engineId;
		private int samplingMode;
	    private int samplingInterval;   
		
	    //Netflow v5 Record
	    //private InetAddress srcAddr;
	    //private InetAddress dstAddr;
		private InetAddress nextHop;
		private int inputIface;
		private int outputIface;
		//private long packetCount;
		private long octetCount;
		private long first;
		private long last;
		//private int srcPort;
		//private int dstPort;
		//private short tcpFlags;
		private short protocol;
		//private short tos;
		private int srcAs;
		private int dstAs;
		private short srcMask;
		private short dstMask;
		
		@Override
		public String toString() {
			return "MappingJSONCommonModel [srcAddr=" + srcAddr + ", srcPort=" + srcPort + ", dstAddr=" + dstAddr
					+ ", dstPort=" + dstPort + ", protocolName=" + protocolName + ", duration=" + duration
					+ ", src_bytes=" + src_bytes + ", packetCount=" + packetCount + ", tcpFlags=" + tcpFlags
					+ ", service=" + service + ", version=" + version + ", type=" + type + ", count=" + count
					+ ", sysUptime=" + sysUptime + ", unixSecs=" + unixSecs + ", unixNsecs=" + unixNsecs
					+ ", flowSequence=" + flowSequence + ", engineType=" + engineType + ", engineId=" + engineId
					+ ", samplingMode=" + samplingMode + ", samplingInterval=" + samplingInterval + ", nextHop="
					+ nextHop + ", inputIface=" + inputIface + ", outputIface=" + outputIface + ", octetCount="
					+ octetCount + ", first=" + first + ", last=" + last + ", protocol=" + protocol + ", srcAs=" + srcAs
					+ ", dstAs=" + dstAs + ", srcMask=" + srcMask + ", dstMask=" + dstMask + "]";
		}
		
			
	 
		 
	
}
