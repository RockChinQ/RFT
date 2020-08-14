package test;

import com.rft.core.server.BufferedFileReceiver;
import com.rft.core.server.FileReceiver;
import com.rft.core.server.FileServer;
import com.rft.core.server.ParallelFileServer;

public class ServerMain {
    static FileServer fileServer;
    static FileReceiver fileReceiver;
    public static void main(String[] args){
        fileReceiver=new BufferedFileReceiver();
        fileServer=new ParallelFileServer(1035,fileReceiver);
        try {
            fileServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
