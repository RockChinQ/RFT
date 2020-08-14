package com.rft.core.server;

import java.io.InputStream;

public abstract class FileReceiver implements IFileReceiverControl{
    private String rootPath="";
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

    public String getRootPath() {
        return rootPath;
    }

    /**
     * 最后必须跟一个路径分隔符!
     * @param rootPath
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

}
