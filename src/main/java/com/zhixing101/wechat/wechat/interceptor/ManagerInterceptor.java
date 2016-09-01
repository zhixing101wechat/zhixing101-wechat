package com.zhixing101.wechat.wechat.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户的过滤器 似乎不需要
 * Created by adam on 1/9/16.
 */
public class ManagerInterceptor extends HandlerInterceptorAdapter{

    private final Logger logger = LoggerFactory.getLogger(ManagerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("===========进入过滤器=========");
        request.setCharacterEncoding("UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            logger.info("用户已登录,完成验证");
            return true;
        } else {
            logger.info("用户未登录,跳转首页");
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
