package com.ys.modulecommon.utils;

import java.text.DecimalFormat;
import java.util.Map;

import okhttp3.FormBody;

/**
 * Created by Administrator on 2018/6/12.
 */
public class CommonUtil {
    /**
     * map转为builder
     * @param map
     * @return
     */
    public static FormBody.Builder mapToBuilder(Map<String,String> map)
    {
        FormBody.Builder builder=new FormBody.Builder();
        for (String key:map.keySet())
        {
            builder.add(key,map.get(key));
        }
        return builder;
    }
    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
