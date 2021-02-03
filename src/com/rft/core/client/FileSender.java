package com.rft.core.client;

import com.rft.core.util.Out;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 每个发送任务新建一个此线程
 */
public class FileSender{
    public static void sendFileMethod(File file,String savePath,String token,String ip,int port)throws Exception{
        if(file.exists()) {
            FileInputStream inputStream=new FileInputStream(file);
            Socket socket = new Socket(ip, port);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Thread t=new Thread(()->{
                try {
                    String msg = "";
                    readMsg:while ((msg = bufferedReader.readLine()) != null) {
                        String msgs[]=msg.split(" ");
                        switch (msgs[0]){
                            case "reconn":{
                                Out.say("FileSender-"+token,"连接被拒绝:"+msgs[1]);
                                taskMap.remove(token);
                                inputStream.close();
                                bufferedReader.close();
                                dataOutputStream.close();
                                socket.close();
                                break readMsg;
                            }
                        }
                    }
                }catch (Exception e){
//                    e.printStackTrace();
//                    Out.say("FileSender","接收消息失败");
                }
            });
            t.start();
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.flush();
            dataOutputStream.writeUTF(savePath);
            dataOutputStream.flush();
            dataOutputStream.writeUTF(token);
            dataOutputStream.flush();
            dataOutputStream.writeLong(file.length());
            dataOutputStream.flush();
            byte bytes[] = new byte[1024];
            int length = 0;
            while ((length =inputStream.read(bytes,0,bytes.length))!=-1){
                dataOutputStream.write(bytes,0,length);
                dataOutputStream.flush();
            }
            taskMap.remove(token);
//            Out.say("FileSender-"+token,"文件已发送");
            t.stop();
            inputStream.close();
            dataOutputStream.close();
            bufferedReader.close();
            socket.close();
        }
    }

    static Map<String,Thread> taskMap=new HashMap<>();
    public static void sendFile(File file,String savePath,String token,String ip,int port){
        try{
            Thread t=new Thread(()-> {
                try {
                    sendFileMethod(file,savePath, token, ip, port);
                } catch (Exception e) {
//                    e.printStackTrace();
//                    Out.say("SendFileThread-"+token,"发送文件失败");
                }
            });
            taskMap.put(token,t);
            t.start();
        }catch (Exception e){
//            e.printStackTrace();
//            Out.say("FileSender","无法创建线程");
        }
    }
}
