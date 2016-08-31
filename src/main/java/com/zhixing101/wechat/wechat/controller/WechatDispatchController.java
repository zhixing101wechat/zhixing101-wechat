package com.zhixing101.wechat.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixing101.wechat.wechat.common.Constants;
import com.zhixing101.wechat.wechat.service.AccessValidationService;

/**
 * 微信分发Controller
 * 
 */
@Controller
@RequestMapping("/wechat")
public class WechatDispatchController {

    /**
     * 日志记录器
     * 
     */
    private Logger log = Logger.getLogger(WechatDispatchController.class);

    /**
     * 接入验证Service
     * 
     */
    @Autowired
    private AccessValidationService accessValidationService;

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
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String accessValidate(@RequestParam(Constants.STR_ACCESS_SIGNATURE) String signature,
            @RequestParam(Constants.STR_ACCESS_TIMESTAMP) String timestamp,
            @RequestParam(Constants.STR_ACCESS_NONCE) String nonce,
            @RequestParam(Constants.STR_ACCESS_ECHOSTR) String echostr, HttpServletRequest request) {

        log.info("access validate begins!!!");
        log.info(Constants.STR_URL + Constants.STR_EQUAL + request.getRequestURL() + request.getQueryString());
        log.info(Constants.STR_ACCESS_SIGNATURE + Constants.STR_EQUAL + signature);
        log.info(Constants.STR_ACCESS_TIMESTAMP + Constants.STR_EQUAL + timestamp);
        log.info(Constants.STR_ACCESS_NONCE + Constants.STR_EQUAL + nonce);
        log.info(Constants.STR_ACCESS_ECHOSTR + Constants.STR_EQUAL + echostr);

        // 接入验证
        String validateResult = accessValidationService.doAccessValidation(signature, timestamp, nonce, echostr);

        log.info(Constants.STR_RESULT + Constants.STR_EQUAL + validateResult);
        log.info("access validate ends!!!");

        // 返回接入验证结果
        return validateResult;
    }
}
