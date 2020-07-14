package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

public abstract class NetFlowV9FieldType {
	
    public abstract int id();

    public abstract ValueType valueType();

    public abstract String name();

    public enum ValueType {
        UINT8(1), INT8(1), UINT16(2), INT16(2), UINT24(3), INT24(3),
        UINT32(4), INT32(4), UINT64(8), INT64(8), IPV4(4), IPV6(16),
        MAC(6), STRING(0), SKIP(0);

        private final int defaultLength;

        ValueType(int defaultLength) {
            this.defaultLength = defaultLength;
        }

        public int getDefaultLength() {
            return defaultLength;
        }

        public static ValueType byLength(int length) {
            switch (length) {
                case 1: return UINT8;
                case 2: return UINT16;
                case 3: return UINT24;
                case 4: return UINT32;
                case 6: return MAC;
                case 8: return UINT64;
                case 16: return IPV6;
                default: return SKIP;
            }
        }
    }
}
