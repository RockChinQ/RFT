package com.rft.core.client;

import com.rft.core.util.Out;

import java.io.File;

/**
 * 每个发送任务新建一个此线程
 */
public class FileSender{
    private static void sendFileMethod(File file,String token,String ip,int port)throws Exception{

    }
    public void sendFile(File file,String token,String ip,int port){
        try{
            new Thread(()-> {
                try {
                    sendFileMethod(file, token, ip, port);
                } catch (Exception e) {
                    e.printStackTrace();
                    Out.say("SendFileThread-"+token,"发送文件失败");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Out.say("FileSender","无法创建线程");
        }
    }
}
