package com.mapc.webmagic.webmagic;

import com.mapc.webmagic.processor.SentencePageProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @desc: 句子爬虫
 * @author: duchao
 * @date: 2020/01/03 11:42
 */
@ConditionalOnClass(SentencePageProcessor.class)
@Component
public class SentenceSpider {
}
