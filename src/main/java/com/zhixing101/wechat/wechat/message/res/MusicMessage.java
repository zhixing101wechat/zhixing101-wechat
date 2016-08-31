package com.zhixing101.wechat.wechat.message.res;

/**
 * 响应消息-回复音乐消息
 * 
 */
public class MusicMessage extends BaseMessage {

    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
