package com.rft.core.server;

import com.rft.core.util.CommandSender;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓冲型的文件接收器
 */
public class BufferedFileReceiver extends FileReceiver{
    /**
     * 实际完成文件接收的线程
     */
    class ReceiveTask extends Thread{
        FileInfo info;
        DataInputStream dataInputStream;
        ReceiveTask(FileInfo fileInfo,DataInputStream dataInputStream){
            this.info=fileInfo;
            this.dataInputStream=dataInputStream;
        }

        @Override
        public void run() {
//            super.run();
            //接收文件

        }
    }

    /**
     * 保存了所有正在进行的接收任务，key为token
     */
    Map<String,ReceiveTask> taskMap=new HashMap<>();

    public BufferedFileReceiver(){
        super();
    }
    public BufferedFileReceiver(String rootPath){
        super(rootPath);
    }

    /**
     * 此方法为线程安全的，避免两个线程同时向map里面装新的任务
     * @param info
     * @param socket
     * @param dataInputStream
     * @param token
     * @throws Exception
     */
    @Override
    public synchronized void receiveFile(FileInfo info, Socket socket, DataInputStream dataInputStream,String token) throws Exception {
        ReceiveTask receiveTask=new ReceiveTask(info,dataInputStream);
        taskMap.put(token,receiveTask);
        receiveTask.start();
    }

    @Override
    public void interruptFile(String token) {
        if(taskMap.containsKey(token)){
            CommandSender.sendMsg(token,"reconn activelyInterrupt");
            taskMap.get(token).stop();
            taskMap.remove(token);
            System.gc();
        }else {
            return;
        }
    }

    @Override
    public void stopAll() {
        taskMap.keySet().forEach(this::interruptFile);
    }
}
