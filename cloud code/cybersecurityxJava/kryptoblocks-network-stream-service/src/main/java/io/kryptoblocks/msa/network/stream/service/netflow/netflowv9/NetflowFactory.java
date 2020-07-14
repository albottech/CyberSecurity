package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.net.InetSocketAddress;
import java.util.List;

import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXDecoder.IPFIXMessagev10;
import io.kryptoblocks.msa.network.stream.service.netflow.ipfix.IPFIXHeader;
import io.kryptoblocks.msa.network.stream.service.netflow.netflowv9.NetFlowV9Decoder.NetFlowMessagev9;

public interface NetflowFactory {
	/**
	 * Netflow message v9.
	 *
	 * @param version      the version
	 * @param count        the count
	 * @param uptime       the uptime
	 * @param timestamp    the timestamp
	 * @param flowSequence the flow sequence
	 * @param sourceID     the source ID
	 * @param sender       the sender
	 * @param recipient    the recipient
	 * @param flowsets     the flowsets
	 * @return the net flow message
	 */
	NetFlowMessagev9 netflowMessagev9(NetFlowV9Header header, List<FlowSet> flowsets, DataFlowSet dataFlowSet);
	
	/**
	 * Netflow message v10.
	 *
	 * @param version      the version
	 * @param count        the count
	 * @param uptime       the exportTime
	 * @param flowSequence the flow sequence
	 * @param sourceID     the source ID
	 * @param sender       the sender
	 * @param recipient    the recipient
	 * @param flowsets     the flowsets
	 * @return the net flow message
	 */
	IPFIXMessagev10 iPFIXMessagev10( IPFIXHeader header, List<FlowSet> flowsets, DataFlowSet dataFlowSet);
	/**
	 * Template field.
	 *
	 * @param type   the type
	 * @param length the length
	 * @return the template field
	 */
	TemplateField templateField(short type, short length);

	/**
	 * Template flow set.
	 *
	 * @param flowsetID  the flowset ID
	 * @param templateID the template ID
	 * @param fields     the fields
	 * @return the template flow set
	 */
	TemplateFlowSet templateFlowSet(short flowsetID, short templateID, List<TemplateField> fields);

	/**
	 * Data flow set.
	 *
	 * @param flowsetID the flowset ID
	 * @param data      the data
	 * @return the data flow set
	 */
	DataFlowSet dataFlowSet(short flowsetID, byte[] data);
}
