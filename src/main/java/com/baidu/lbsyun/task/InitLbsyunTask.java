package com.baidu.lbsyun.task;

import com.baidu.lbsyun.common.LbsConstants;
import com.baidu.lbsyun.req.Geotable;

/**
 * Lbs云初始化任务类
 *
 */
public class InitLbsyunTask {

    // 密钥
    private static String baiduAk = "N32lyWhU7RfjrFD76cYt6jmCHGWbicbd";

    // 创建表（create geotable）接口
    private static String createGeotableUrl = "http://api.map.baidu.com/geodata/v3/geotable/create";

    public static void main(String[] args) {

        // 创建【图书存放点】位置数据表
        createBookStoragePlaceGeotable();
    }

    private static void createBookStoragePlaceGeotable() {

        // 准备请求参数
        Geotable reqPara = new Geotable();
        reqPara.setName("图书存放点");
        reqPara.setGeotype(LbsConstants.GEOTYPE_POINT);
        reqPara.setIs_published(LbsConstants.IS_NOT_PUBLISHED);
        reqPara.setAk(baiduAk);

        System.out.println(baiduAk);
    }
}
