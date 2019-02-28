package tech.phosphorus.sstp.ppp;

import java.io.IOException;

public interface PPPProvider {

    int write(byte[] buf, int off) throws IOException;

    int receive(byte[] buf, int off) throws IOException;

}
