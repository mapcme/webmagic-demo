package com.mapc.webmagic.controller;

import com.mapc.webmagic.webmagic.WordSpider;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @desc: TODO
 * @author: duchao
 * @date: 2020/01/03 13:11
 */
@RestController
@RequestMapping("spider")
public class SpiderController {

    @Resource
    private WordSpider wordSpider;

    @PutMapping("word/start")
    public void startWordSpider(){
        wordSpider.start();
    }

    @PutMapping("word/stop")
    public void stopWordSpider(){
        wordSpider.stop();
    }
}
