package tech.phosphorus.sstp.packets;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ControlPacketTest {

    @Test
    public void serialize() { // Packet Construction Test
        ControlPacket packet = new ControlPacket();
        packet.attributesNumber = 1;
        packet.type = ControlConstants.SSTP_MSG_CALL_CONNECT_ACK;
        SSTPAttribute attribute = new SSTPAttribute();
        attribute.type = SSTPAttribute.AttributeType.SSTP_ATTRIB_CRYPTO_BINDING_REQ;
        attribute.data = "test".getBytes();
        packet.attributes.add(attribute);
        Assert.assertArrayEquals(packet.serialize(), new byte[]{0x10, 0x01, 0x00, 0x0C, 0x00, 0x02, 0x00, 0x01, 0x00, 0x04, 0x00, 0x08, 0x74, 0x65, 0x73, 0x74});
    }

    @Test
    public void construct() {
        ControlPacket packet = PacketConstructor.createControlPacket(ControlConstants.SSTP_MSG_CALL_CONNECT_ACK
                , PacketConstructor.createAttribute("test".getBytes(), SSTPAttribute.AttributeType.SSTP_ATTRIB_CRYPTO_BINDING_REQ));
        Assert.assertArrayEquals(packet.serialize(), new byte[]{0x10, 0x01, 0x00, 0x0C, 0x00, 0x02, 0x00, 0x01, 0x00, 0x04, 0x00, 0x08, 0x74, 0x65, 0x73, 0x74});
    }
}
