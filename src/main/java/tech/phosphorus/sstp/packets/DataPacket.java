package tech.phosphorus.sstp.packets;

import java.util.Arrays;

public class DataPacket extends SSTPPacket {

    byte[] data;

    @Override
    public String toString() {
        if (data.length > 30)
            return "SSTP Data Packet - length : " + length;
        else
            return "SSTP Data Packet - length : " + length + "\n" + Arrays.toString(data);
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public byte[] serialize() {
        byte[] data = new byte[4 + this.data.length];
        data[0] = VERSION;
        data[1] = (byte) (reservedField | 0x1);
        byte[] len = PacketParser.encapsule(this.data.length + 4);
        data[2] = (byte) (len[0] | reservedField2 << 4);
        data[3] = len[1];
        System.arraycopy(data, 0, data, 4, data.length);
        return data;
    }

}
