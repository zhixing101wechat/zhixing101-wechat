package com.zhixing101.wechat.wechat.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    // @RequestMapping(value = "redirectFindBook", method = RequestMethod.GET)
    // public String redirectFindBook() throws UnsupportedEncodingException {
    //
    // String tmpUrl =
    // webGrantRequestUrl.replaceFirst(Constants.STR_REDIRECT_URI,
    // URLEncoder.encode(findBookUrl,
    // Constants.STR_ENCODING_UTF_8).replace(Constants.STR_ASTERISK,
    // Constants.STR_PER_CENT_TWO_A));
    // tmpUrl = tmpUrl.replaceFirst(Constants.STR_SCOPE,
    // Constants.STR_SNSAPI_USERINFO);
    // tmpUrl = tmpUrl.replaceFirst(Constants.STR_STATE, "123");
    // tmpUrl = Constants.STR_REDIRECT_COLON + tmpUrl;
    // logger.info(tmpUrl);
    // return tmpUrl;
    // }
    //
    @RequestMapping(value = "findBook", method = RequestMethod.GET)
    public String findBook(HttpServletRequest request, HttpServletResponse response) {
        return "findBook";
    }

    @RequestMapping(value = "getLoc", method = RequestMethod.GET)
    public String getLoc(Model model, HttpServletRequest request) {

        String url = rootUrl + "/getLoc?" + request.getQueryString();
        String noncestr = UUID.randomUUID().toString();
        String jsapi_ticket = tokenCache.getJsapi_ticket();
//        System.out.println("@getLoc jsapi_ticket : " + jsapi_ticket);
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
}
