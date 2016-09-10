package com.zhixing101.wechat.wechat.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhixing101.wechat.wechat.token.TokenCache;
import com.zhixing101.wechat.wechat.util.JsSdkUtil;

/**
 * 微信重定向Controller
 * 
 */
@Controller
public class WechatRedirectController {

    /**
     * 日志记录器
     * 
     */
    private Logger logger = Logger.getLogger(WechatRedirectController.class);

    @Autowired
    TokenCache tokenCache;

    @Value("#{configProperties['weixin.rootUrl']}")
    private String rootUrl;

    @Value("#{configProperties['weixin.appId']}")
    private String appId;

    @Value("#{configProperties['baidu.bookStoragePlaceGeotableId']}")
    private String bookStoragePlaceGeotableId;

    @RequestMapping(value = "findBook", method = RequestMethod.GET)
    public String findBook(Model model, HttpServletRequest request) {

        String url = rootUrl + "/findBook?" + request.getQueryString();
        String noncestr = UUID.randomUUID().toString();
        String jsapi_ticket = tokenCache.getJsapi_ticket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = JsSdkUtil.getJsSdkSignature(noncestr, jsapi_ticket, timestamp, url);

        logger.info("url = " + url);
        logger.info("noncestr = " + noncestr);
        logger.info("jsapi_ticket = " + jsapi_ticket);
        logger.info("timestamp = " + timestamp);
        logger.info("signature = " + signature);

        model.addAttribute("appId", appId);
        model.addAttribute("noncestr", noncestr);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("signature", signature);
        model.addAttribute("bookStoragePlaceGeotableId", bookStoragePlaceGeotableId);

        return "findBook";
    }

    @RequestMapping(value = "getLoc", method = RequestMethod.GET)
    public String getLoc(Model model, HttpServletRequest request) {

        String url = rootUrl + "/getLoc?" + request.getQueryString();
        String noncestr = UUID.randomUUID().toString();
        String jsapi_ticket = tokenCache.getJsapi_ticket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = JsSdkUtil.getJsSdkSignature(noncestr, jsapi_ticket, timestamp, url);

        logger.info("url = " + url);
        logger.info("noncestr = " + noncestr);
        logger.info("jsapi_ticket = " + jsapi_ticket);
        logger.info("timestamp = " + timestamp);
        logger.info("signature = " + signature);

        model.addAttribute("appId", appId);
        model.addAttribute("noncestr", noncestr);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("signature", signature);

        return "getLoc";
    }

    @RequestMapping(value = "getLoc4Pad", method = RequestMethod.GET)
    public String getLoc4Pad(Model model, HttpServletRequest request) {

        String url = rootUrl + "/getLoc4Pad?" + request.getQueryString();
        String noncestr = UUID.randomUUID().toString();
        String jsapi_ticket = tokenCache.getJsapi_ticket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = JsSdkUtil.getJsSdkSignature(noncestr, jsapi_ticket, timestamp, url);

        logger.info("url = " + url);
        logger.info("noncestr = " + noncestr);
        logger.info("jsapi_ticket = " + jsapi_ticket);
        logger.info("timestamp = " + timestamp);
        logger.info("signature = " + signature);

        model.addAttribute("appId", appId);
        model.addAttribute("noncestr", noncestr);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("signature", signature);

        return "getLoc4Pad";
    }
}
