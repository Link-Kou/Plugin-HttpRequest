package com.plugin.httprequest.ssl;

import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author lk
 * @version 1.0
 * @date 2018/12/7 17:49
 */
public class UserSSL {

    public static void UserSSL(OkHttpClient.Builder httpBuilder,String password,String pathname) {
        // 实例化密钥库 & 初始化密钥工厂
        try {
            char[] passwordchar = password.toCharArray();
            File file = new File(pathname);
            InputStream certStream = new FileInputStream(file);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, passwordchar);
            KeyManagerFactory kmf  = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, passwordchar);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            httpBuilder.sslSocketFactory(sslContext.getSocketFactory(), new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            });
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException | CertificateException | IOException e) {
            e.printStackTrace();
        }

    }
}
