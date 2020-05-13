package com.sym.domain;

import com.sym.util.LocalDateTimeAttributeConverter;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 *
 * @author shenym
 * @date 2020/3/8 20:29
 */
@Data
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "commodity_code")
    private String commodityCode;

    @Column(name = "order_count")
    private int orderCount;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "create_time", columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createTime;

    @Column(name = "update_time", columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime updateTime;

}
