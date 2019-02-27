package tech.phosphorus.sstp.crypto;

import tech.phosphorus.sstp.packets.ControlConstants;
import tech.phosphorus.sstp.packets.ControlPacket;
import tech.phosphorus.sstp.packets.PacketConstructor;
import tech.phosphorus.sstp.packets.SSTPAttribute;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class CryptoBinding {

    public static final byte CERT_HASH_PROTOCOL_SHA1 = 0x1;

    public static final byte CERT_HASH_PROTOCOL_SHA256 = 0x2;

    private static SecureRandom random = new SecureRandom();

    public static ControlPacket getAckPacket() {
        byte[] data = new byte[0x24];
        random.nextBytes(data);
        data[0] = data[1] = data[2] = 0;
        data[3] = CERT_HASH_PROTOCOL_SHA256;
        SSTPAttribute nonce = PacketConstructor.createAttribute(data, SSTPAttribute.AttributeType.SSTP_ATTRIB_CRYPTO_BINDING_REQ);
        return PacketConstructor.createControlPacket(ControlConstants.SSTP_MSG_CALL_CONNECT_ACK, nonce);
    }

}
