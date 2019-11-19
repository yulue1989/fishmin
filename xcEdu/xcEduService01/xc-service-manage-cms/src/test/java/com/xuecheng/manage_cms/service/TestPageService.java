package com.xuecheng.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPageService {

    @Autowired
    PageService pageService;

    @Test
    public void testPageService(){
        String html = pageService.getPageHtml("5da818aeb16be84a3c50741d");
        System.out.println(html);
    }
}
