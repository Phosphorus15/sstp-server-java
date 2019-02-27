package tech.phosphorus.sstp.packets;

public class SSTPAttribute {

    SSTPAttribute() {

    }

    byte reserved;

    AttributeType type;

    byte reserved2;

    int length;

    byte data[];

    public enum AttributeType {
        SSTP_ATTRIB_ENCAPSULATED_PROTOCOL_ID(0x01),
        SSTP_ATTRIB_STATUS_INFO(0x02),
        SSTP_ATTRIB_CRYPTO_BINDING(0x03),
        SSTP_ATTRIB_CRYPTO_BINDING_REQ(0x04);

        int id;

        AttributeType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static AttributeType getType(int id) {
            return AttributeType.values()[id - 1];
        }

    }

    @Override
    public String toString() {
        return type + " - length : " + length;
    }
}
