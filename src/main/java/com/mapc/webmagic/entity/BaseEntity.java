package com.mapc.webmagic.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @desc: TODO
 * @author: duchao
 * @date: 2020/01/03 13:28
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    @PrePersist
    public void prePersist() {
        this.setCreateTime(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdateTime(LocalDateTime.now());
    }
}
