package com.hwy.downloader.code.core;

import com.sun.xml.internal.ws.Closeable;

/**
 * 将容器中的章节内容写入到文件中
 */
public interface Writer extends Closeable {
    void write() throws InterruptedException;
    void setCompleteListen(CompleteListen completeListen);
}
