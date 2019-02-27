package tech.phosphorus.sstp.packets;

/**
 * Reference : https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-sstp/a961ef2b-daeb-4d1e-bf28-45f10c8ba565
 */
public enum ControlConstants {
    SSTP_MSG_CALL_CONNECT_REQUEST(0x0001),

    SSTP_MSG_CALL_CONNECT_ACK(0x0002),

    SSTP_MSG_CALL_CONNECT_NAK(0x0003),

    SSTP_MSG_CALL_CONNECTED(0x0004),

    SSTP_MSG_CALL_ABORT(0x0005),

    SSTP_MSG_CALL_DISCONNECT(0x0006),

    SSTP_MSG_CALL_DISCONNECT_ACK(0x0007),

    SSTP_MSG_ECHO_REQUEST(0x0008),

    SSTP_MSG_ECHO_RESPONSE(0x0009);

    int id;

    ControlConstants(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ControlConstants getType(int id) {
        return ControlConstants.values()[id - 1];
    }
}
