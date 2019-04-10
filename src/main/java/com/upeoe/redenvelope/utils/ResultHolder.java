package com.upeoe.redenvelope.utils;


/**
 * @author upeoe
 * @create 2019/4/11 02:59
 */
public class ResultHolder {
    private String msg;
    private String code;
    private Object data;

    public static final String OK = "200";
    public static final String FAILED = "999";

    public ResultHolder() {
        this.code = OK;
        this.msg = "";
    }

    public ResultHolder(Object data) {
        this.msg = "ok";
        this.code = OK;
        this.data = data;
    }

    public ResultHolder(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
