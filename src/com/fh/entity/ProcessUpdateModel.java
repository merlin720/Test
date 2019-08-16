package com.fh.entity;

import java.util.HashMap;

public class ProcessUpdateModel {
    private String filePath;
    private String key1;
    private String value;
    private String signal;

    private String map;
    /**
     * 是否需要添加分号
     */
    private String needAddF;

    public ProcessUpdateModel() {
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getNeedAddF() {
        return needAddF;
    }

    public void setNeedAddF(String needAddF) {
        this.needAddF = needAddF;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
