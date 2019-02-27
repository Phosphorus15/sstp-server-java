package tech.phosphorus.sstp.packets;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ControlPacket extends SSTPPacket {

    ControlConstants type;

    int attributesNumber;

    List<SSTPAttribute> attributes;

    public ControlPacket() {
        attributes = new ArrayList<>();
        type = ControlConstants.SSTP_MSG_CALL_CONNECT_ACK;
        attributesNumber = 0;
        length = 0;
        reservedField = 0;
        reservedField2 = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("SSTP Control Packet (" + type + ")");
        if (attributesNumber > 0) {
            for (SSTPAttribute attribute : attributes) {
                builder.append("\n\t").append(attribute);
            }
        }
        return builder.toString();
    }

    @Override
    public byte[] serialize() {
        int length = getLength();
        byte[] data = new byte[length];
        data[0] = VERSION;
        data[1] = (byte) (reservedField | 1);
        byte[] len = PacketParser.encapsule(length);
        data[2] = (byte) (len[0] | reservedField2 << 4);
        data[3] = len[1];
        data[4] = 0;
        data[5] = (byte) (type.ordinal() + 1);
        data[6] = (byte) (attributesNumber >> 8);
        data[7] = (byte) (attributesNumber & 0xFF);
        int loc = 8;
        for (SSTPAttribute attribute : attributes) {
            data[loc++] = attribute.reserved;
            byte[] attrLen = PacketParser.encapsule(attribute.data.length + 4);
            data[loc++] = (byte) (attribute.type.ordinal() + 1);
            data[loc++] = (byte) (attrLen[0] | attribute.reserved2 << 4);
            data[loc++] = attrLen[1];
            System.arraycopy(attribute.data, 0, data, loc, attribute.data.length);
            loc += attribute.length;
        }
        return data;
    }

    public int getLength() {
        int length = 8;
        for (SSTPAttribute attribute : attributes) {
            length += attribute.data.length + 4;
        }
        return length;
    }
}
