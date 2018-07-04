package android_serialport_api;

import android.util.Log;

import com.ys.modulecommon.utils.ByteUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SerialPortUtil {
    private String TAG = SerialPortUtil.class.getSimpleName();
    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    //端口号S3
    private String path = "/dev/ttymxc1";
    //波特率
    private int baudrate = 9600;
    private static SerialPortUtil portUtil;
    private OnDataReceiveListener onDataReceiveListener = null;  

    public interface OnDataReceiveListener {  
        void onDataReceive(byte[] buffer, int size);
    }  
  
    public void setOnDataReceiveListener(  
            OnDataReceiveListener dataReceiveListener) {  
        onDataReceiveListener = dataReceiveListener;  
    }  
      
    public static SerialPortUtil getInstance() {
        if (null == portUtil) {  
            portUtil = new SerialPortUtil();
        }
        return portUtil;  
    }  
  
    /** 
     * 初始化串口信息 
     */  
    public void onCreate() {  
        try {  
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
//            Log.e("TAG","135334");
            mOutputStream = mSerialPort.getOutputStream();  
            mInputStream = mSerialPort.getInputStream();  
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }
  
    /** 
     * 发送指令到串口 
     *  
     * @param cmd 
     * @return 
     */  
    public boolean sendCmds(String cmd) {
        boolean result = true;  
        byte[] mBuffer = (cmd+"\r\n").getBytes();  
//注意：我得项目中需要在每次发送后面加\r\n，大家根据项目项目做修改，也可以去掉，直接发送mBuffer  
        try {  
            if (mOutputStream != null) {  
                mOutputStream.write(mBuffer);  
            } else {  
                result = false;  
            }  
        } catch (IOException e) {
            e.printStackTrace();  
            result = false;  
        }  
        return result;  
    }  
  
    public boolean sendBuffer(byte[] mBuffer) {
        Log.e("TAG", "发送的数据" + ByteUtils.decodeBytesToHexString(mBuffer));
        boolean result = true;
//        String tail = "\r\n";
//        byte[] tailBuffer = tail.getBytes();
//        byte[] mBufferTemp = new byte[mBuffer.length+tailBuffer.length];
//        System.arraycopy(mBuffer, 0, mBufferTemp, 0, mBuffer.length);
//        System.arraycopy(tailBuffer, 0, mBufferTemp, mBuffer.length, tailBuffer.length);
////注意：我得项目中需要在每次发送后面加\r\n，大家根据项目项目做修改，也可以去掉，直接发送mBuffer
        try {  
            if (mOutputStream != null) {  
                mOutputStream.write(mBuffer);
            } else {
                result = false;  
            }  
        } catch (IOException e) {
            e.printStackTrace();
            result = false;  
        }
        return result;  
    }  

    private class ReadThread extends Thread {
  
        @Override
        public void run() {  
            super.run();  
//            while (!isStop && !isInterrupted()) {
                while(!isInterrupted()) {
                    int size;
                    try {
                        if (mInputStream == null)
                            return;
                        byte[] buffer = new byte[512];
                        size = mInputStream.read(buffer);
                        if (size > 0) {
                            if (null != onDataReceiveListener) {
                                onDataReceiveListener.onDataReceive(buffer, size);
                            }
                        }
//                    LockSupport.parkNanos(1000000*3);
//                    Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
//            }
        }
    }  
  
    /** 
     * 关闭串口 
     */  
    public void closeSerialPort() {  
//        isStop = true;
        if (mReadThread != null) {
            mReadThread.interrupt();  
        }  
        if (mSerialPort != null) {  
            mSerialPort.close();  
        }  
    }  
      
}  