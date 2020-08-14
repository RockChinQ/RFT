package com.rft.core.server;

public abstract class FileServer implements IFileServerControl{
    private int port=0;//端口
    private FileReceiver receiver;
    private TaskEvent taskEvent;
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
    public void setTaskEvent(TaskEvent event){
        this.taskEvent=event;
    }
    protected TaskEvent getTaskEvent(){
        return taskEvent;
    }
    public abstract void taskStarted(String token,FileInfo info);
    public abstract void taskFinished(String token,FileInfo info);
    public abstract void taskInterrupted(String token,FileInfo info);
}
