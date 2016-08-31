package com.zhixing101.wechat.wechat.message.req;

/**
 * 请求消息-文本消息
 * 
 */
public class TextMessage extends BaseMessage {

    // 文本消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
