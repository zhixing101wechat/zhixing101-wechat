package com.zhixing101.wechat.wechat.message.res;

/**
 * 响应消息-回复图片消息
 * 
 */
public class ImageMessage extends BaseMessage {

    // 图片
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
