package com.zhixing101.wechat.wechat.message.res;

/**
 * 响应消息-回复语音消息
 * 
 */
public class VoiceMessage extends BaseMessage {

    // 语音
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}
