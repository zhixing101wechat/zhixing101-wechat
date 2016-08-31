package com.zhixing101.wechat.wechat.message.res;

/**
 * 响应消息-语音
 * 
 */
public class Voice {

    // 通过素材管理中的接口上传多媒体文件，得到的id
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
