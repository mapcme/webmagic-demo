package com.mapc.webmagic;

import com.mapc.webmagic.webmagic.WordSpider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import us.codecraft.webmagic.Request;

import javax.annotation.Resource;

@SpringBootApplication
public class WebmagicDemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebmagicDemoApplication.class, args);
    }

    @Resource
    private WordSpider wordSpider;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        wordSpider.addRequest("https://dict.baidu.com/hanyu/ajax/search_list?wd=成语&pn=230").start();
    }
}
