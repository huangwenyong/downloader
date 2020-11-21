package com.hwy.downloader.code.core;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        long startTime = new Date().getTime();
        Downloader downloader = new Downloader("http://www.shuquge.com/txt/6356/index.html");
        try {
            downloader.setDocumentHandler(new QuanshuDocumnetHandler()).init().downloadChapter();
            Writer writer = new DefaultWrite(downloader.getContainer());
            writer.setCompleteListen(() ->{
                long endTime = new Date().getTime();
                System.out.println("下载完成！ 耗时:"+ (endTime - startTime) +"ms");
            });
            writer.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
