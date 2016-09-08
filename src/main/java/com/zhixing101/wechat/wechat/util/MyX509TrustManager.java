package com.zhixing101.wechat.wechat.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * MyX509TrustManager
 * 
 */
public class MyX509TrustManager implements X509TrustManager {

    // 检查客户端证书
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        // TODO Auto-generated method stub

    }

    // 检查服务器端证书
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        // TODO Auto-generated method stub

    }

    // 返回受信任的X509证书数组
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }

}
