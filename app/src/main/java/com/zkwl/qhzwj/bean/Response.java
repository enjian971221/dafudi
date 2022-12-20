package com.zkwl.qhzwj.bean;

/**
 * Created by Zaifeng on 2018/2/28.
 * 返回结果封装
 */

public class Response<T> {


    /**
     * success : 1
     * data : {}
     * msg : 展示信息
     * page : {"total":"记录总条数","pageSize":"每页条数","page":"当前页码","sort":"...","totalPage":"总页数","order":"..."}
     */

    private T data;
    private T pay_mode;
    private String msg;
    private int code;

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    public T getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(T pay_mode) {
        this.pay_mode = pay_mode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return 200 == code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}