package com.mapc.webmagic.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @desc: 句子
 * @author: duchao
 * @date: 2020/01/03 14:35
 */
@Entity
@Table(name = "sentence")
@Data
public class Sentence extends BaseEntity{

    private String text;

    /**
     * 词语id
     */
    private Long wordId;
}
