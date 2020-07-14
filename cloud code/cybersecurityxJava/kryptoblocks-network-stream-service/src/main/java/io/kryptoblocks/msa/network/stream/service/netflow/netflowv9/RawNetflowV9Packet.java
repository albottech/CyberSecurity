package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.Map;
import java.util.Set;

import com.datastax.shaded.netty.buffer.ByteBufUtil;
import com.datastax.shaded.netty.buffer.Unpooled;

import edu.umd.cs.findbugs.annotations.Nullable;

public abstract class RawNetflowV9Packet {
	
		public abstract NetFlowV9Header header();

	    public abstract int dataLength();

	    public abstract Map<Integer, byte[]> templates();

	    @Nullable
	    public abstract Map.Entry<Integer, byte[]> optionTemplate();

	    public abstract Set<Integer> usedTemplates();

	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder("\n");
	        sb.append(header().prettyHexDump()).append("\n");
	        sb.append("\nTemplates:\n");
	        templates().forEach((integer, byteBuf) -> {
	            sb.append("\n").append(integer).append(":\n").append(ByteBufUtil.prettyHexDump(Unpooled.wrappedBuffer(byteBuf)));
	        });
	        final Map.Entry<Integer, byte[]> optionTemplate = optionTemplate();
	        if (optionTemplate != null) {
	            sb.append("\nOption Template:\n").append(ByteBufUtil.prettyHexDump(Unpooled.wrappedBuffer(optionTemplate.getValue())));
	        }
	        sb.append("\nData flows using these templates:\n");
	        usedTemplates().forEach(templateId -> sb.append(templateId).append(" "));
	        return sb.toString();
	    }
}
