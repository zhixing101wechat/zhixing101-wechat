package com.zhixing101.wechat.wechat.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhixing101.wechat.wechat.common.Constants;
import com.zhixing101.wechat.wechat.token.TokenCache;
import com.zhixing101.wechat.wechat.util.MyX509TrustManager;

/**
 * 定时同步微信公众号的token Created by adam on 1/9/16.
 */
public class WechatTask {

    private static final Logger logger = LoggerFactory.getLogger(WechatTask.class);

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    TokenCache tokenCache;

    @Value("#{configProperties['weixin.appId']}")
    private String appId;

    @Value("#{configProperties['weixin.appSecret']}")
    private String appSecret;

    @Value("#{configProperties['weixin.accessTokenRequestUrl']}")
    private String accessTokenRequestUrl;

    public void executeTask() {

        // 获取access_token
        GetAccessToken getAccessToken = new GetAccessToken();
        taskExecutor.execute(getAccessToken);

    }

    class GetAccessToken implements Runnable {

        public void run() {

            // 修改appId,appSecret
            String requestUrl = new String(accessTokenRequestUrl);
            requestUrl = requestUrl.replaceFirst("APPID", appId);
            requestUrl = requestUrl.replaceFirst("APPSECRET", appSecret);

            try {
                // 建立连接
                URL url = new URL(requestUrl);
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

                // 输出返回结果
                JSONObject obj = JSON.parseObject(buffer.toString());
                String accessToken = obj.getString("access_token");
                tokenCache.setAccess_token(accessToken);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
