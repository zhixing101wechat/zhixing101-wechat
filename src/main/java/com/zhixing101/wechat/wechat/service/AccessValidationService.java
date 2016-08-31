package com.zhixing101.wechat.wechat.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zhixing101.wechat.wechat.common.Constants;
import com.zhixing101.wechat.wechat.util.SHA1EncryptionUtil;

/**
 * 接入验证Service
 * 
 */
@Service
public class AccessValidationService {

    @Value("#{configProperties['weixin.token']}")
    private String token;

    /**
     * 接入验证
     * 
     * @param signature
     *            微信加密签名
     * @param timestamp
     *            时间戳
     * @param nonce
     *            随机数
     * @param echostr
     *            随机字符串
     * @return 接入验证结果
     */
    public String doAccessValidation(String signature, String timestamp, String nonce, String echostr) {

        // 将token、timestamp、nonce三个参数进行字典序排序
        String dicrSortStr = doDictSort(timestamp, nonce);

        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String encryptedStr = SHA1EncryptionUtil.SHA1(dicrSortStr);

        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(encryptedStr)) {
            return echostr;
        } else {
            return Constants.STR_FAILED;
        }
    }

    /**
     * 字典序排序
     * 
     * @param token
     * @param timestamp
     * @param nonce
     * @return 字典序排序结果
     */
    public String doDictSort(String timestamp, String nonce) {

        // 构建参数数组
        String[] paras = { token, timestamp, nonce };
        // 对参数数组进行字典排序
        Arrays.sort(paras);
        // 把三个参数字符串拼接成一个字符串
        StringBuilder sb = new StringBuilder();
        for (String str : paras) {
            sb.append(str);
        }
        return sb.toString();
    }
}
