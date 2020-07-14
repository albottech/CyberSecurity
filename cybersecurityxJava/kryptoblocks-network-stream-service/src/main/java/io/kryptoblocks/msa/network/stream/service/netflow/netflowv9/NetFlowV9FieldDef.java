package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.net.InetAddress;
import java.util.Optional;
import io.netty.buffer.ByteBuf;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.net.UnknownHostException;
import static com.google.common.base.Strings.emptyToNull;

public abstract class NetFlowV9FieldDef {
	
		public abstract NetFlowV9FieldType type();

	    public abstract int length();

	    public Optional<Object> parse(ByteBuf bb) {
	        int len = length() != 0 ? length() : type().valueType().getDefaultLength();
	        switch (type().valueType()) {
	            case UINT8:
	            case UINT16:
	            case UINT24:
	            case UINT32:
	            case UINT64:
	                return parseUnsignedNumber(bb, len);
	            case INT8:
	                return Optional.of(bb.readByte());
	            case INT16:
	                return Optional.of(bb.readShort());
	            case INT24:
	                return Optional.of(bb.readMedium());
	            case INT32:
	                return Optional.of(bb.readInt());
	            case INT64:
	                return Optional.of(bb.readLong());
	            case IPV4:
	                byte[] b = new byte[4];
	                bb.readBytes(b);
	                try {
	                    return Optional.of(InetAddress.getByAddress(b).getHostAddress());
	                } catch (UnknownHostException e) {
	                    return Optional.empty();
	                }
	            case IPV6:
	                byte[] b2 = new byte[16];
	                bb.readBytes(b2);
	                try {
	                    return Optional.of(InetAddress.getByAddress(b2).getHostAddress());
	                } catch (UnknownHostException e) {
	                    return Optional.empty();
	                }
	            case MAC:
	                byte[] b3 = new byte[6];
	                bb.readBytes(b3);
	                return Optional.of(String.format(Locale.ROOT, "%02x:%02x:%02x:%02x:%02x:%02x", b3[0], b3[1], b3[2], b3[3], b3[4], b3[5]));
	            case STRING:
	                byte[] b4 = new byte[len];
	                bb.readBytes(b4);
	                return Optional.ofNullable(emptyToNull(new String(b4, StandardCharsets.UTF_8).trim()));
	            case SKIP:
	                bb.skipBytes(len);
	                return Optional.empty();
	            default:
	                return Optional.empty();
	        }
	    }

	    private Optional<Object> parseUnsignedNumber(ByteBuf bb, int length) {
	        switch (length) {
	            case 1:
	                return Optional.of(bb.readUnsignedByte());
	            case 2:
	                return Optional.of(bb.readUnsignedShort());
	            case 3:
	                return Optional.of(bb.readUnsignedMedium());
	            case 4:
	                return Optional.of(bb.readUnsignedInt());
	            case 8:
	                return Optional.of(bb.readLong());
	            default:
	                byte[] uint64Bytes = new byte[length];
	                bb.readBytes(uint64Bytes);
	                return Optional.of(new BigInteger(uint64Bytes));
	        }

	    }
}
