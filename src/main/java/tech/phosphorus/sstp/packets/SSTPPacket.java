package tech.phosphorus.sstp.packets;

public abstract class SSTPPacket {

    public static final byte VERSION = 0x10;

    /**
     * First byte of packet
     */
    byte version;

    /**
     * Reserved Field with C-Control
     */
    byte reservedField;

    /**
     * Reserved Field within length info
     */
    byte reservedField2;

    /**
     * Content Length
     */
    int length;

    public abstract byte[] serialize();

}
