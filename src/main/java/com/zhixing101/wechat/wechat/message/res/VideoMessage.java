package com.zhixing101.wechat.wechat.message.res;

/**
 * 响应消息-回复视频消息
 * 
 */
public class VideoMessage extends BaseMessage {

    // 视频
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

}
