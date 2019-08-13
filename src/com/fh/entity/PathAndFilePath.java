package com.fh.entity;

/**
 * Created by RX-78 on 2019/7/16.
 */
public class PathAndFilePath {
    //有多少个配置文件夹的路径
    private String filePath;
    //当前是哪个系统的路径
    private String systemFilePath;
    private String fileName;
    private String endStr;
    private String key;


    public PathAndFilePath() {
    }

    public PathAndFilePath(String filePath, String fileName, String endStr) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.endStr = endStr;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getSystemFilePath() {
        return systemFilePath;
    }

    public void setSystemFilePath(String systemFilePath) {
        this.systemFilePath = systemFilePath;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}



