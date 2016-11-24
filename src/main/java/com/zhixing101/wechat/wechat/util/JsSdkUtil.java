package com.zhixing101.wechat.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.qq.weixin.mp.api.res.GetWebAccessTokenResponse;
import com.zhixing101.wechat.wechat.common.Constants;

/**
 * JS-SDKUtil
 *
 */
@Component
public class JsSdkUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsSdkUtil.class);

    private static String webAccessTokenRequestUrl;

    @Value("#{configProperties['weixin.webAccessTokenRequestUrl']}")
    public void setWebAccessTokenRequestUrl(String url) {
        webAccessTokenRequestUrl = url;
    }

    public static String getJsSdkSignature(String noncestr, String jsapi_ticket, String timestamp, String url) {

        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
                + url;
        String signature = "";
        MessageDigest crypt;
        try {
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }

        return signature;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 通过code换取网页授权access_token
     * 
     */
    public static GetWebAccessTokenResponse getWebAccessToken(String appid, String secret, String code) {

        logger.debug("JsSdkUtil#GetWebAccessTokenResponse begin");

        // 修改APPID和SECRET，构造获取access_token的链接
        String reqUrl = new String(webAccessTokenRequestUrl);
        reqUrl = reqUrl.replaceFirst("APPID", appid);
        reqUrl = reqUrl.replaceFirst("SECRET", secret);

        try {
            // 建立连接
            URL url = new URL(reqUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");

            // 取得输入流
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Constants.STR_ENCODING_UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 读取响应内容
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();

            logger.debug("JsSdkUtil#GetWebAccessTokenResponse result " + buffer.toString());

            // 返回返回结果
            GetWebAccessTokenResponse res = JSON.parseObject(buffer.toString(), GetWebAccessTokenResponse.class);

            logger.debug("JsSdkUtil#GetWebAccessTokenResponse end");
            return res;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("JsSdkUtil#GetWebAccessTokenResponse return null");
        return null;
    }
}
