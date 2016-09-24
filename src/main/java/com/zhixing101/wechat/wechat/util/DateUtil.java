package com.zhixing101.wechat.wechat.util;

import org.apache.commons.lang.time.FastDateFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by adam on 8/4/16.
 */
public abstract class DateUtil {

    /**
     * 获取当前时间
     * @method_name
     * @author: wangyulong@dnion.com
     * @Title: getToday
     * @return Timestamp
     */
    public static Timestamp getToday(){
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String day = fdf.format(date);
        Timestamp today = Timestamp.valueOf(day);
        return today;
    }

    public static String getTodayStr(){
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String day = fdf.format(date);
        return day;
    }

    public static String getTodayYMDStr(){
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
        Date date = new Date();
        String day = fdf.format(date);
        return day;
    }

    public static String getTomorrowYMDStr(){
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, +1);
        String day = fdf.format(cal.getTime());
        return day;
    }

    /**
     * 时间类型转换
     * @param time
     * @return
     */
    public static String dateToString(Timestamp time){
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        String date = fdf.format(time);
        return date;
    }

    /**
     * 获取传入时间和现在时间的差
     * @param time
     * @return
     * @throws Exception
     */
    public static Integer checkDateBetween(String time) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(time);
        long diff = date.getTime() - new Date().getTime();
        long diffHours = diff / (60 * 60 * 1000) ;
        long diffMinutes = diff / (60 * 1000) % 60;
        int mins = (int) (diffHours * 60 + diffMinutes);
        return mins;
    }

    public static Timestamp getDateByExpires(Integer expires){
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.SECOND,expires);
		return Timestamp.valueOf(fdf.format(ca));
//        return fdf.format(ca);
    }

}
