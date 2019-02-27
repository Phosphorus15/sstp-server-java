package tech.phosphorus.sstp.ppp;

public interface PPPProvider {

    int write(byte[] buf, int off);

    int receive(byte[] buf, int off);

}
