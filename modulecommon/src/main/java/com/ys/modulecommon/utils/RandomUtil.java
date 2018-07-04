package com.ys.modulecommon.utils;

import android.os.Build;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2018/5/29.
 */
public class RandomUtil {

    /**
     * 生成20位MD5 key
     * @return
     */
    public static String getRandomData()
    {
        String m_szDevIDShort = "35" +//we make this look like a valid IMEI

                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10+
                Build.DEVICE.length()%10+
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10+
                Build.ID.length()%10+
                Build.MANUFACTURER.length()%10+
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10+
                Build.TAGS.length()%10+
                Build.TYPE.length()%10+
                Build.USER.length()%10; //13 digits
        Random rand = new Random();
        String str = "";
        int index;
        char[] letters=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','r',};
        boolean[] flags = new boolean[letters.length];//默认为false
        for(int i=0;i<5;i++){
            do{
                index = rand.nextInt(letters.length);
            }while(flags[index]==true);
            char c = letters[index];
            str += c;
            flags[index]=true;
        }
        return  m_szDevIDShort+str;
    }

    //生成5位随机数
    public static String getFiveRandomData()
    {
        Random rand = new Random();
        String str = "";
        int index;
        char[] letters=new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','r',};
        boolean[] flags = new boolean[letters.length];//默认为false
        for(int i=0;i<5;i++){
            do{
                index = rand.nextInt(letters.length);
            }while(flags[index]==true);
            char c = letters[index];
            str += c;
            flags[index]=true;
        }
        return str;
    }
    //生成32位uuid
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
