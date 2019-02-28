import tech.phosphorus.ppp.linux.NativePPP
import tech.phosphorus.sstp.SSLSide
import tech.phosphorus.sstp.SecurePeer
import tech.phosphorus.sstp.crypto.CryptoBinding
import tech.phosphorus.sstp.packets.PacketParser
import java.io.DataInputStream
import java.io.PrintWriter
import java.security.SecureRandom
import java.util.*
import javax.net.ssl.*

/**
 * This is a procedure test of SSTP Handshake
 */
fun main(args: Array<String>) {
    val manager = SSLSide.ssl()
    val sc = SSLContext.getInstance("TLSv1.2")
    sc.init(SSLSide.keys(), manager, SecureRandom())
    sc.serverSocketFactory.createServerSocket(2333).apply {
        (this as SSLServerSocket).enabledProtocols = arrayOf("TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3")
        this.enabledCipherSuites = sc.serverSocketFactory.supportedCipherSuites
        accept().apply {
            val scan = Scanner(getInputStream())
            (1..5).forEach { _ ->
                println(scan.nextLine())
            }
            val out = PrintWriter(getOutputStream(), true)
            out.println("HTTP/1.1 200")
            out.println("Content-Length: 18446744073709551615")
            out.println("Server: SSTP4J\n")
            SecurePeer(this as SSLSocket).apply {
                readPacket()
                sendPacket(CryptoBinding.getAckPacket())
                readPacket()
                readPacket()
            }
        }
    }
}
