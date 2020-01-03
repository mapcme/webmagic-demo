package com.mapc.webmagic.processor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.mapc.webmagic.entity.Word;
import com.mapc.webmagic.repository.WordRepository;
import com.mapc.webmagic.webmagic.WordSpider;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @desc: 句子爬取页面处理器
 * @author: duchao
 * @date: 2020/01/03 09:30
 */
@Component
public class WordPageProcessor implements PageProcessor {

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(100);
    private static Integer processingPageNo = 1;
    private static Integer lastPageNo = 231;//1546;
    private static String rootUrl = "https://dict.baidu.com/hanyu/ajax/search_list?wd=成语&pn=";

    @Resource
    private WordRepository wordRepository;

    @Override
    public void process(Page page) {
        String processingUrl=page.getRequest().getUrl();
        processingPageNo = Integer.parseInt(processingUrl.substring(processingUrl.lastIndexOf("=")+1));

        if (processingPageNo > lastPageNo) {
            System.out.println("已经爬取全部数据");
            return;
        }

        List<Word> wordList=new ArrayList<>();

        //解析页面内容
        String response=page.getHtml().getDocument().getElementsByTag("body").text();
        if(processingPageNo.equals(230)){//json错误
            response=response.replace("\\uff1b\\u884c\\u5546\\u5750\\u8d3e\"\\uff1b\\u3002\\u73b0\\u6cdb\\u6307\\u7ecf\\u5546\\u7684\\u4e70\\u5356\\u4eba\\u3002\"","");
        }
        JSONArray wordJSONArray = JSON.parseObject(response).getJSONArray("ret_array");
        System.out.println(wordJSONArray);
        wordJSONArray.stream().forEach(item->{
            Map<String, List<String>> wordDesc = JSON.parseObject(JSON.toJSONString(item),Map.class);
            String wordName=wordDesc.get("name").get(0);
            String wordPinyin = wordDesc.get("pinyin").get(0);
            String wordDefinition= CollectionUtil.isNotEmpty(wordDesc.get("definition"))?wordDesc.get("definition").stream().reduce("",String::concat):null;
            String wordSynonym=CollectionUtil.isNotEmpty(wordDesc.get("synonym"))?wordDesc.get("synonym").stream().reduce("", (a,b)->String.join(",",a,b)):null;
            wordSynonym=StrUtil.isNotEmpty(wordSynonym)?wordSynonym.substring(1):null;
            String wordAntonym=CollectionUtil.isNotEmpty(wordDesc.get("antonym"))?wordDesc.get("antonym").stream().reduce("",(a,b)->String.join(",",a,b)):null;
            wordAntonym=StrUtil.isNotEmpty(wordAntonym)?wordAntonym.substring(1):null;
            wordList.add(new Word(wordName,wordPinyin,wordDefinition,wordSynonym,wordAntonym));
        });
        wordRepository.saveAll(wordList);

        //添加爬取链接
        processingPageNo++; // TODO 这里多线程导致并发问题。方案：1加锁；2爬取之前提前把链接全部放进去（倾向2）
        page.addTargetRequests(Collections.singletonList(rootUrl + processingPageNo));
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
