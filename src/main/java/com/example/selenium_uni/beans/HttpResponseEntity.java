package com.example.selenium_uni.beans;

public class HttpResponseEntity {
    private String code; // 状态码
    private Object data; // 返回数据
    private String message; // 状态消息

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
