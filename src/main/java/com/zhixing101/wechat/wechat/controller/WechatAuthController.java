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
 * 微信认证Controller
 * 
 */
@Controller
@RequestMapping("/wechat")
public class WechatAuthController {

    /**
     * 日志记录器
     * 
     */
    private Logger logger = Logger.getLogger(WechatAuthController.class);

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

        logger.info("access validate begins!!!");
        logger.info(Constants.STR_URL + Constants.STR_EQUAL + request.getRequestURL() + request.getQueryString());
        logger.info(Constants.STR_ACCESS_SIGNATURE + Constants.STR_EQUAL + signature);
        logger.info(Constants.STR_ACCESS_TIMESTAMP + Constants.STR_EQUAL + timestamp);
        logger.info(Constants.STR_ACCESS_NONCE + Constants.STR_EQUAL + nonce);
        logger.info(Constants.STR_ACCESS_ECHOSTR + Constants.STR_EQUAL + echostr);

        // 接入验证
        String validateResult = accessValidationService.doAccessValidation(signature, timestamp, nonce, echostr);

        logger.info(Constants.STR_RESULT + Constants.STR_EQUAL + validateResult);
        logger.info("access validate ends!!!");

        // 返回接入验证结果
        return validateResult;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String replyMsg(HttpServletRequest request){
        logger.debug("收到微信消息,验证是否来自微信服务器");
        if (accessValidationService.checkWeixinReques(request)){
            //业务处理
        }
        return "";
    }
}
