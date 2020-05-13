package com.sym.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单DTO
 *
 * @author shenyanming
 * Created on 2020/5/13 15:13
 */
@Data
@ToString
@Accessors(chain = true)
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 3492836717416237470L;

    /**
     * 订单流水号
     */
    private Long id;

    /**
     * 商品编号
     */
    private String commodityCode;

    /**
     * 下单数量
     */
    private Integer orderCount;

    /**
     * 总花费
     */
    private BigDecimal money;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
