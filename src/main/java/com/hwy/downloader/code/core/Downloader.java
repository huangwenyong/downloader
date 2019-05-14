package com.hwy.downloader.code.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载器
 * 负责小说章节的下载，并将章节内容放置到容器中
 *
 */
public class Downloader implements Closeable {

    private DocumentHandler documentHandler;
    private Container container;
    private List<String> chapterUrls;
    private String bookUrl;
    private ExecutorService es  = Executors.newFixedThreadPool(4);

    public Downloader(String bookUrl){
        this.bookUrl = bookUrl;
    }

   public Downloader init() throws Exception {
        Document document = Jsoup.connect(bookUrl).get();
        this.chapterUrls = documentHandler.getChapterUrls(document);
        this.container = new Container(chapterUrls.size());
        return this;
   }

   public Downloader setDocumentHandler(DocumentHandler documentHandler){
        this.documentHandler = documentHandler;
        return this;
   }

   public Container getContainer(){
        return this.container;
   }

    public void downloadChapter() throws IOException {
        for (int index = 0,size = chapterUrls.size(); index < size ; index++) {
            String url = chapterUrls.get(index);
            int downIndex = index+1;
            es.execute(() -> {
                Document document = null;
                while (true){
                    try {
                        document = Jsoup.connect(url).get();
                    } catch (IOException e) {
                       e.printStackTrace();
                    }
                    if(document != null){
                        break;
                    }
                }
                Chapter chapter = null;
                try {
                    chapter = documentHandler.getChapter(document);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                container.put(downIndex, chapter);
            });
        }
        close();
    }
    @Override
    public void close() throws IOException {
        es.shutdown();
    }
}
