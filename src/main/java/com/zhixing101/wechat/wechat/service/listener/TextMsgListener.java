package com.zhixing101.wechat.wechat.service.listener;

import com.zhixing101.wechat.wechat.message.req.TextMessage;
import org.springframework.context.ApplicationEvent;

/**
 * 接收到文字消息推送的事件
 * Created by adam on 1/9/16.
 */
public class TextMsgListener extends ApplicationEvent {

    /*
     *接受到的消息对象
     */
    protected transient TextMessage textMessage;


    public TextMsgListener(Object source) {
        super(source);
    }

    public TextMessage getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(TextMessage textMessage) {
        this.textMessage = textMessage;
    }
}
