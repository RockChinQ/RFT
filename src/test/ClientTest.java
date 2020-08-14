package test;

import com.rft.core.client.FileSender;

import java.io.File;

public class ClientTest {
    public static void main(String [] args){
        FileSender.sendFile(new File("testVideo.MOV"),"","localtest","localhost",1035);
    }
}
