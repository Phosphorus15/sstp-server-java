package tech.phosphorus.ppp.linux;

import tech.phosphorus.sstp.ppp.PPPProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NativePPP implements PPPProvider {

    private final Process process;

    public NativePPP(String args) throws IOException {
        process = Runtime.getRuntime().exec("pppd " + args);
    }

    public PPPProvider getProvider() {
        return this;
    }

    @Override
    public int write(byte[] buf, int off) throws IOException {
        process.getOutputStream().write(buf, off, buf.length);
        return 0;
    }

    @Override
    public int receive(byte[] buf, int off) throws IOException {
        return process.getInputStream().read(buf, off, buf.length);
    }

    public BufferedReader getErrorStream() {
        return new BufferedReader(new InputStreamReader(process.getErrorStream()));
    }

}
