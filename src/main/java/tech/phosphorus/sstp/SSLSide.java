package tech.phosphorus.sstp;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class SSLSide {

	public static KeyManager[] keys() {
		try {
			final KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new FileInputStream("/etc/keystore"), "lance2000".toCharArray());
			Enumeration e = keyStore.aliases();
			while(e.hasMoreElements()) System.out.println(e.nextElement());
			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
					.getDefaultAlgorithm());
			kmf.init(keyStore, "lance2000".toCharArray());
			return kmf.getKeyManagers();
		} catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | IOException | CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TrustManager[] ssl() {
		return new TrustManager[]{
				new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

					public X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}
				}
		};
	}

	public static void main(String[] args) throws Throwable {
		SSLServerSocketFactory sslServersocketFactory =
				(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		SSLServerSocket sslServerSocket =
				(SSLServerSocket) sslServersocketFactory.createServerSocket(1024);
		SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
		sslSocket.setEnabledCipherSuites(new String[] { "TLS_DH_anon_WITH_AES_128_CBC_SHA" });
		sslSocket.getInputStream().read();
	}

}
