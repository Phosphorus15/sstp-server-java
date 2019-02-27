# Sstp Server (sstp4j)

### Description

The (intended) server framework of SSTP written in java.

Aimed to be secure，modularized, customizable and dependable.

This Project has not been completed and contributions are welcomed.

Current Progress:
- [x] SSTP Packet Parsing & Constructing
- [x] SSTP Layer Handshake
- [ ] Crypto Bindings
- [ ] PPP Layer Handshake
- [ ] Individual PPP Implementation (called mini-ppp)
- [ ] Modularized Response System
- [ ] Data transmission

### How to use

Currently, only the SSTP Layer are available. and PPP Handshake could not be completed (which means it is **not** functional)

To establish SSTP Connection, what you need to do is to

```java
SSLSocket socket = ...; // Complete ssl handshake in java
SecurePeer peer = new SecurePeer(socket);
ControlPacket connected = (ControlPacket) peer.readPacket();
if(connected.type == ControlConstants.SSTP_MSG_CALL_CONNECT_REQUEST)
    peer.sendPacket(CryptoBinding.getAckPacket()); // fast construction of ack packet
else
    ...; // report error
```
