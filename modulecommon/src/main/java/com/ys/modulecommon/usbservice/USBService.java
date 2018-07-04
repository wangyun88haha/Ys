package com.ys.modulecommon.usbservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ys.modulecommon.utils.ByteUtils;
import com.ys.modulecommon.utils.LogUtil;

import android_serialport_api.SerialPortUtil;


public class USBService extends Service {

    private static final String TAG = USBService.class.getSimpleName();
    private Handler mHandler = new Handler();
    private final IBinder mBinder = new USBBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * 重新绑定服务时
     */
    @Override
    public void onRebind(Intent intent) {
        //初始化串口信息
        super.onRebind(intent);
    }

    /**
     * 解除绑定服务时
     */
    @Override
    public boolean onUnbind(Intent intent) {
        //关闭串口
        return super.onUnbind(intent);
    }

    public class USBBinder extends Binder {
        public USBService getService() {
            return USBService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SerialPortUtil.getInstance().onCreate();
        SerialPortUtil.getInstance().setOnDataReceiveListener(new SerialPortUtil.OnDataReceiveListener() {
            @Override
            public void onDataReceive(final byte[] buffer, final int size) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        byte[] msg = new byte[size];
                        System.arraycopy(buffer, 0, msg, 0, size);
                        if(onReceivePlcDataListener!=null)
                        {
                            onReceivePlcDataListener.receivePlcData(ByteUtils.decodeBytesToHexString(msg));
                        }
                    }
                });
            }
        });
    }

    public interface OnReceivePlcDataListener
    {
        void receivePlcData(String plcData);
    }

    public void setOnReceivePlcDataListener(OnReceivePlcDataListener onReceivePlcDataListener) {
        this.onReceivePlcDataListener = onReceivePlcDataListener;
    }

    private OnReceivePlcDataListener onReceivePlcDataListener;
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("TAG", "onDestroy USB");
        SerialPortUtil.getInstance().closeSerialPort();
    }
    //向串口发送数据
    public void write(byte [] data) {
        SerialPortUtil.getInstance().sendBuffer(data);
    }

}
