package com.fh.entity;

/**
 * Created by RX-78 on 2019/7/16.
 */
public class TestVo {
    private String filePath;
    private String fileName;
    private String endStr;

    public TestVo() {
    }

    public TestVo(String filePath, String fileName,String endStr) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.endStr = endStr;
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



