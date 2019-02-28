package tech.phosphorus.sstp.ppp;

import tech.phosphorus.ppp.linux.NativePPP;
import tech.phosphorus.sstp.SSLSide;
import tech.phosphorus.sstp.SecurePeer;
import tech.phosphorus.sstp.crypto.CryptoBinding;
import tech.phosphorus.sstp.packets.DataPacket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class PPPTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        TrustManager[] manager = SSLSide.ssl();
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(SSLSide.keys(), manager, new SecureRandom());
        SSLServerSocket socket = (SSLServerSocket) sc.getServerSocketFactory().createServerSocket(2333);
        socket.setEnabledProtocols(Arrays.asList("TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3").toArray(new String[0]));
        socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
        SSLSocket client = (SSLSocket) socket.accept();
        Scanner scan = new Scanner(client.getInputStream());
        for (int i = 0; i < 5; i++) {
            System.out.println(scan.nextLine());
        }
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        out.println("HTTP/1.1 200");
        out.println("Content-Length: 18446744073709551615");
        out.println("Server: SSTP4J\n");
        SecurePeer peer = new SecurePeer(client);
        peer.readPacket();
        peer.sendPacket(CryptoBinding.getAckPacket());
        NativePPP ppp = new NativePPP("");
        DataPacket packet = (DataPacket) peer.readPacket();
        ppp.write(packet.getData(), 0);
        byte[] rec = new byte[64];
        int l = ppp.receive(rec, 0);
        System.out.println(l);
    }

}

