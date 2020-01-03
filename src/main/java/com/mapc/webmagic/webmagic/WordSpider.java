package com.mapc.webmagic.webmagic;

import com.mapc.webmagic.processor.WordPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @desc: 词语爬虫
 * @author: duchao
 * @date: 2020/01/03 11:36
 */
@ConditionalOnClass(WordPageProcessor.class)
@Component
public class WordSpider extends BaseSpider {

    @Autowired
    public WordSpider(WordPageProcessor wordPageProcessor) {
        super(wordPageProcessor);
    }
}
