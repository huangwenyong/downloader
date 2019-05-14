package com.hwy.downloader.code;

import com.hwy.downloader.code.core.Chapter;
import com.hwy.downloader.code.core.Container;
import org.junit.Test;

public class ContainerTest {

    @Test
    public void testGetAndPut() throws InterruptedException {
        Container container = new Container(2);
        new Thread(() ->{
            try {
                Chapter chapter = container.get(1);
                System.out.println(chapter.getTitle());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(3000);
      //  container.put(1, new Chapter("1" , "2"));

    }

}
