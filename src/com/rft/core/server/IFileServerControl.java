package com.rft.core.server;

public interface IFileServerControl {
    /**
     * server的关闭
     * @throws Exception
     */
    void stop() throws Exception;

    /**
     * server的启动
     * @throws Exception
     */
    void start() throws Exception;
}
