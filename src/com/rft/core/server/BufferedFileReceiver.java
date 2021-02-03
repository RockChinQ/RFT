package com.rft.core.server;

import com.rft.core.util.CommandSender;
import com.rft.core.util.Out;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓冲型的文件接收器
 */
public class BufferedFileReceiver extends FileReceiver{
    /**
     * 实际完成文件接收的线程
     */
    public class ReceiveTask extends Thread{
        FileInfo info;
        DataInputStream dataInputStream;
        String token="";
        long receivedSize=0;
        ReceiveTask(FileInfo fileInfo,DataInputStream dataInputStream,String token){
            this.info=fileInfo;
            this.dataInputStream=dataInputStream;
            this.token=token;
        }

        @Override
        public void run() {
//            super.run();
            try {
                /**
                 * 建立指向文件的输出流
                 */
                receivedSize=0;
                getFileServer().taskStarted(token,info);
                File saveAbsolutePath = new File(getRootPath() + info.getSavePath());
                if (!saveAbsolutePath.exists() || !saveAbsolutePath.isDirectory()) {//不存在则创建保存目录
                    saveAbsolutePath.mkdirs();
                }
                File target = new File(saveAbsolutePath.getAbsolutePath() + File.separatorChar + info.getName().replaceAll("MASK",""));
                FileOutputStream fileOutputStream = new FileOutputStream(target);

                //接收文件
                byte[] bytes =new byte[1024];
                int length=0;
                while ((length=dataInputStream.read(bytes,0,bytes.length))!=-1){
                    fileOutputStream.write(bytes,0,length);
                    fileOutputStream.flush();
                    receivedSize+=length;
                }
                //接收完成，调用用户的taskEvent
                taskMap.remove(token);
                fileOutputStream.close();
                dataInputStream.close();
                getFileServer().taskFinished(token,info);
            }catch (Exception e){
//                e.printStackTrace();
//                Out.say("ReceiveTask","接收文件失败 token:"+token);
                interruptFile(token);
            }
        }

        public FileInfo getInfo() {
            return info;
        }

        public String getToken() {
            return token;
        }

        public long getReceivedSize() {
            return receivedSize;
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

    public Map<String,ReceiveTask> getTaskMap(){
        return taskMap;
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
        ReceiveTask receiveTask=new ReceiveTask(info,dataInputStream,token);
        taskMap.put(token,receiveTask);
        receiveTask.start();
    }

    @Override
    public void interruptFile(String token) {
        if(taskMap.containsKey(token)){
            CommandSender.sendMsg(token,"reconn activelyInterrupt");
            getFileServer().taskInterrupted(token,taskMap.get(token).info);
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
