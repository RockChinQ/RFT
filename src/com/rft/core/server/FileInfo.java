package com.rft.core.server;

/**
 * 描述正在接收的文件的信息
 */
public class FileInfo {
    private String name="";
    private String savePath="";
    /**
     * 文件大小，单位为KB
     */
    private long size=0;
    FileInfo(String name,long size,String savePath){
        this.name=name;
        this.size=size;
        this.savePath=savePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
