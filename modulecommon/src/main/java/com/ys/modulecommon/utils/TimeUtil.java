package com.ys.modulecommon.utils;


import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/31.
 */
public class TimeUtil {
    /**
     * 获得系统时间
     * @return
     */
    public static String getLocalTime()
    {
        SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String   date   =   sDateFormat.format(new Date());
        return date;
    }

    /**
     * 获得指定格式的系统时间
     * @return
     */
    public static String getSystemTime()
    {
        SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");
        String   date   =   sDateFormat.format(new Date());
        return date;
    }
    /**
     * 改变系统时间
     * @param time 时间格式 yyyyMMdd.HHmmss
     */
    public static void changeSystemTime(String time){
        try {
            Process process = Runtime.getRuntime().exec("su");
            String datetime = time; //测试的设置的时间【时间格式 yyyyMMdd.HHmmss】
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("setprop persist.sys.timezone GMT\n");
            os.writeBytes("/system/bin/date -s "+datetime+"\n");
            os.writeBytes("clock -w\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器端返回的时间变成指定格式的时间 yyyyMMdd.HHmmss
     * @param time
     * @return
     */
    public static String changeServerTimeToSureTime(String time)
    {
        String finalTime="";
        String []s=time.substring(0,10).split("-");
        for (int i=0;i<s.length;i++)
        {
            finalTime+=s[i];
        }
        finalTime+=".";
        String m=time.substring(11,19);
        String [] z=m.split(":");
        for (int i=0;i<z.length;i++)
        {
            finalTime+=z[i];
        }
        return finalTime;
    }

    /**
     * 计算2个时间相差多少秒
     * @param str1
     * @param str2
     * @return
     */
    public static long getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            diff = time1 - time2;
//            if(time1<time2) {
//                diff = time2 - time1;
//            } else {
//                diff = time1 - time2;
//            }
            sec = (diff/1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sec;
    }
    /**
     * 计算2个时间相差多少天
     * @param str1
     * @param str2
     * @return
     */
    public static long getDistanceDays(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long day = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            diff = time1 - time2;
            day = diff / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
}
