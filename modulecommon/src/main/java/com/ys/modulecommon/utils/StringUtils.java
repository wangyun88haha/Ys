package com.ys.modulecommon.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */
public class StringUtils {

    public static String listToString(List<String> list)
    {
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<list.size();i++)
        {
            if(i==list.size()-1) {
            sb.append(list.get(i));
            }else
        {
            sb.append(list.get(i) + ",");
        }
        }
        return sb.toString();
    }

    public static List<String> stringToList(String soltNos)
    {
        List<String> soltNosList=new ArrayList<>();
        String [] m=soltNos.substring(0,soltNos.length()).split(",");
        for (int i=0;i<m.length;i++)
        {
            soltNosList.add(m[i]);
        }
        return soltNosList;
    }
}
