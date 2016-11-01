package com.zhixing101.wechat.wechat.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhixing101.wechat.api.entity.Book;
import com.zhixing101.wechat.api.entity.BookStoragePlace;
import com.zhixing101.wechat.api.service.BookService;
import com.zhixing101.wechat.api.service.BookStoragePlaceService;
import com.zhixing101.wechat.wechat.token.TokenCache;
import com.zhixing101.wechat.wechat.util.JsSdkUtil;

/**
 * 微信业务Controller
 * 
 */
@Controller
public class WechatBusinessController {

    /**
     * 日志记录器
     * 
     */
    private Logger logger = Logger.getLogger(WechatBusinessController.class);

    @Autowired
    TokenCache tokenCache;

    @Autowired
    BookService bookService;

    @Autowired
    BookStoragePlaceService bookStoragePlaceService;

    @Value("#{configProperties['weixin.rootUrl']}")
    private String rootUrl;

    @Value("#{configProperties['weixin.appId']}")
    private String appId;

    @Value("#{configProperties['baidu.bookStoragePlaceGeotableId']}")
    private String bookStoragePlaceGeotableId;

    @Value("#{configProperties['baidu.searchBookStoragePlaceRadius']}")
    private String searchBookStoragePlaceRadius;

    /**
     * 找书
     * 
     * @param model
     * @param request
     * @return
     */
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
        model.addAttribute("searchBookStoragePlaceRadius", searchBookStoragePlaceRadius);

        return "findBook";
    }

    /**
     * 录书
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addBook", method = RequestMethod.GET)
    public String addBook(Model model, HttpServletRequest request) {

        String url = rootUrl + "/addBook?" + request.getQueryString();
        String noncestr = UUID.randomUUID().toString();
        String jsapi_ticket = tokenCache.getJsapi_ticket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = JsSdkUtil.getJsSdkSignature(noncestr, jsapi_ticket, timestamp, url);

        // Book bookInfo = JSONObject.parseObject(book, Book.class);
        // boolean resultFlag = bookService.saveBook(bookInfo);

        // logger.info("resultFlag ="+resultFlag);
        logger.info("url = " + url);
        logger.info("noncestr = " + noncestr);
        logger.info("jsapi_ticket = " + jsapi_ticket);
        logger.info("timestamp = " + timestamp);
        logger.info("signature = " + signature);

        // model.addAttribute("resultFlag", resultFlag);
        model.addAttribute("appId", appId);
        model.addAttribute("noncestr", noncestr);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("signature", signature);
        model.addAttribute("bookStoragePlaceGeotableId", bookStoragePlaceGeotableId);
        model.addAttribute("searchBookStoragePlaceRadius", searchBookStoragePlaceRadius);

        return "addBook";
    }

    @RequestMapping(value = "getLoc", method = RequestMethod.GET)
    public String getLoc(Model model, HttpServletRequest request) {

        // String url = rootUrl + "/getLoc?" + request.getQueryString();
        // String noncestr = UUID.randomUUID().toString();
        // String jsapi_ticket = tokenCache.getJsapi_ticket();
        // String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        // String signature = JsSdkUtil.getJsSdkSignature(noncestr,
        // jsapi_ticket, timestamp, url);
        //
        // logger.info("url = " + url);
        // logger.info("noncestr = " + noncestr);
        // logger.info("jsapi_ticket = " + jsapi_ticket);
        // logger.info("timestamp = " + timestamp);
        // logger.info("signature = " + signature);
        //
        // model.addAttribute("appId", appId);
        // model.addAttribute("noncestr", noncestr);
        // model.addAttribute("timestamp", timestamp);
        // model.addAttribute("signature", signature);
        //
        // return "getLoc";
        String url = rootUrl + "/getLoc?" + request.getQueryString();
        String noncestr = UUID.randomUUID().toString();
        String jsapi_ticket = tokenCache.getJsapi_ticket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String signature = JsSdkUtil.getJsSdkSignature(noncestr, jsapi_ticket, timestamp, url);

        // Book bookInfo = JSONObject.parseObject(book, Book.class);
        // boolean resultFlag = bookService.saveBook(bookInfo);

        // logger.info("resultFlag ="+resultFlag);
        logger.info("url = " + url);
        logger.info("noncestr = " + noncestr);
        logger.info("jsapi_ticket = " + jsapi_ticket);
        logger.info("timestamp = " + timestamp);
        logger.info("signature = " + signature);

        // model.addAttribute("resultFlag", resultFlag);
        model.addAttribute("appId", appId);
        model.addAttribute("noncestr", noncestr);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("signature", signature);
        model.addAttribute("bookStoragePlaceGeotableId", bookStoragePlaceGeotableId);
        model.addAttribute("searchBookStoragePlaceRadius", searchBookStoragePlaceRadius);

        return "addBook_bak20161024";
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

    @RequestMapping(value = "findBookByISBN", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findBookByISBN(@RequestParam("isbn") String isbn) {

        logger.debug("WechatBusinessController.findBookByISBN begin");
        logger.debug("isbn = " + isbn);

        Book book = bookService.findBookByISBN(isbn);
        String result = JSON.toJSONString(book);

        logger.debug("result = " + result);
        logger.debug("WechatBusinessController.findBookByISBN end");

        return result;
    }

    /**
     * 录书业务
     * 
     * @param book
     * @return
     */
    @RequestMapping(value = "addBookBiz", method = RequestMethod.POST)
    @ResponseBody
    public String addBookBiz(Book book) {

        boolean result = bookService.saveBook(book);
        return String.valueOf(result);
    }

    /**
     * 找书业务
     * 
     * @param keyword
     * @return
     */
    @RequestMapping(value = "findBookBiz", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findBookBiz(@RequestParam("keyword") String keyword) {
        return JSON.toJSONString(bookService.pagingQueryBooksByKeyword(keyword, 10, 1));
    }

    /**
     * 创建存书点业务
     * 
     * @param bookStoragePlace
     * @return
     */
    @RequestMapping(value = "createBookStoragePlaceBiz", method = RequestMethod.POST)
    @ResponseBody
    public String createBookStoragePlaceBiz(BookStoragePlace bookStoragePlace) {

        boolean result = bookStoragePlaceService.saveBookStoragePlace(bookStoragePlace);
        return String.valueOf(result);
    }

    /**
     * 测试queryAllBooks
     * 
     * @return
     */
    @RequestMapping(value = "queryAllBooks", method = RequestMethod.GET)
    @ResponseBody
    public String queryAllBooks() {

        logger.debug("WechatBusinessController#queryAllBooks begin");

        List<Book> queryResult = bookService.queryAllBooks();

        for (Book loop : queryResult) {
            logger.debug(loop);
        }
        logger.debug("WechatBusinessController#queryAllBooks end");

        return JSON.toJSONString(queryResult);
    }
}
