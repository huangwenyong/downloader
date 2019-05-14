package com.hwy.downloader.code.core;

import javax.xml.ws.WebServiceException;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DefaultWrite implements Writer {

    private Container container;
    private int writeIndex;
    private static final int  START_INDEX = 1;
    private CompleteListen completeListen;
    private ExecutorService es = Executors.newSingleThreadExecutor();
    public DefaultWrite(Container container){
        this.container = container;
        this.writeIndex = START_INDEX;
    }

    public void setCompleteListen(CompleteListen completeListen){
        this.completeListen = completeListen;
    }

    @Override
    public void write() {
        es.execute(() -> {
            while (true){
                try {
                    if(writeIndex > container.capacity()){
                       if(completeListen != null){
                           completeListen.complete();
                       }
                        break;
                    }
                    Chapter chapter = container.get(writeIndex);
                    doWrite(chapter);
                    container.remove(writeIndex);
                    writeIndex ++;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("写入第 " + writeIndex + " 章失败");
                }
            }
        });
    }

    public void doWrite(Chapter chapter) throws IOException {
        if (checkChapterIsNull(chapter)){
           return;
        }
        writeToFile(chapter);
    }

    private void writeToFile(Chapter chapter) throws IOException {
        File file = new File("/home/huangwenyong/book.txt");
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, true)))){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(chapter.getTitle());
            stringBuffer.append("\r\n");
            stringBuffer.append(chapter.getContent());
            stringBuffer.append("\r\n");
            stringBuffer.append("\r\n");
            writer.write(stringBuffer.toString());
            System.out.println(chapter.getTitle() +" 下载成功！");
        } catch (IOException e) {
            throw new IOException(e);
        }finally {
            close();
        }
    }


    private boolean checkChapterIsNull(Chapter chapter){
        return chapter == null ? true : false;
    }

    @Override
    public void close() throws WebServiceException {
        if(writeIndex >= container.capacity()){
            es.shutdown();
        }
    }
}
