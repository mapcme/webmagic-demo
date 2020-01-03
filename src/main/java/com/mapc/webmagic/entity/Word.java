package com.mapc.webmagic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @desc: 词语
 * @author: duchao
 * @date: 2020/01/03 13:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "word")
public class Word extends BaseEntity{

    /**
     * 名称
     */
    private String name;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 意思
     */
    private String mean;

    /**
     * 近义词
     */
    private String synonym;

    /**
     * 反义词
     */
    private String antonym;
}
