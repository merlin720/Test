package com.fh.entity;

/**
 * Created by RX-78 on 2019/7/16.
 */
public class SettingModel {
    private String filePath;
    private String fileName;
    private String endStr;

    public SettingModel() {
    }

    public SettingModel(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }
}



