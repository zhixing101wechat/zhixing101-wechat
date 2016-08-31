package com.zhixing101.wechat.wechat.event.req;

/**
 * 事件推送-扫描带参数二维码事件
 * 
 */
public class QRCodeEvent extends BaseEvent {

    // 事件KEY值
    private String EventKey;

    // 二维码的ticket，可用来换取二维码图片
    private String Ticket;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
