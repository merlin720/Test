package com.fh.entity;

/**
 * Created by RX-78 on 2019/7/16.
 */
public class SettingAddBean {
    private String sourcePath;
    private String destPath;


    public SettingAddBean() {
    }


    public SettingAddBean(String sourcePath, String destPath) {

        this.sourcePath = sourcePath;
        this.destPath = destPath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }
}



