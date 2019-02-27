package tech.phosphorus.sstp;

import tech.phosphorus.sstp.packets.DataPacket;
import tech.phosphorus.sstp.packets.PacketParser;
import tech.phosphorus.sstp.packets.SSTPPacket;

import javax.net.ssl.SSLSocket;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecurePeer {

    private SSLSocket socket;

    public SecurePeer(SSLSocket socket) {
        this.socket = socket;
    }

    public void sendPacket(SSTPPacket packet) throws IOException {
        socket.getOutputStream().write(packet.serialize());
        System.out.println("Packet Sent :\n" + packet);
    }

    public SSTPPacket readPacket() throws IOException {
        SSTPPacket packet = PacketParser.parsePacket(socket.getInputStream());
        System.out.println("Packet Received :\n" + packet);
        return packet;
    }

}
