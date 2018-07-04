package com.ys.modulecommon.bean;

/**
 * Created by Administrator on 2018/6/25.
 */
public class OpenFeedbackMsg {
    public OpenFeedbackMsg(String soltNo, String status, String plcContent) {
        this.soltNo = soltNo;
        this.status = status;
        this.plcContent = plcContent;
    }

    public String getSoltNo() {
        return soltNo;
    }

    public String getStatus() {
        return status;
    }

    public String getPlcContent() {
        return plcContent;
    }

    //货道号
    private String soltNo;
    //货道状态
    private String status;
    //下位机返回内容
    private String plcContent;
}
