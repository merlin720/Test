package com.fh.entity;

public class UpdateModel {
    private String filePath;
    private String key1;
    private String value;
    private String signal;
    /**
     * 是否需要添加分号
     */
    private String needAddF;

    public UpdateModel() {
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
}
