package com.zhixing101.wechat.wechat.event.req;

/**
 * 事件推送-自定义菜单事件
 * 
 */
public class ClickEvent extends BaseEvent {

    // 事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
