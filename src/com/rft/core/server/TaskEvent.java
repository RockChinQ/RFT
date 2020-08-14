package com.rft.core.server;

public interface TaskEvent {
    void taskStarted(String token,FileInfo info);
    void taskFinished(String token,FileInfo info);
    void taskInterrupted(String token,FileInfo info);
}
