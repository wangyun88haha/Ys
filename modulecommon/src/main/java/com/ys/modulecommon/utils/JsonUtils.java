package com.ys.modulecommon.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/5.
 */
public class JsonUtils {
    /**
     * json字符串转换为map
     * @param gson
     * @param backMsgContent
     * @return
     */
    public static Map<String,String> parseJsonToMap(Gson gson,String backMsgContent)
    {
        return gson.fromJson(backMsgContent, new TypeToken<HashMap<String,String>>(){}.getType());
    }
}
