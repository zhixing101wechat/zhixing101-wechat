package com.zhixing101.wechat.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.zhixing101.wechat.wechat.common.Constants;

/**
 * SHA1加密Util
 *
 */
public class SHA1EncryptionUtil {

    /**
     * SHA1加密
     * 
     * @param originStr 加密前字符串
     * @return 加密后字符串
     */
    public static String SHA1(String originStr) {

        try {
            MessageDigest digest = MessageDigest.getInstance(Constants.STR_ENCRYPT_SHA_1);
            digest.update(originStr.getBytes());
            byte messageDigest[] = digest.digest();
            // 创建十六进制字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Constants.STR_EMPTY;
    }
}
