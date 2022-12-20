package com.zkwl.app_healthy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class Result<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("msg")
    private String message;

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
                ", data=" + data +
                '}';
    }
}
