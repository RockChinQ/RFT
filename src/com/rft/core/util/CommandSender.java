package com.rft.core.util;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class CommandSender {
    static class TargetAlreadyExistException extends Exception{
        public TargetAlreadyExistException(){
            super();
        }
        public TargetAlreadyExistException(String msg){
            super(msg);
        }
        public TargetAlreadyExistException(String msg,Throwable cause){
            super(msg,cause);
        }
        public TargetAlreadyExistException(Throwable cause){
            super(cause);
        }
    }

    private static Map<String, BufferedWriter> targetMap=new HashMap<>();
    public static synchronized void sendMsg(String target, String msg){
        try {
            BufferedWriter bufferedWriter=targetMap.get(target);
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
            Out.say("CommandSender","发送消息失败");
        }
    }
    public static void addTarget(String key,BufferedWriter bufferedWriter)throws Exception{
        if(targetMap.containsKey(key)){
            throw new TargetAlreadyExistException("已存在此消息发送目标");
        }
        targetMap.put(key,bufferedWriter);
    }
    public static BufferedWriter removeTarget(String key){
        return targetMap.remove(key);
    }
}
