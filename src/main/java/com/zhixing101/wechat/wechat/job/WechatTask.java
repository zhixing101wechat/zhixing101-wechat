package com.zhixing101.wechat.wechat.job;

import com.zhixing101.wechat.wechat.util.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 定时同步微信公众号的token
 * Created by adam on 1/9/16.
 */
public class WechatTask {

    private static final Logger logger = LoggerFactory.getLogger(WechatTask.class);

//    @Autowired
//    WechatService wechatService;

    @Autowired
    WechatUtils wechatUtils;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    public void executeTask(){
        //定时同步微信公众号的token

        //获取accessToken
        /**
         * 1.先获取本地库中的accesstoken
         * 2.判断accesstoken是否失效
         * 3.如果accesstoken距离失效时间少于15分钟则进行accesstoken重新获取
         * 4.将最新的accesstoken保存到数据库中
         * 注意:后期将修改为本地缓存文件
         */
        CheckAccessToken checkAccessToken = new CheckAccessToken();
        taskExecutor.execute(checkAccessToken);

    }

    class CheckAccessToken implements Runnable{

        public void run() {
            //获取本地数据库中的accesstoken
//            List<Wechat> wechatList = wechatService.queryWechatList();
//            for (Wechat wechat : wechatList){
//                //判断当前accesstoken是否超时
//                try {
//                    int between = 0;
//                    if (!EmptyUtil.isEmpty(wechat.getOverDate())){
//                        between = DateUtil.checkDateBetween(DateUtil.dateToString(wechat.getOverDate()));
//                        logger.debug("输出between"+between);
//                    }
//                    logger.debug("当前时间与微信{}的accesstoken失效时间差为{}",new Object[]{wechat.getCname(),between+""});
//                    if (between < 15) {
//                        logger.debug("当前微信需要重新获取accesstoken");
//                        TokenVO tokenVO = wechatUtils.getAccessToken(wechat);
//                        logger.debug("tokenVO========="+tokenVO.getAccess_token());
//                        wechat.setToken(tokenVO.getAccess_token());
//                        String jstoken = wechatUtils.getJSToken(tokenVO.getAccess_token());
//                        logger.debug("jstoken====现在的==="+jstoken);
//                        wechat.setJstoken(jstoken);
//                        //获取当前时间2小时后的时间
//                        Timestamp expires = DateUtil.getDateByExpires(tokenVO.getExpires_in());
//                        wechat.setOverDate(expires);
//                        logger.debug("更新jstoken");
//                        wechatService.updateWechat(wechat);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
