package com.mapc.webmagic.webmagic;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @desc: 自定义爬虫
 * @author: duchao
 * @date: 2020/01/03 11:06
 */
@Slf4j
@Configuration
@PropertySource(value = "classpath:webmagic.properties",ignoreResourceNotFound = true)
public abstract class BaseSpider {

    private Spider spider;

    @Setter
    @Value("${spider.threadNum}")
    private int threadNum;
    @Setter
    @Value("${scheduler.filePath}")
    private String schedulerFilePath;

    //@Resource
    //private SpiderFileCacheQueueScheduler spiderFileCacheQueueScheduler;

    public BaseSpider(PageProcessor pageProcessor) {
        log.info("===========爬虫实例化===========");
        spider = new Spider(pageProcessor);
    }

    @PostConstruct
    public void init() {
        log.info("===========爬虫初始化===========");
        //SpiderFileCacheQueueScheduler fileCacheQueueScheduler = new SpiderFileCacheQueueScheduler(schedulerFilePath);
        //fileCacheQueueScheduler.setRegx("https://dict.baidu.com/hanyu/ajax/search_list?wd=成语&pn=[0-9]{1,5}");
        spider.setScheduler(new FileCacheQueueScheduler(schedulerFilePath)).thread(threadNum);
    }

    public void start() {
        log.info("===========爬虫开启===========");
        spider.start();
    }

    public void stop() {
        log.info("===========爬虫停止===========");
        spider.stop();
    }

    public BaseSpider addRequest(String... urls) {
        for (String url : urls) {
            spider.addRequest(new Request(url));
        }
        return this;
    }

}
