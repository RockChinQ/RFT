package com.rft.core.server;

import com.rft.core.util.CommandSender;
import com.rft.core.util.Out;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ParallelFileServer extends FileServer{

    private ServerSocket serverSocket;
    /**
     * 用于堵塞式接受连接的线程
     */
    private Thread accept=new Thread(()->{
        try{
            while (true){
                Socket socket=serverSocket.accept();
                DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
                /**
                 * 读取文件名、相对保存路径、token和大小并发送到receiver
                 */
                new Thread(()->{
                    try {
                        String name = dataInputStream.readUTF();
                        String savePath = dataInputStream.readUTF();
                        String token = dataInputStream.readUTF();
                        long size = dataInputStream.readLong();
                        /**
                         * 尝试新建命令发送目标
                         */
                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        try{
                            CommandSender.addTarget(token,bufferedWriter);
                        }catch (Exception e){
                            e.printStackTrace();
                            bufferedWriter.write("reset tokenAlreadyExists");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                        this.getReceiver().receiveFile(new FileInfo(name, size, savePath), socket, dataInputStream, token);
                    }catch (Exception e){
                        e.printStackTrace();
                        Out.say("ParallelFileServer","接受连接失败");
                    }
                }).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    });
    public ParallelFileServer(int port,FileReceiver receiver){
        this.setPort(port);
        this.setReceiver(receiver);
        receiver.setFileServer(this);
    }
    @Override
    public void stop() throws Exception {
        getReceiver().stopAll();
        accept.stop();
    }
    @Override
    public void start() throws Exception {
        serverSocket=new ServerSocket(getPort());
        accept.start();
    }

    @Override
    public void taskStarted(String token, FileInfo info) {
        if(getTaskEvent()!=null)
            getTaskEvent().taskStarted(token, info);
    }

    @Override
    public void taskFinished(String token,FileInfo info) {
        if(getTaskEvent()!=null)
            getTaskEvent().taskFinished(token,info);
        CommandSender.removeTarget(token);
    }

    @Override
    public void taskInterrupted(String token, FileInfo info) {
        if(getTaskEvent()!=null)
            getTaskEvent().taskInterrupted(token, info);
        CommandSender.removeTarget(token);
    }
}
