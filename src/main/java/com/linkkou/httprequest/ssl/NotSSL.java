package com.linkkou.httprequest.ssl;

import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * 请求取消https安全校验
 * @author lk
 * @version 1.0
 * @date 2018/12/7 17:43
 */
public class NotSSL {

    public static void SSL(OkHttpClient sClient){
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            //SSL证书忽略信任
            sc.init(null, new TrustManager[]{new X509TrustManager() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostnameVerifier hv1 = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        String workerClassName="okhttp3.OkHttpClient";
        try {
            Class workerClass = Class.forName(workerClassName);

            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(sClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            sslSocketFactory.set(sClient, sc != null ? sc.getSocketFactory() : null);

            Field writeTimeout = workerClass.getDeclaredField("writeTimeout");
            writeTimeout.setAccessible(true);
            //毫秒
            writeTimeout.set(sClient, (int) TimeUnit.MILLISECONDS.toMillis(15560));

            Field readTimeout = workerClass.getDeclaredField("readTimeout");
            readTimeout.setAccessible(true);
            readTimeout.set(sClient, (int)TimeUnit.MILLISECONDS.toMillis(15560));

            Field connectTimeout = workerClass.getDeclaredField("connectTimeout");
            connectTimeout.setAccessible(true);
            connectTimeout.set(sClient, (int)TimeUnit.MILLISECONDS.toMillis(15560));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
