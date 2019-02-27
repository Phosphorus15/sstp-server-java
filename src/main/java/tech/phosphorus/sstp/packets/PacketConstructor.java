package tech.phosphorus.sstp.packets;

import java.util.Arrays;

public class PacketConstructor {

    public static DataPacket createDataPacket(byte[] data) {
        DataPacket packet = new DataPacket();
        packet.version = DataPacket.VERSION;
        packet.data = data;
        packet.length = data.length;
        packet.reservedField = 0;
        packet.reservedField2 = 0;
        return packet;
    }

    public static SSTPAttribute createAttribute(byte[] data, SSTPAttribute.AttributeType type) {
        SSTPAttribute attribute = new SSTPAttribute();
        attribute.data = data;
        attribute.length = data.length;
        attribute.reserved2 = 0;
        attribute.reserved = 0;
        attribute.type = type;
        return attribute;
    }

    public static ControlPacket createControlPacket(ControlConstants type, SSTPAttribute... attributes) {
        ControlPacket packet = new ControlPacket();
        packet.version = DataPacket.VERSION;
        packet.length = packet.getLength();
        packet.reservedField = 0;
        packet.reservedField2 = 0;
        packet.attributesNumber = attributes.length;
        packet.attributes.addAll(Arrays.asList(attributes));
        packet.type = type;
        return packet;
    }

}
