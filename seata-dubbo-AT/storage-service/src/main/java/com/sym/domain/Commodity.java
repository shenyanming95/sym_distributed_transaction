package com.sym.domain;

import com.sym.util.LocalDateTimeAttributeConverter;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
    private long id;

    @Column(name = "commodity_code")
    private String commodityCode;

    @Column(name = "commodity_name")
    private String commodityName;

    /**
     * 当前库存
     */
    @Column(name = "stock")
    private int stock;

    @Column(name = "create_time", columnDefinition="timestamp")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createTime;

    @Column(name = "update_time", columnDefinition="timestamp")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime updateTime;

}
