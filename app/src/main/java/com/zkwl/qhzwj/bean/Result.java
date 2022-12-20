package com.zkwl.qhzwj.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class Result<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public boolean isSuccess(){
        return  code == 0;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg=" + msg +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
