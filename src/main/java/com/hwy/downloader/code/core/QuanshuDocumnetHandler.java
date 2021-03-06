package com.hwy.downloader.code.core;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class QuanshuDocumnetHandler extends  DocumentHandler {

    @Override
    List<String> getChapterUrls(Document document) throws Exception {
        Elements elements = document.select("div.listmain  a");
        if(elements == null || elements.size() <= 0){
           throw new Exception("未找到 div.readerlist  a 节点");
        }
        return  elements.stream().map(e -> e.absUrl("href")).collect(Collectors.toList());
    }

    @Override
    Chapter getChapter(Document document) throws Exception {
       Element element = document.selectFirst("div.content h1");
       if(element == null){
           System.out.println(document.text());
           throw new Exception("div.content h1");
       }
       String title = element.text();
       element = document.selectFirst("div#content");
       if(element == null){
           throw new Exception("未找到 div#content 节点");
       }
       String content = element.text();
       return new Chapter(title, content);
    }
}
