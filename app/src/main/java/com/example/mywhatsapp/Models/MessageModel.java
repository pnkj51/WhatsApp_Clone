package com.example.mywhatsapp.Models;

public class MessageModel {
    String uId,msg,msgId;
    Long timeStamp;

    public MessageModel(String uId, String msg, Long timeStamp) {
        this.uId = uId;
        this.msg = msg;
        this.timeStamp = timeStamp;
    }

    public MessageModel(String uId, String msg) {
        this.uId = uId;
        this.msg = msg;
    }

    public MessageModel() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getuId() {
        return uId;
    }

    public String getMsg() {
        return msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
