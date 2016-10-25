package com.zhixing101.wechat.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.zhixing101.wechat.wechat.common.Constants;
import com.zhixing101.wechat.wechat.message.Message;
import com.zhixing101.wechat.wechat.message.req.ImageMessage;
import com.zhixing101.wechat.wechat.message.req.LinkMessage;
import com.zhixing101.wechat.wechat.message.req.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.Map;

/**
 * 微信解析所用的工具类
 * Created by adam on 1/9/16 .
 */
@Component("wechatUtils")
public class WechatUtils {

    private static final Logger logger = LoggerFactory.getLogger(WechatUtils.class);

    @Value("#{configProperties['weixin.token']}")
    private String token;


    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    try {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    });

    /**
     * 将回复消息对象转换成xml字符串
     *
     * @param reply 回复消息对象
     * @return 返回符合微信接口的xml字符串
     */
    public static String replyToXml() {
//        String type = "";
//        if (Constants.TEXT.equals(type)) {
//            xstream.omitField(Reply.class, "articles");
//            xstream.omitField(Reply.class, "articleCount");
//            xstream.omitField(Reply.class, "musicUrl");
//            xstream.omitField(Reply.class, "hQMusicUrl");
//            xstream.omitField(Reply.class, "title");
//            xstream.omitField(Reply.class, "MediaId");
//            xstream.omitField(Reply.class, "description");
//        } else if (Constants.IMAGE.equals(type)) {
//            xstream.omitField(Reply.class, "articles");
//            xstream.omitField(Reply.class, "articleCount");
//            xstream.omitField(Reply.class, "content");
//            xstream.omitField(Reply.class, "title");
//            xstream.omitField(Reply.class, "description");
//        } else if (Constants.VIDEO.equals(type)) {
//            xstream.omitField(Reply.class, "content");
//            xstream.omitField(Reply.class, "musicUrl");
//            xstream.omitField(Reply.class, "hQMusicUrl");
//        } else if (Constants.MUSIC.equals(type)) {
//            xstream.omitField(Reply.class, "articles");
//            xstream.omitField(Reply.class, "articleCount");
//            xstream.omitField(Reply.class, "content");
//        } else if (Constants.NEWS.equals(type)) {
//            xstream.omitField(Reply.class, "content");
//            xstream.omitField(Reply.class, "musicUrl");
//            xstream.omitField(Reply.class, "hQMusicUrl");
//            xstream.omitField(Reply.class, "title");
//            xstream.omitField(Reply.class, "description");
//        }
//        xstream.autodetectAnnotations(true);
//        xstream.alias("xml", reply.getClass());
//        xstream.alias("item", new Article().getClass());
//        return xstream.toXML(reply);
        return "";
    }

    /**
     * 存储数据的Map转换为对应的Message对象
     *
     * @param map 存储数据的map
     * @return 返回对应Message对象
     */
    public static Object mapToMessage(Map<String, String> map) {
        if (map == null)
            return null;
        String msgType = map.get("MsgType");
        Message message = new Message();
        message.setToUserName(map.get("ToUserName"));
        message.setFromUserName(map.get("FromUserName"));
        message.setCreateTime(Long.parseLong(map.get("CreateTime")));
        message.setMsgType(msgType);
        message.setMsgId(Long.parseLong(map.get("MsgId")));
        //根据消息类型定义对象
        if (msgType.equals(Constants.TEXT)) {
            message.setContent(map.get("Content"));
        } else if (msgType.equals(Constants.IMAGE)) {
            message.setPicUrl(map.get("PicUrl"));
            message.setMediaId(map.get("MediaId"));
        } else if (msgType.equals((Constants.VOICE))){
            message.setMediaId(map.get("MediaId"));
            message.setFormat(map.get("Format"));
            if (EmptyUtil.isEmpty(map.get("Recognition"))){    //语音识别结果
                message.setRecognition(map.get("Recognition"));
            }
        } else if (msgType.equals(Constants.VIDEO) || msgType.equals(Constants.SHORTVIDEO)){
            message.setThumbMediaId(map.get("ThumbMediaId"));
            message.setMediaId(map.get("MediaId"));
        } else if (msgType.equals(Constants.LOCATION)) {
            message.setLocationX(map.get("Location_X"));
            message.setLocationY(map.get("Location_Y"));
            message.setScale(map.get("Scale"));
            message.setLabel(map.get("Label"));
        } else if (msgType.equals(Constants.LINK)) {
            message.setTitle(map.get("Title"));
            message.setDescription(map.get("Description"));
            message.setUrl(map.get("Url"));
        } else if (msgType.equals(Constants.EVENT)) {
            message.setEvent(map.get("Event"));
            message.setEventKey(map.get("EventKey"));
        }
        return message;
    }
}
