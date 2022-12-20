package com.zkwl.app_classroom.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class Result<T> {

    @SerializedName("code")
    private int code;

    private String message;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("data")
    private T data;

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
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess(){
        return  code == 0;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message=" + message +
                ", msg=" + msg +
                ", data=" + data +
                '}';
    }
}
