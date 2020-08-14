package com.rft.core.server;

public abstract class FileServer implements IFileServerControl{
    private int port=0;//端口
    private FileReceiver receiver;
    public void setPort(int port){
        this.port=port;
    }
    public int getPort(){
        return port;
    }

    public FileReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(FileReceiver receiver) {
        this.receiver = receiver;
    }
}
