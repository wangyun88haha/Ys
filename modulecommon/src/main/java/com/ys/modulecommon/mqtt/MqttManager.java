package com.ys.modulecommon.mqtt;

import android.content.Context;
import android.text.TextUtils;

import com.ys.modulecommon.utils.LogUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2018/5/25.
 */
public class MqttManager {
    public static final String TAG="MqttManager";
    //用户名
    private String userName = "admin";
    //用户密码
    private String passWord = "admin";

    private MqttAndroidClient mqttAndroidClient;
    private  static  MqttManager mqttManager;
    private MqttManager()
    {
    }
    public static  MqttManager getMqttManager()
    {
        if(mqttManager==null)
        {
            mqttManager=new MqttManager();
        }
        return mqttManager;
    }

    /**
     * 配置mqtt
     * @param serverUrl 服务端url
     * @param clientId  客户端id
     * @param context
     * @param subscriptionTopic  订阅主题
     * @param qos  0  最多一次，有可能重复或丢失。   1 至少一次，有可能重复。 2只有一次，确保消息只到达一次
     */
    public void initMqttClient(String serverUrl,String clientId, Context context, final String subscriptionTopic, final int qos)
    {
        try {
            mqttAndroidClient = new MqttAndroidClient(context, serverUrl, clientId);
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            // 配置MQTT连接
            mqttConnectOptions.setCleanSession(false);
            mqttConnectOptions.setUserName(userName);
            mqttConnectOptions.setPassword(passWord.toCharArray());
//            mqttConnectOptions.setConnectionTimeout(5);  //超时时间
            mqttConnectOptions.setKeepAliveInterval(60); //心跳时间,单位秒
            mqttConnectOptions.setAutomaticReconnect(true);//自动重连
            mqttAndroidClient.connect(mqttConnectOptions);
            mqttAndroidClient.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean reconnect, String serverURI) {
                    LogUtil.e(TAG, "connectComplete ---> " + reconnect + "       serverURI--->" + serverURI);
                    LogUtil.e(TAG,"subscriptionTopic:"+subscriptionTopic);
                    subscribeToTopic(subscriptionTopic,qos);
                }

                @Override
                public void connectionLost(Throwable cause) {
                    LogUtil.e(TAG, "connectionLost ---> " + cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    LogUtil.e(TAG, "messageArrived topic ---> " + topic + "       message--->" + message.getId());
                    //服务器端下发的json数据
                    String arrivedResult=new String(message.getPayload(),"utf-8");
                    if(TextUtils.isEmpty(arrivedResult))
                    {
                        return;
                    }
                    if(onMqArriveMsgListener!=null)
                    {
                        onMqArriveMsgListener.mqArriveMsg(arrivedResult);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    LogUtil.e(TAG, "token ---> " + token);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    private void subscribeToTopic(String subscriptionTopic,int qos) {
        try {
            if(mqttAndroidClient==null)
            {
                return;
            }
            mqttAndroidClient.subscribe(subscriptionTopic, qos, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    LogUtil.e(TAG, "订阅成功 ---> " + asyncActionToken);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    LogUtil.e(TAG, "订阅失败 ---> " + exception);
                }
            });
        } catch (MqttException e) {
            LogUtil.e(TAG, "subscribeToTopic is error");
            e.printStackTrace();
        }
    }

    /**
     * 与服务器断开连接
     * @throws MqttException
     */
    public void disconnect() throws MqttException {
        if(mqttAndroidClient==null)
        {
            return;
        }
        mqttAndroidClient.disconnect();
    }

    public interface OnMqArriveMsgListener{
        void  mqArriveMsg(String arriveMsg);
    }

    public void setOnMqArriveMsgListener(OnMqArriveMsgListener onMqArriveMsgListener) {
        this.onMqArriveMsgListener = onMqArriveMsgListener;
    }

    private OnMqArriveMsgListener onMqArriveMsgListener;
}
