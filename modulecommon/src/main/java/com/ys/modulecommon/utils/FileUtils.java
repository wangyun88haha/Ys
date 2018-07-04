package com.ys.modulecommon.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2018/5/28.
 */
public class FileUtils {
    /**
     * 判断某个文件是否存在
     * @return
     */
    public static boolean isFilePathExit(String filePath)
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    /**
     * 创建一个文件夹
     */
    public static void createDir(Context context,String filePath)
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            File f=new File(filePath);
            if(!f.exists())
            {
                f.mkdirs();
                Log.e("TAG", f.getAbsolutePath());
            }
        }else
        {
            Toast.makeText(context, "no sdcard", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从文件中读取内容
     * @param context
     * @param fileName
     * @return
     */
    public static String readContentFromFile(Context context,String fileName) {


        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            File targetFile = new File(fileName);
            String readedStr = "";
            try {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                    return null;
                } else {
                    InputStream in = new BufferedInputStream(new FileInputStream(targetFile));
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String tmp;

                    while ((tmp = br.readLine()) != null) {
                        readedStr += tmp;
                    }
                    br.close();
                    in.close();
                    return readedStr;
                }
            } catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                return e.toString();
            }
        } else {
            Toast.makeText(context, "未发现SD卡！", Toast.LENGTH_LONG).show();
            return "SD Card error";
        }

    }
    /**
     * 写内容到文件
     * @param context
     * @param stringToWrite
     * @param filePath
     */
    public static boolean writeContentToText(Context context, String stringToWrite,String filePath) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            File targetFile = new File(filePath);
            OutputStreamWriter osw;
            try {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                    osw = new OutputStreamWriter(new FileOutputStream(targetFile), "utf-8");
                    osw.write(stringToWrite);
                    osw.close();
                } else {
                    osw = new OutputStreamWriter(new FileOutputStream(targetFile, true), "utf-8");
                    osw.write("\n" + stringToWrite);
                    osw.flush();
                    osw.close();
                }
                return true;
            } catch (Exception e) {
                LogUtil.e("TAG", e.toString());
                return false;
            }
        } else {
            LogUtil.e("TAG", "未发现SD卡！");
            return false;
        }
    }

}
