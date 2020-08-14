package com.rft.core.server;

public interface TaskEvent {
    void taskFinished(String token,FileInfo info);
    void taskInterrupted(String token,FileInfo info);
}
