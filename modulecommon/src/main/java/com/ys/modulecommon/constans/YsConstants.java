package com.ys.modulecommon.constans;

import android.os.Environment;

/**
 * Created by Administrator on 2018/5/22.
 */
public class YsConstants {
    public static final String TOPIC="MACHINE_CMD.MQTT";
    //服务器地址
    public static final String SERVER_URL="tcp://58.250.168.194:8086";
    //推送质量  1 至少一次，有可能重复
    public static final int QOS=1;
    // 下载存储的文件名
    public static final String DOWNLOAD_NAME = "lattices.apk";
    //公钥
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd79jKJcPViZDlBlohKOdH+gpQtH2HIugOV6g3HxamD3Gul45gYc+bCZ84wFtNJnPRaLmzHVlYW1Tn9M3VkMyvHzlhV8SLDZ5sBM1dKAvdful5VJdGWoWnCsEk6L724hBa3z+Gm6dK3DvAGnXYjyOo65Jwn2vVR+40iY6HKxtijwIDAQAB";
    //公共请求地址
    public static final String BASE_URL="http://58.250.168.194:8082/wgo-api-gateway-ci/machine/";
    //所有存储文件的根目录
    public static final String  FILE_NAME= Environment.getExternalStorageDirectory()+"/"+"YsFolder/";
    // md5 key 机器编号 机器id 存储路径
    public static final String  MACHINE_MESSAGE= FILE_NAME+"machineMessage.txt";
    //接口版本号
    public static final String SERVER_VERSION="V1.0.0";
    //服务器返回请求成功状态
    public static final String SERVER_STATUS="API_SUCCESS";
    //开机自启动广播
    public static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    public static final String INFO_SYNC_URL=BASE_URL+"infoSync.json";
    public static final String SOLT_SYNC_URL=BASE_URL+"slotSync.json";
    public static final String OPEN_DOOR_CALLBAEK_URL=BASE_URL+"openDoorCallback.json";
    public static final String INFO_POLL__URL=BASE_URL+"infoPoll.json";
    //正常上报开门反馈接口
    public static final int OPEND_DOOR_CALLBACK_NORMAL=1027;
    //异常上报开门反馈接口
    public static final int OPEND_DOOR_CALLBACK_UNNORMAL=1028;
    //上报流量
    public static final int UPLOAD_FLOW=3;

    public static final int UPLOAD_LOCATION=4;
    //购物开门
    public static final int  SHOPPING_GOODS = 2;
    //上货开门
    public static final int  EXHIBIT_GOODS = 1;
}
