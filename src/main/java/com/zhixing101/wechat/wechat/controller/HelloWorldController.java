package com.zhixing101.wechat.wechat.controller;

import com.zhixing101.wechat.api.entity.Book;
import com.zhixing101.wechat.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * HelloWorldController
 * 
 */
@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @Autowired
    BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        Book book = bookService.findBookByISBN("9787515000541");
        model.addAttribute("book", book);
        return "index";
    }

    @RequestMapping("/shutdown")
    public String shut(Model model) {
        Book book = new Book();
        bookService.deleteBook(book);
        return "index";
    }

}
