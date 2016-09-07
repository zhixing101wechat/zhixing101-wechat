package com.zhixing101.wechat.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhixing101.wechat.wechat.common.Constants;

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

    @Value("#{configProperties['weixin.webGrantRequestUrl']}")
    private String webGrantRequestUrl;

    @Value("#{configProperties['weixin.findBookUrl']}")
    private String findBookUrl;

    @RequestMapping(value = "redirectFindBook", method = RequestMethod.GET)
    public String redirectFindBook() throws UnsupportedEncodingException {

        String tmpUrl = webGrantRequestUrl.replaceFirst(Constants.STR_REDIRECT_URI,
                URLEncoder.encode(findBookUrl, Constants.STR_ENCODING_UTF_8).replace(Constants.STR_ASTERISK,
                        Constants.STR_PER_CENT_TWO_A));
        tmpUrl = tmpUrl.replaceFirst(Constants.STR_SCOPE, Constants.STR_SNSAPI_USERINFO);
        tmpUrl = tmpUrl.replaceFirst(Constants.STR_STATE, "123");
        tmpUrl = Constants.STR_REDIRECT_COLON + tmpUrl;
        logger.info(tmpUrl);
        return tmpUrl;
    }

    @RequestMapping(value = "findBook", method = RequestMethod.GET)
    public String findBook(HttpServletRequest request, HttpServletResponse response) {
        return "findBook";
    }

    @RequestMapping(value = "getLoc", method = RequestMethod.GET)
    public String getLoc(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletRequest request) {
        
        logger.info(request.getRequestURL() + request.getQueryString());
        logger.info(code);
        logger.info(state);
        return "getLoc";
    }
}
