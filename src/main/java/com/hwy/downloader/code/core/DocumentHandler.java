package com.hwy.downloader.code.core;

import org.jsoup.nodes.Document;

import java.util.List;

public abstract class DocumentHandler {

    abstract  List<String> getChapterUrls(Document document) throws Exception;

    abstract  Chapter getChapter(Document document) throws Exception;
}
