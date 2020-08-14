package com.rft.core.server;

import java.io.DataInputStream;
import java.net.Socket;

public interface IFileReceiverControl {
    /**
     * 接收一个文件
     * @throws Exception
     */
    void receiveFile(FileInfo info,Socket socket, DataInputStream dataInputStream,String token) throws Exception;
    /**
     * 中断当前正在进行的接收
     */
    void interruptFile(String token);

    /**
     * 停止所有接收任务
     */
    void stopAll();
}
