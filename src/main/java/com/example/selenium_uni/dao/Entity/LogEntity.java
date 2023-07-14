package com.example.selenium_uni.dao.Entity;

import java.sql.Timestamp;

public class LogEntity {
    int logid;
    Timestamp time;
    String id;
    String type;
    String state;
    String exception;
    public int getLogid() {
        return logid;
    }
    public void setLogid(int logid) {
        this.logid = logid;
    }
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getException() {
        return exception;
    }
    public void setException(String exception) {
        this.exception = exception;
    }
}
