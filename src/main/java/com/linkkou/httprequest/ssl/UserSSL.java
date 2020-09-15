package com.linkkou.httprequest.ssl;

import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
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

    public static void SSL(OkHttpClient okHttpClient, String password, String pathname) {
        // 实例化密钥库 & 初始化密钥工厂
        try {
            char[] passwordchar = password.toCharArray();
            File file = new File(pathname);
            InputStream certStream = new FileInputStream(file);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, passwordchar);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, passwordchar);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());

            HostnameVerifier hv1 = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            String workerClassName = "okhttp3.OkHttpClient";

            Class workerClass = Class.forName(workerClassName);

            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(okHttpClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            sslSocketFactory.set(okHttpClient, sslContext != null ? sslContext.getSocketFactory() : null);

            Field writeTimeout = workerClass.getDeclaredField("writeTimeout");
            writeTimeout.setAccessible(true);
            //毫秒
            writeTimeout.set(okHttpClient, (int) TimeUnit.MILLISECONDS.toMillis(15560));

            Field readTimeout = workerClass.getDeclaredField("readTimeout");
            readTimeout.setAccessible(true);
            readTimeout.set(okHttpClient, (int) TimeUnit.MILLISECONDS.toMillis(15560));

            Field connectTimeout = workerClass.getDeclaredField("connectTimeout");
            connectTimeout.setAccessible(true);
            connectTimeout.set(okHttpClient, (int) TimeUnit.MILLISECONDS.toMillis(15560));

        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException | KeyManagementException | CertificateException | IOException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void getFile(String pathname) {
        //同一个包下,还是要包名/文件名
        URL u1 = Thread.currentThread().getContextClassLoader().getResource(pathname);
        try {
            if (u1 != null) {
                final String decode = URLDecoder.decode(u1.getPath(), "UTF-8");
                InputStream in = new BufferedInputStream(new FileInputStream(decode));
            } else {
                throw new IOException("Properties Is Null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
