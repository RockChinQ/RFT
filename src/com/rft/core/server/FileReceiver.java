package com.rft.core.server;

import java.io.InputStream;

public abstract class FileReceiver implements IFileReceiverControl{
    private String rootPath="";
    private long maxSize=-1;//文件最大为-1则表示无限制
    private long minSize=0;

    private FileServer fileServer;
    public FileReceiver(){
        ;
    }

    public FileServer getFileServer() {
        return fileServer;
    }

    public void setFileServer(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    public FileReceiver(String rootPath){
        this.rootPath=rootPath;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getMinSize() {
        return minSize;
    }

    public void setMinSize(long minSize) {
        this.minSize = minSize;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

}
