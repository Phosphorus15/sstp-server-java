package tech.phosphorus.sstp.packets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PacketParser {

    public static SSTPPacket parsePacket(InputStream input) throws IOException {
        DataInputStream data = new DataInputStream(input);
        int version = data.read();
        int type = data.read();
        boolean control = ((type & 1) == 1);
        if (control) return parseControlPacket(data, version, type);
        else return parseDataPacket(data, version, type);
    }

    public static DataPacket parseDataPacket(DataInputStream data, int version, int type) throws IOException {
        int[] length = splitQuater(data.readByte(), data.readByte());
        DataPacket packet = new DataPacket();
        packet.reservedField = (byte) (type ^ 1);
        packet.reservedField2 = (byte) length[0];
        packet.length = length[1] - 4;
        packet.data = new byte[packet.length];
        data.readFully(packet.data);
        return packet;
    }

    public static ControlPacket parseControlPacket(DataInputStream data, int version, int type) throws IOException {
        int[] length = splitQuater(data.readByte(), data.readByte());
        int id = data.readShort();
        int attributes = data.readShort();
        ControlPacket packet = new ControlPacket();
        packet.attributesNumber = attributes;
        packet.reservedField = (byte) (type ^ 1);
        packet.reservedField2 = (byte) length[0];
        packet.type = ControlConstants.getType(id);
        packet.length = length[1] - 4;
        for (int i = 0; i < attributes; i++) {
            int preserved = data.readByte();
            int typeId = data.readByte();
            int[] attrLength = splitQuater(data.readByte(), data.readByte());
            SSTPAttribute attribute = new SSTPAttribute();
            attribute.reserved = (byte) preserved;
            attribute.type = SSTPAttribute.AttributeType.getType(typeId);
            attribute.reserved2 = (byte) attrLength[0];
            attribute.length = attrLength[1] - 4;
            attribute.data = new byte[attribute.length];
            data.readFully(attribute.data);
            packet.attributes.add(attribute);
        }
        return packet;
    }

    public static int[] splitQuater(byte b1, byte b2) {
        int prior = b1 >> 4;
        b1 = (byte) (b1 & 0xF); // quater
        return new int[]{prior, b1 << 4 | b2};
    }

    public static byte[] encapsule(int length) {
        return new byte[] {(byte) ((byte) (length >> 8) & 0xF), (byte) (length & 0xFF)};
    }


}
