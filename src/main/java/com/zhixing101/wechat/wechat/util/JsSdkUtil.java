package com.zhixing101.wechat.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * JS-SDKUtil
 *
 */
public class JsSdkUtil {

//    public static void main(String[] args) {
//
//        String jsapi_ticket = "kgt8ON7yVITDhtdwci0qeZPRzKNUOwbX6lWlJbLh5kgxRhwFbAA1egtFHDyyV3mEV6YmlIQzHACOT69GqMe4Lw";
//        String noncestr = "Wm3WZYTPz0wzccnW";
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String url = "http://www.mikko.tech/wechat/getLoc";
//
//        String para = "jsapi_ticket=" + jsapi_ticket + "&" + "noncestr=" + noncestr + "&" + "timestamp=" + timestamp
//                + "&" + "url=" + url;
//        String signature = SHA1EncryptionUtil.SHA1(para);
//
//        System.out.println("jsapi_ticket=" + jsapi_ticket);
//        System.out.println("noncestr=" + noncestr);
//        System.out.println("timestamp=" + timestamp);
//        System.out.println("url=" + url);
//        System.out.println("signature=" + signature);
//    }

    public static String getJsSdkSignature(String noncestr, String jsapi_ticket, String timestamp, String url) {

        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
                + url;
        String signature = "";
        // String signature = SHA1EncryptionUtil.SHA1(para);
        MessageDigest crypt;
        try {
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
}
