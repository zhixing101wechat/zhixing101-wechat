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

import com.zhixing101.wechat.wechat.util.MyX509TrustManager;
import com.zhixing101.wechat.wechat.util.WechatUtils;

/**
 * 定时同步微信公众号的token Created by adam on 1/9/16.
 */
public class WechatTask {

    private static final Logger logger = LoggerFactory.getLogger(WechatTask.class);

    // @Autowired
    // WechatService wechatService;

    @Autowired
    WechatUtils wechatUtils;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Value("#{configProperties['weixin.appId']}")
    private String appId;

    @Value("#{configProperties['weixin.appSecret']}")
    private String appSecret;

    public void executeTask() throws Exception {
        // 定时同步微信公众号的token

        // 获取accessToken
        /**
         * 1.先获取本地库中的accesstoken 2.判断accesstoken是否失效
         * 3.如果accesstoken距离失效时间少于15分钟则进行accesstoken重新获取
         * 4.将最新的accesstoken保存到数据库中 注意:后期将修改为本地缓存文件
         */
        // CheckAccessToken checkAccessToken = new CheckAccessToken();
        // taskExecutor.execute(checkAccessToken);
        getAccessToken();

    }

    class CheckAccessToken implements Runnable {

        public void run() {
            // 获取本地数据库中的accesstoken
            // List<Wechat> wechatList = wechatService.queryWechatList();
            // for (Wechat wechat : wechatList){
            // //判断当前accesstoken是否超时
            // try {
            // int between = 0;
            // if (!EmptyUtil.isEmpty(wechat.getOverDate())){
            // between =
            // DateUtil.checkDateBetween(DateUtil.dateToString(wechat.getOverDate()));
            // logger.debug("输出between"+between);
            // }
            // logger.debug("当前时间与微信{}的accesstoken失效时间差为{}",new
            // Object[]{wechat.getCname(),between+""});
            // if (between < 15) {
            // logger.debug("当前微信需要重新获取accesstoken");
            // TokenVO tokenVO = wechatUtils.getAccessToken(wechat);
            // logger.debug("tokenVO========="+tokenVO.getAccess_token());
            // wechat.setToken(tokenVO.getAccess_token());
            // String jstoken =
            // wechatUtils.getJSToken(tokenVO.getAccess_token());
            // logger.debug("jstoken====现在的==="+jstoken);
            // wechat.setJstoken(jstoken);
            // //获取当前时间2小时后的时间
            // Timestamp expires =
            // DateUtil.getDateByExpires(tokenVO.getExpires_in());
            // wechat.setOverDate(expires);
            // logger.debug("更新jstoken");
            // wechatService.updateWechat(wechat);
            // }
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
            // }
        }
    }

    private void getAccessToken() throws Exception {

        // 修改appId,appSecret
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        tokenUrl.replaceFirst("APPID", appId);
        tokenUrl.replaceFirst("APPSECRET", appSecret);

        // 建立连接
        URL url = new URL(tokenUrl);
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
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
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
//        System.out.println(buffer);
        logger.info("get access_token : " + buffer.toString());
    }
}
