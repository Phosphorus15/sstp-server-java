package tech.phosphorus.ppp;

public enum PPPProtocolCode {

    LINK_CONTROL_PROTOCOL(0xc0, 0x21),
    PASSWORD_AUTHENTICATION_PROTOCOL(0xc0, 0x23);

    byte high;

    byte low;

    PPPProtocolCode(int high, int low) {
        this.high = (byte) high;
        this.low = (byte) low;
    }
}
