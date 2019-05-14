package com.hwy.downloader.code.core;

import java.io.UnsupportedEncodingException;

public class Chapter {

    private String title;
    private String content;
    private String url;

    public Chapter(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] toByte() throws UnsupportedEncodingException {
       StringBuffer stringBuffer = new StringBuffer();
       stringBuffer.append(title);
       stringBuffer.append("\r\n");
       stringBuffer.append(content);
       stringBuffer.append("\r\n");
       return stringBuffer.toString().getBytes(Constant.CHARSET);
    }
}
