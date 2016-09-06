package com.zhixing101.wechat.wechat.util;

/**
 * JS-SDKUtil
 *
 */
public class JsSdkUtil {

    public static void main(String[] args) {

        String jsapi_ticket = "kgt8ON7yVITDhtdwci0qeZPRzKNUOwbX6lWlJbLh5kgXlO7cP_9E7yyCCJH9J-Ep24iT05Cq6ddJnBJg87WooQ";
        String noncestr = "Wm3WZYTPz0wzccnW";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = "http://www.mikko.tech/wechat/getLoc";

        String para = "jsapi_ticket=" + jsapi_ticket + "&" + "noncestr=" + noncestr + "&" + "timestamp=" + timestamp
                + "&" + "url=" + url;
        String signature = SHA1EncryptionUtil.SHA1(para);

        System.out.println("jsapi_ticket=" + jsapi_ticket);
        System.out.println("noncestr=" + noncestr);
        System.out.println("timestamp=" + timestamp);
        System.out.println("url=" + url);
        System.out.println("signature=" + signature);
    }
}
