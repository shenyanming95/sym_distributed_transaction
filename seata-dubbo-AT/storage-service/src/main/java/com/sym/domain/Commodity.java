package com.sym.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 商品实体
 *
 * @author shenym
 * @date 2020/3/8 20:29
 */
@Data
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "t_commodity")
public class Commodity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "commodity_code")
    private String commodityCode;

    @Column(name = "commodity_name")
    private String commodityName;

    /**
     * 当前库存
     */
    @Column(name = "stock")
    private int stock;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
