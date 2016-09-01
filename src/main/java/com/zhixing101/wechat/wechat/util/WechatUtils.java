package com.zhixing101.wechat.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Writer;

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
                        // TODO Auto-generated catch block
                        System.out.println(e);
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
}
